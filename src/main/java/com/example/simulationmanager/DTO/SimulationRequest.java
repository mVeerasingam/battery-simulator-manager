package com.example.simulationmanager.DTO;

import java.util.List;
import java.util.Map;

public class SimulationRequest {
    private Map<String, Integer> equivalentCircuitModel; // { "RC_pairs": 1 }
    private Map<String, String> parameterValues;         // { "parameter_value": "ECM_Example" }
    private Map<String, String> solver;                 // { "solver": "IDAKLUSolver" }
    private Map<String, Object> simulation;             // flexible enough to support main post body of simTypes https://github.com/MarkVeerasingam/Battery-Simulator/tree/main?tab=readme-ov-file#performing-various-simultion-types:~:text=Performing%20various%20simultion%20types
    private List<String> displayParams;                 // ["Voltage [V]", "Current [A]", ...]
}
