package com.example.simulationmanager;

import com.example.simulationmanager.DTO.SimulationResults;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class SimulationConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "simulation_results")
    public void consumeMessage(String message) {
        try {
            // Log the raw message
            System.out.println("Received message: " + message);

            // Deserialize the message into SimulationResult
            SimulationResults results = objectMapper.readValue(message, SimulationResults.class);

            // Print the result
            System.out.println("Task ID: " + results.getTask_id());
            System.out.println("Status: " + results.getStatus());

            // Log the nested results
            if (results.getResults() != null) {
                results.getResults().forEach((time, data) -> {
                    System.out.println("Time: " + time);
                    data.forEach((key, value) -> {
                        System.out.println("  " + key + ": " + value);
                    });
                });
            }

            if (results.getError() != null) {
                System.err.println("Error: " + results.getError());
            }

        } catch (Exception e) {
            System.err.println("Failed to process message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
