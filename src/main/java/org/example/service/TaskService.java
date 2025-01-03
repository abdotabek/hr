package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.task.TaskDTO;
import org.example.entity.Task;
import org.example.exception.ExceptionUtil;
import org.example.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaskService {

    TaskRepository taskRepository;

    public Long create(TaskDTO taskDTO) {
        if (taskDTO.getTitle() == null || taskDTO.getTitle().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("Task title is required!");
        }
        if (taskDTO.getEmployeeId() == null) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("employee id is required!");
        }
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setContent(taskDTO.getContent());
        task.setCreatedDate(LocalDateTime.now());
        task.setEmployeeId(taskDTO.getEmployeeId());
        taskRepository.save(task);
        return task.getId();
    }

    public TaskDTO get(Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    TaskDTO taskDTO = new TaskDTO();
                    taskDTO.setId(task.getId());
                    taskDTO.setTitle(task.getTitle());
                    taskDTO.setContent(task.getContent());
                    taskDTO.setCreatedDate(task.getCreatedDate());
                    taskDTO.setEmployeeId(task.getEmployeeId());
                    return taskDTO;
                }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("task with id does not exist"));
    }

    public List<TaskDTO> getList() {
        return taskRepository.findAll().stream()
                .map(task -> {
                    TaskDTO taskDTO = new TaskDTO();
                    taskDTO.setId(task.getId());
                    taskDTO.setTitle(task.getTitle());
                    taskDTO.setContent(task.getContent());
                    taskDTO.setCreatedDate(task.getCreatedDate());
                    taskDTO.setEmployeeId(task.getEmployeeId());
                    return taskDTO;
                }).collect(Collectors.toList());
    }

    public TaskDTO update(Long id, TaskDTO taskDTO) {
        if (taskDTO.getTitle() == null || taskDTO.getTitle().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("Task title is required!");
        }
        if (taskDTO.getEmployeeId() == null) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("employee id is required!");
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

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    private TaskDTO toDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setContent(task.getContent());
        taskDTO.setCreatedDate(task.getCreatedDate());
        taskDTO.setEmployeeId(task.getEmployeeId());
        return taskDTO;
    }

}
