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
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("task_id")
    private String taskId;

    private String status;

    private Map<String, Map<String, Object>> results;

    private String error;
}
