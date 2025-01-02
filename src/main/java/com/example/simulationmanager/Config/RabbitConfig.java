package com.example.simulationmanager.Config;

import org.springframework.amqp.core.Queue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String SIMULATION_RESULTS_QUEUE = "simulation_results";

    @Bean
    public Queue simulationResultsQueue() {
        return new Queue(SIMULATION_RESULTS_QUEUE, true);
    }
}
