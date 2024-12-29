package com.example.simulationmanager.DTO;

import java.util.List;
import java.util.Map;

public class SimulationRequest {
    private Map<String, Object> equivalentCircuitModel;
    private Map<String, Object> parameterValues;
    private Map<String, Object> solver;
    private Map<String, Object> simulation;
    private List<String> displayParams;
}
