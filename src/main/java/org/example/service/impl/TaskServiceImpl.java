package org.example.service.impl;

import com.google.common.collect.Iterables;
import lombok.RequiredArgsConstructor;
import org.example.constants.MyConstants;
import org.example.dto.task.TaskDTO;
import org.example.entity.Task;
import org.example.exception.ExceptionUtil;
import org.example.repository.TaskRepository;
import org.example.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final RabbitTemplate rabbitTemplate;


    static Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);
    static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    public Long create(final TaskDTO taskDTO) {
        if (taskDTO.getTitle() == null || taskDTO.getTitle().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("Task title is required!");
        }
        final Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setContent(taskDTO.getContent());
        task.setCreatedDate(LocalDateTime.now());
        task.setEmployeeId(taskDTO.getEmployeeId());
        taskRepository.save(task);
        return task.getId();
    }

    @Override
    public TaskDTO get(final Long id) {
        return taskRepository.findById(id)
            .map(task -> {
                final TaskDTO taskDTO = new TaskDTO();
                taskDTO.setId(task.getId());
                taskDTO.setTitle(task.getTitle());
                taskDTO.setContent(task.getContent());
                taskDTO.setCreatedDate(task.getCreatedDate());
                taskDTO.setEmployeeId(task.getEmployeeId());
                return taskDTO;
            }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("task with id does not exist"));
    }

    @Override
    public List<TaskDTO> getList() {
        return taskRepository.findAll().stream()
            .map(task -> {
                final TaskDTO taskDTO = new TaskDTO();
                taskDTO.setId(task.getId());
                taskDTO.setTitle(task.getTitle());
                taskDTO.setContent(task.getContent());
                taskDTO.setCreatedDate(task.getCreatedDate());
                taskDTO.setEmployeeId(task.getEmployeeId());
                return taskDTO;
            }).collect(Collectors.toList());
    }

    public TaskDTO update(final Long id, final TaskDTO taskDTO) {
        if (taskDTO.getTitle() == null || taskDTO.getTitle().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("Task title is required!");
        }
        taskRepository.findById(id)
            .map(task -> {
                task.setTitle(taskDTO.getTitle());
                task.setContent(taskDTO.getContent());
                task.setCreatedDate(taskDTO.getCreatedDate());
                task.setEmployeeId(taskDTO.getEmployeeId());

                taskRepository.save(task);
                return task.getId();
            }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("task with in not exist"));
        return taskDTO;
    }

    @Override
    public void delete(final Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void sendTaskIdsToQueue(final List<Long> taskIds) {
        Iterable<List<Long>> partitions = Iterables.partition(taskIds, 4);

        for (List<Long> partition : partitions) {
            for (Long taskId : partition) {
                rabbitTemplate.convertAndSend(MyConstants.TASK_QUEUE_EXCHANGE, MyConstants.TASK_QUEUE_ROUTING_KEY, taskId);
            }
        }
    }

    @RabbitListener(queues = MyConstants.TASK_QUEUE_NAME)
    private void deleteTask(final Long taskId) {
        taskRepository.deleteById(taskId);
        log.info("task with id {} has been deleted.", taskId);
    }

    @Scheduled(cron = "0 0 0 1 * *")
    private void startTaskDeletionAtStartOfMonth() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        List<Long> taskIds = taskRepository.findTasksIdsToDelete(thirtyDaysAgo);
        sendTaskIdsToQueue(taskIds);
        log.info("Starting task deletion at the beginning of the month.");
    }
}
