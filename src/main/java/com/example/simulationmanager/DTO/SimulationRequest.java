package com.example.simulationmanager.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimulationRequest {
    @JsonProperty("equivalent_circuit_model")
    private EquivalentCircuitModel equivalentCircuitModel;

    @JsonProperty("parameter_values")
    private ParameterValues parameterValues;

    @JsonProperty("solver")
    private Solver solver;

    @JsonProperty("simulation")
    private SimulationType simulation;

    @JsonProperty("display_params")
    private List<String> displayParams;
}
