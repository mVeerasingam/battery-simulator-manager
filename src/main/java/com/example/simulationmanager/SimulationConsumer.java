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
            // Deserialize the message into SimulationResults object
            SimulationResults results = objectMapper.readValue(message, SimulationResults.class);

            // Log the received message
            System.out.println("Received SimulationResults for Task ID: " + results.getTask_id());
            System.out.println("Status: " + results.getStatus());

            // Get the results as a Map
            Map<String, Map<String, Object>> resultsMap = results.getResults();

            // Log the results
            if (resultsMap != null) {
                resultsMap.forEach((time, data) -> {
                    System.out.println("Time: " + time);
                    data.forEach((key, value) -> {
                        System.out.println("  " + key + ": " + value);
                    });
                });
            }

            // Save the results directly to MongoDB
            repository.save(results);  // Save the entire SimulationResults document to MongoDB

            System.out.println("Saved SimulationResult with Task ID: " + results.getTask_id());

        } catch (Exception e) {
            // Handle errors in deserialization and processing
            System.err.println("Failed to process message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
