package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.constants.MyConstants;
import org.example.repository.EmployeeRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RabbitMQService implements MyConstants{

    EmployeeRepository employeeRepository;
    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = MyConstants.EMPLOYEE_QUEUE_NAME, concurrency = "1")
    public void receiveEmployeeDeleteMessage(String message) {
        log.info("receiveEmployeeDeleteMessage: {}", message);
        Long employeeId = Long.parseLong(message);
        employeeRepository.deleteById(employeeId);

    }

    public void deleteEmployee(List<Long> employeeIds) {

        int initialDelayMilliseconds = 10_000;
        int cumulativeDelay = 0;
        for (Long employeeId : employeeIds) {
            int delayMilliseconds = initialDelayMilliseconds + cumulativeDelay;
            cumulativeDelay += initialDelayMilliseconds;

            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setHeader("x-delay", delayMilliseconds);
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);

            Message message = new Message(employeeId.toString().getBytes(StandardCharsets.UTF_8), messageProperties);

            rabbitTemplate.send(MyConstants.EMPLOYEE_QUEUE_EXCHANGE, MyConstants.EMPLOYEE_QUEUE_ROUTING_KEY, message);
        }
    }

}


