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
        return new Binding(
                MyConstants.EMPLOYEE_QUEUE_NAME,
                Binding.DestinationType.QUEUE,
                MyConstants.EMPLOYEE_QUEUE_EXCHANGE,
                MyConstants.EMPLOYEE_QUEUE_ROUTING_KEY,
                null
        );
    }

    @Bean
    public Queue companyQueue() {
        return new Queue(MyConstants.COMPANY_QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange companyExchange() {
        return new TopicExchange(MyConstants.COMPANY_QUEUE_EXCHANGE);
    }

    @Bean
    public Binding companyBinding() {
        return new Binding(
                MyConstants.COMPANY_QUEUE_NAME,
                Binding.DestinationType.QUEUE,
                MyConstants.COMPANY_QUEUE_EXCHANGE,
                MyConstants.COMPANY_QUEUE_ROUTING_KEY,
                null
        );
    }

    @Bean
    public Queue branchQueue() {
        return new Queue(MyConstants.BRANCH_QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange branchExchange() {
        return new TopicExchange(MyConstants.BRANCH_QUEUE_EXCHANGE);
    }

    @Bean
    public Binding branchBinding() {
        return new Binding(
                MyConstants.BRANCH_QUEUE_NAME,
                Binding.DestinationType.QUEUE,
                MyConstants.BRANCH_QUEUE_EXCHANGE,
                MyConstants.BRANCH_QUEUE_ROUTING_KEY,
                null
        );
    }


}
