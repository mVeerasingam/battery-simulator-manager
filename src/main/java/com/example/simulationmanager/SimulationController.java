package com.example.simulationmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimulationController {
    private final SimulationService simulationService;
    @Autowired
    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }
    @PostMapping("/simulate")
    public String startSimulation() {
        simulationService.simulateBattery();
        return "Simulation started";
    }
}
