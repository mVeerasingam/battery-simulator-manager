package com.example.simulationmanager.Config;

import org.springframework.amqp.core.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue simulationResultsQueue() {
        return new Queue("simulation_results", true);
    }

    @Bean
    public Queue userValidationRequestQueue() {
        return new Queue("user_validation_request", true);
    }

    @Bean
    public Queue userValidationResponseQueue() {
        return new Queue("user_validation_response", true);
    }

    @Bean
    public Exchange userValidationExchange() {
        return new DirectExchange("user_validation_exchange", true, false);
    }

    // Bind request queue to exchange
    @Bean
    public Binding bindRequestQueueToExchange() {
        return BindingBuilder.bind(userValidationRequestQueue()).to(userValidationExchange())
                .with("user.validation.request")
                .noargs();
    }

    // Bind response queue to exchange
    @Bean
    public Binding bindResponseQueueToExchange() {
        return BindingBuilder.bind(userValidationResponseQueue()).to(userValidationExchange())
                .with("user.validation.response")
                .noargs();
    }
}
