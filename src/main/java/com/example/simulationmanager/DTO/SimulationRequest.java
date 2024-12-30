package com.example.simulationmanager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimulationRequest {
    // will assign a uuid later - right now that's done in python
    // Equivalent Circuit Model (ECM) of a battery
    private EquivalentCircuitModel equivalentCircuitModel;
}
