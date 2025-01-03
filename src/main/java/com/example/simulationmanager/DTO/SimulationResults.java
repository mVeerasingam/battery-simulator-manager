package com.example.simulationmanager.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimulationResults {
    @JsonProperty("task_id")
    private String task_id;

    private String status;

    private Map<String, Map<String, Object>> results;

    private String error;
}
