package com.example.simulationmanager;

import com.example.simulationmanager.DTO.SimulationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SimulationService {
    private SimulationClient simulationClient;

    @Autowired
    public SimulationService(SimulationClient simulationClient) {
        this.simulationClient = simulationClient;
    }

    public String simulateBattery(SimulationRequest simulationRequest) {
        simulationRequest.setTaskId(UUID.randomUUID().toString());
        simulationRequest.setUserId(UUID.randomUUID().toString());

        System.out.println("Sending simulation request with task_id: " + simulationRequest.getTaskId() +
                " with user_id " + simulationRequest.getUserId());
        return simulationClient.runSimulation(simulationRequest);
    }
}

