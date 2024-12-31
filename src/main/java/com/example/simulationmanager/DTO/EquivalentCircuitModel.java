package com.example.simulationmanager.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquivalentCircuitModel {
    @JsonProperty("RC_pairs")
    private int RC_pairs;  // Example: 1 RC pair
}
