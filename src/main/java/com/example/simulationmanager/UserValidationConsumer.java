package com.example.simulationmanager;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidationConsumer {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public UserValidationConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "user_validation_response")
    public void handleUserValidationResponse(String response) {

        // Handle the response based on validation result from user creation microserivce
        // "Valid" or "Invalid"
        if ("Valid".equalsIgnoreCase(response)) {
            System.out.println("User validation successful. Proceeding with simulation.");
        } else {
            System.out.println("User validation failed. Cannot proceed with simulation.");
        }
    }
}