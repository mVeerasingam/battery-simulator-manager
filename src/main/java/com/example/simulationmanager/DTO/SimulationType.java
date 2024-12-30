package com.example.simulationmanager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SimulationType {
    // all simulation types available in the battery simulation api are documented in my github repo below
    // this class really just handles the needed data type + structuring of it to be encapsulated
    // https://github.com/MarkVeerasingam/Battery-Simulator/tree/main?tab=readme-ov-file#performing-various-simultion-types:~:text=Performing%20various%20simultion%20types

    private List<String> experiment;  // e.g. "Discharge at C/10 for 1 hour or until 3.3 V", "Rest for 30 minutes"...

    private List<Integer> t_eval;  // Time evaluation (e.g. [0, 3600] for a 1-hour simulation)

    private Map<String, String> driveCycle;  // drive cycle configurations (e.g., {"drive_cycle_file": "LFP_25degC_DriveCycle"})
}
