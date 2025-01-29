package org.example.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.constants.MyConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RabbitConfig {

    @Bean
    public Queue taskQueue() {
        return new Queue(MyConstants.TASK_QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange taskExchange() {
        return new TopicExchange(MyConstants.TASK_QUEUE_EXCHANGE);
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

    @Bean
    public Queue employeeQueue() {
        return QueueBuilder.durable(MyConstants.EMPLOYEE_QUEUE_NAME)
                .withArgument("x-delayed-type", "direct")
                .build();
    }

    @Bean
    public TopicExchange employeeExchange() {
        return new TopicExchange(MyConstants.EMPLOYEE_QUEUE_EXCHANGE);
    }


    @Bean
    public Binding employeeBinding() {
        return BindingBuilder.bind(employeeQueue())
                .to(employeeExchange())
                .with(MyConstants.EMPLOYEE_QUEUE_ROUTING_KEY);
    }


}
