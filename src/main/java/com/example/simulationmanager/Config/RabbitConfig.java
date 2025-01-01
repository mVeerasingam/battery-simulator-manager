package com.example.simulationmanager.Config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue simulationQueue() {
        return new Queue("celery", true);
    }
}
