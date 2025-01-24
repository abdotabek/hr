package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.constants.MyConstants;
import org.example.repository.EmployeeRepository;
import org.example.repository.TaskRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RabbitMQService implements MyConstants {

    RabbitTemplate rabbitTemplate;
    EmployeeRepository employeeRepository;
    TaskRepository taskRepository;


    @RabbitListener(queues = EMPLOYEE_QUEUE_NAME)
    public void receiveEmployeeDeleteMessage(String message) {
        try {
            Long employeeId = Long.parseLong(message);
            employeeRepository.deleteById(employeeId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid message received : " + message);
        }
    }

    @RabbitListener(queues = TASK_QUEUE_NAME)
    public void receiveTaskDeleteMessage(String message) {
        try {
            Long taskId = Long.parseLong(message);
            taskRepository.deleteById(taskId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid message received : " + message);
        }
    }

    public void deleteEmployee(Long employeeId) {
        rabbitTemplate.convertAndSend(EMPLOYEE_QUEUE_EXCHANGE, EMPLOYEE_QUEUE_ROUTING_KEY, employeeId.toString());
    }

    public void deleteTask(Long taskId) {
        rabbitTemplate.convertAndSend(TASK_QUEUE_EXCHANGE, TASK_QUEUE_ROUTING_KEY, taskId.toString());
    }
}
