package com.example.simulationmanager;

import com.example.simulationmanager.DTO.SimulationRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SimulationService {
    private SimulationClient simulationClient;
    private final RabbitTemplate rabbitTemplate;


    @Autowired
    public SimulationService(SimulationClient simulationClient, RabbitTemplate rabbitTemplate) {
        this.simulationClient = simulationClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    public String simulateBattery(SimulationRequest simulationRequest) {
        // ensures that task_id will always be generated.
        if (simulationRequest.getTaskId() == null) {
            simulationRequest.setTaskId(UUID.randomUUID().toString());
        }

        // Send user validation request to the exchange
        String userId = simulationRequest.getUserId();
        rabbitTemplate.convertAndSend("user_validation_exchange", "user.validation.request", userId);

        System.out.println("Sending simulation request with task_id: " + simulationRequest.getTaskId() +
                " with user_id " + simulationRequest.getUserId());
        return simulationClient.runSimulation(simulationRequest);
    }
}

