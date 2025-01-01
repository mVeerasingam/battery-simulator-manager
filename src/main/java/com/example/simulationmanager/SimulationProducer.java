package com.example.simulationmanager;

import com.example.simulationmanager.DTO.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "producer.enabled", havingValue = "true", matchIfMissing = true)
public class SimulationProducer {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public SimulationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // Method to send the SimulationRequest object as a message to RabbitMQ
    public void sendSimulationRequest(SimulationRequest simulationRequest) {
        try {
            // Convert the SimulationRequest object to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String simulationRequestJson = objectMapper.writeValueAsString(simulationRequest);

            // Send the JSON to RabbitMQ
            rabbitTemplate.convertAndSend("celery", simulationRequestJson);
            System.out.println("Sent simulation request: " + simulationRequestJson);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error converting SimulationRequest to JSON: " + e.getMessage());
        }
    }
}