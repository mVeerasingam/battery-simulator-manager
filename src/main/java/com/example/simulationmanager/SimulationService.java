package com.example.simulationmanager;

import com.example.simulationmanager.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimulationService {

    private final SimulationClient simulationClient;

    @Autowired
    public SimulationService(SimulationClient simulationClient) {
        this.simulationClient = simulationClient;
    }

    public String simulateBattery(SimulationRequest simulationRequest) {
        return simulationClient.runSimulation(simulationRequest);
    }
}

