package com.example.simulationmanager.DTO;

import java.util.Map;

public class SimulationRequest {
    private String taskId;
    private String simulationType;
    private Map<String, Object> simulationConfig; // Simulation configuration details (parameters, solver, etc.)
}
