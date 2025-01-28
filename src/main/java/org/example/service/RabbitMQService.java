package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.constants.MyConstants;
import org.example.entity.Task;
import org.example.repository.EmployeeRepository;
import org.example.repository.TaskRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RabbitMQService implements MyConstants {

    EmployeeRepository employeeRepository;
    TaskRepository taskRepository;
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    @RabbitListener(queues = MyConstants.EMPLOYEE_QUEUE_NAME, concurrency = "1")
    public void receiveEmployeeDeleteMessage(String message) {
        try {
            Long employeeId = Long.parseLong(message);
            System.out.println("Received employee with id : " + employeeId + " at " + LocalDateTime.now());
            employeeRepository.deleteById(employeeId);
            System.out.println("employee with id " + employeeId + " has been deleted.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid message received : " + message);
        }
    }

    public void deleteEmployee(List<Long> ids) {
        deleteEmployeeBatchWithDelay(ids, 30_000);
    }

    public void deleteEmployeeBatchWithDelay(List<Long> employeeIds, int delayMilliseconds) {
        int[] delayCount = {0};

        employeeIds.forEach(employeeId -> {
            int currentDelay = delayMilliseconds + delayCount[0];
            delayCount[0] += delayMilliseconds;

            scheduler.schedule(() -> {
                try {
                    List<Task> taskList = taskRepository.findByEmployeeId(employeeId);
                    taskRepository.deleteAll(taskList);

                    employeeRepository.deleteById(employeeId);
                    System.out.println("Employee with id " + employeeId + " has been deleted at " + LocalDateTime.now());
                } catch (Exception e) {
                    System.out.println("Failed to delete employee with id " + employeeId + ": " + e.getMessage());
                }
            }, currentDelay, TimeUnit.MILLISECONDS);

            System.out.println("Scheduled deletion for employee " + employeeId + " after " + currentDelay + " ms");
        });
    }

    @RabbitListener(queues = MyConstants.TASK_QUEUE_NAME)
    public void receiveTaskDeleteMessage(String message) {
        try {
            Long taskId = Long.parseLong(message);
            taskRepository.deleteById(taskId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid message received : " + message);
        }
    }
}

