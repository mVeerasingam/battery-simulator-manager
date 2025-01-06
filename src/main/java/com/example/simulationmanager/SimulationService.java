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
    private final SimulationRequestRepository repository;


    @Autowired
    public SimulationService(SimulationClient simulationClient, RabbitTemplate rabbitTemplate, SimulationRequestRepository repository) {
        this.simulationClient = simulationClient;
        this.rabbitTemplate = rabbitTemplate;
        this.repository = repository;
    }

    public String simulateBattery(SimulationRequest simulationRequest) {
        // ensures that task_id will always be generated.
        if (simulationRequest.getTaskId() == null) {
            simulationRequest.setTaskId(UUID.randomUUID().toString());
        }
        String userId = simulationRequest.getUserId();

        // Send user validation request to the exchange
        rabbitTemplate.convertAndSend("user_validation_exchange", "user.validation.request", userId);

        System.out.println("Sending simulation request with task_id: " + simulationRequest.getTaskId() +
                " with user_id " + simulationRequest.getUserId());

        System.out.println("Saving SimulationRequest task_id: " + simulationRequest.getTaskId() + "for user_id: " + simulationRequest.getUserId() );
        repository.save(simulationRequest);

        return simulationClient.runSimulation(simulationRequest);
    }
}

