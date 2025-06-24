package org.example.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.constants.MyConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RabbitConfig implements MyConstants {

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

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(MyConstants.TASK_QUEUE_EXCHANGE);
    }

    @Bean
    public Queue taskQueue() {
        return new Queue(MyConstants.TASK_QUEUE_NAME, true);
    }

    @Bean
    public Binding binding(Queue taskQueue, DirectExchange exchange) {
        return BindingBuilder.bind(taskQueue).to(exchange).with(MyConstants.TASK_QUEUE_ROUTING_KEY);
    }


}

