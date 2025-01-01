package com.example.simulationmanager.Config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RabbitConfig {
//
//    @Bean
//    public Queue simulationQueue() {
//        return new Queue("celery", true);  // Durable queue
//    }
//
//    @Bean
//    public Jackson2JsonMessageConverter jacksonMessageConverter() {
//        return new Jackson2JsonMessageConverter();  // Convert objects to JSON
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(jacksonMessageConverter());  // Use JSON converter
//        return rabbitTemplate;
//    }
}
