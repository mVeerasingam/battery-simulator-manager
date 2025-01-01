package com.example.simulationmanager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimulationResults {
    private String task_id;
    private String status;
    private Map<String, Object> results;
    private String error;
}
