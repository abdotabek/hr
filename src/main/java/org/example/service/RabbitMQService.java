package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.constants.MyConstants;
import org.example.repository.EmployeeRepository;
import org.example.repository.TaskRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RabbitMQService implements MyConstants {

    EmployeeRepository employeeRepository;
    TaskRepository taskRepository;
    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = MyConstants.EMPLOYEE_QUEUE_NAME, concurrency = "1")
    public void receiveEmployeeDeleteMessage(String message) {
        Long employeeId = Long.parseLong(message);
        employeeRepository.deleteById(employeeId);
    }

    public void deleteEmployee(List<Long> employeeIds) {

        int initialDelayMilliseconds = 30_000;  // задержка для первого сообщения 30 секунд

        int[] cumulativeDelay = {0};
        for (Long employeeId : employeeIds) {

            int delayMilliseconds = initialDelayMilliseconds + cumulativeDelay[0];
            cumulativeDelay[0] += initialDelayMilliseconds;

            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setHeader("x-delay", delayMilliseconds);

            Message message = new Message(employeeId.toString().getBytes(), messageProperties);

            System.out.println("Sending message for employeeId: " + employeeId + " with delay: " + delayMilliseconds);
            rabbitTemplate.send(MyConstants.EMPLOYEE_QUEUE_EXCHANGE, MyConstants.EMPLOYEE_QUEUE_ROUTING_KEY, message);
        }
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

