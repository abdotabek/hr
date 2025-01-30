package org.example.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.constants.MyConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RabbitConfig {

    @Bean
    public CustomExchange delayedExchange() {
        return new CustomExchange(MyConstants.EMPLOYEE_QUEUE_EXCHANGE, "x-delayed-message", true, false,
                Map.of("x-delayed-type", "direct"));
    }

    @Bean
    public Queue employeeQueue() {
        return QueueBuilder.durable(MyConstants.EMPLOYEE_QUEUE_NAME).build();
    }

    @Bean
    public Binding employeeBinding(Queue employeeQueue, CustomExchange delayedExchange) {
        return BindingBuilder.bind(employeeQueue)
                .to(delayedExchange)
                .with(MyConstants.EMPLOYEE_QUEUE_ROUTING_KEY)
                .noargs();
    }
}

