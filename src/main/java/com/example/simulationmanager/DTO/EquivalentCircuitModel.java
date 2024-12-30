package com.example.simulationmanager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquivalentCircuitModel {
    // Equivalent Circuit Model (ECM)
    private int RC_pairs; // e.g. 1 or 2 RC series pairs in the ECM

    // Parameter values
    private String parameterValue; // this will always be "ECM_Example"

    // Solver (ODE or PDE)
    private String solver; // e.g. "IDAKLUSolver", "CasadiSolver"

    // SimulationType setup
    private SimulationType simulationType;

    // Display the parameters you want to see when you simulate
    private List<String> displayParams; // "Voltage [V]", "Current [A]", "Jig temperature [K]"
}
