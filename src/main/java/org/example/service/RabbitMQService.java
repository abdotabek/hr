package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RabbitMQService {

    final RabbitTemplate rabbitTemplate;
    final EmployeeService employeeService;

    @Value("${rabbitmq.exchange}")
    String exchange;

    @Value("${rabbitmq.routing-key}")
    String routingKey;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveMessage(String message) {
        try {
            Long employeeId = Long.parseLong(message);
            employeeService.delete(employeeId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid message received : " + message);
        }
    }

    public void sendMessage(Long employeeId) {
        rabbitTemplate.convertAndSend(exchange, routingKey, employeeId.toString());
    }
}
