package com.example.simulationmanager;

import com.example.simulationmanager.DTO.SimulationResults;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SimulationConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final SimulationResultRepository repository;

    @Autowired
    public SimulationConsumer(SimulationResultRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "simulation_results")
    public void consumeMessage(String message) {
        try {
            System.out.println("Received Message: " + message);
            // Deserialize the message into SimulationResults object
            SimulationResults results = objectMapper.readValue(message, SimulationResults.class);

            // Save the results directly to MongoDB
            repository.save(results);  // Save the entire SimulationResults document to MongoDB

            System.out.println("User ID " + results.getUserId() +
                    " Saved SimulationResult with Task ID: " + results.getTaskId());

        } catch (Exception e) {
            // Handle errors in deserialization and processing
            System.err.println("Failed to process message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
