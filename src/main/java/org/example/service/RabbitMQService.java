package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.constants.MyConstants;
import org.example.entity.Task;
import org.example.repository.EmployeeRepository;
import org.example.repository.TaskRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RabbitMQService implements MyConstants {

    RabbitTemplate rabbitTemplate;
    EmployeeRepository employeeRepository;
    TaskRepository taskRepository;
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    @RabbitListener(queues = MyConstants.EMPLOYEE_QUEUE_NAME, concurrency = "1")
    public void receiveEmployeeDeleteMessage(String message) {
        try {
            Long employeeId = Long.parseLong(message);
            System.out.println("Received employee with id : " + employeeId + " at " + LocalDateTime.now());

            // Найти все связанные задачи
            List<Task> tasks = taskRepository.findByEmployeeId(employeeId);
            if (!tasks.isEmpty()) {
                deleteTasksWithDelay(tasks, employeeId);  // 10 секунд задержки между задачами
            } else {
                // Если задач нет, сразу удаляем сотрудника
                deleteEmployee(employeeId);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid message received : " + message);
        }
    }

    public void sendDeleteTaskMessage(List<Long> employeeIds, int delaySeconds) {
        int[] delayCount = {0};  // Массив для изменения значения внутри лямбды

        employeeIds.forEach(employeeId -> {
            int currentDelay = delayCount[0];
            delayCount[0] += delaySeconds;  // Увеличиваем задержку для следующего сообщения

            // Отправка сообщения с задержкой
            rabbitTemplate.convertAndSend(MyConstants.TASK_QUEUE_EXCHANGE, MyConstants.TASK_QUEUE_ROUTING_KEY, employeeId.toString(), message -> {
                // Устанавливаем задержку в миллисекундах
                message.getMessageProperties().setHeader("x-delay", currentDelay * 10000);  // Задержка в миллисекундах
                return message;
            });

            System.out.println("Sent task delete message for employee with id: " + employeeId + " after " + currentDelay + " seconds");
        });
    }

    private void deleteEmployee(Long employeeId) {
        try {
            employeeRepository.deleteById(employeeId);
            System.out.println("Employee with id " + employeeId + " has been deleted.");
        } catch (Exception e) {
            System.out.println("Failed to delete employee with id " + employeeId + ": " + e.getMessage());
        }
    }

    private void deleteTasksWithDelay(List<Task> tasks, Long employeeId) {
        int[] delayCount = {0};  // Массив для изменения значения внутри лямбды

        tasks.forEach(task -> {
            int currentDelay = delayCount[0];
            delayCount[0] += 10;  // Увеличиваем задержку для следующей задачи

            // Удаление задачи с задержкой
            scheduler.schedule(() -> {
                taskRepository.delete(task);
                System.out.println("Deleted task with id " + task.getId() + " for employee with id " + employeeId);
                // После удаления задачи — удаляем сотрудника с задержкой
                if (delayCount[0] == 10 * tasks.size()) {
                    deleteEmployee(employeeId);
                }
            }, currentDelay, TimeUnit.SECONDS);
        });
    }

    public void deleteEmployeeBatchWithDelay(List<Long> employeeIds, int delaySeconds) {
        int[] delayCount = {0};

        employeeIds.forEach(employeeId -> {
            int currentDelay = delayCount[0];
            delayCount[0] += delaySeconds;

            // Сначала отправляем задачи на удаление
            sendDeleteTaskMessage(Collections.singletonList(employeeId), delaySeconds);

            // Затем запланируем удаление сотрудника
            scheduler.schedule(() -> {
                try {
                    employeeRepository.deleteById(employeeId);
                    System.out.println("Employee with id " + employeeId + " has been deleted at " + LocalDateTime.now());
                } catch (Exception e) {
                    System.out.println("Failed to delete employee with id " + employeeId + ": " + e.getMessage());
                }
            }, currentDelay + delaySeconds, TimeUnit.SECONDS);  // Устанавливаем задержку на удаление сотрудника

            System.out.println("Scheduled deletion for employee " + employeeId + " after " + (currentDelay + delaySeconds) + " seconds");
        });
    }

    @RabbitListener(queues = MyConstants.TASK_QUEUE_NAME, concurrency = "1")
    public void receiveTaskDeleteMessage(String message) {
        try {
            Long employeeId = Long.parseLong(message);
            System.out.println("Received task delete message for employee with id : " + employeeId);

            // Найдем все задачи сотрудника и удалим их поочередно
            List<Task> tasks = taskRepository.findByEmployeeId(employeeId);
            if (!tasks.isEmpty()) {
                int[] delayCount = {0};
                tasks.forEach(task -> {
                    int currentDelay = delayCount[0];
                    delayCount[0] += 10;  // 10 секунд задержки между задачами

                    scheduler.schedule(() -> {
                        taskRepository.delete(task);
                        System.out.println("Deleted task with id " + task.getId() + " for employee with id " + employeeId);
                    }, currentDelay, TimeUnit.SECONDS);
                });
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid message received : " + message);
        }
    }


}

