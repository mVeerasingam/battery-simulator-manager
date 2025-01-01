package com.example.simulationmanager;

import com.example.simulationmanager.DTO.SimulationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimulationController {

    private final SimulationProducer simulationProducer;

    @Autowired
    public SimulationController(SimulationProducer simulationService) {
        this.simulationProducer = simulationService;
    }

    @PostMapping("/simulate")
    public String startSimulation(@RequestBody SimulationRequest simulationRequest) {
        System.out.println("Received SimulationRequest: " + simulationRequest);
        simulationProducer.sendSimulationRequest(simulationRequest);
        return "Simulation producer is disabled.";
    }
}