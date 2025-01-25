package org.example.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.constants.MyConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RabbitConfig {

    @Bean
    public Queue employeeQueue() {
        return new Queue(MyConstants.EMPLOYEE_QUEUE_NAME, true);
    }

    @Bean
    public Queue taskQueue() {
        return QueueBuilder.durable(MyConstants.TASK_QUEUE_NAME)
                .withArgument("x-delayed-type", "direct")
                .build();
    }


    @Bean
    public TopicExchange employeeExchange() {
        return new TopicExchange(MyConstants.EMPLOYEE_QUEUE_EXCHANGE);
    }

    @Bean
    public TopicExchange taskExchange() {
        return new TopicExchange(MyConstants.TASK_QUEUE_EXCHANGE);
    }

    @Bean
    public Binding employeeBinding() {
        return new Binding(
                MyConstants.EMPLOYEE_QUEUE_NAME,
                Binding.DestinationType.QUEUE,
                MyConstants.EMPLOYEE_QUEUE_EXCHANGE,
                MyConstants.EMPLOYEE_QUEUE_ROUTING_KEY,
                null
        );
    }

    @Bean
    public Binding taskBinding() {
        return new Binding(
                MyConstants.TASK_QUEUE_NAME,
                Binding.DestinationType.QUEUE,
                MyConstants.TASK_QUEUE_EXCHANGE,
                MyConstants.TASK_QUEUE_ROUTING_KEY,
                null
        );
    }
}
