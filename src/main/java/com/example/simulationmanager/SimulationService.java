package com.example.simulationmanager;

import com.example.simulationmanager.DTO.EquivalentCircuitModel;
import com.example.simulationmanager.DTO.SimulationRequest;
import com.example.simulationmanager.DTO.SimulationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimulationService {
    private final SimulationClient simulationClient;
    @Autowired
    public SimulationService(SimulationClient simulationClient) {
        this.simulationClient = simulationClient;
    }

    // mocking the simulation post idea.... just want to establish connection and to simualte the battery
    public void simulateBattery(){
        // Create SimulationType (experiment)
        SimulationType simulationType = new SimulationType();
        simulationType.setExperiment(List.of(
                "Discharge at C/10 for 1 hour or until 3.3 V",
                "Rest for 30 minutes",
                "Rest for 2 hours",
                "Charge at 100 A until 4.1 V",
                "Hold at 4.1 V until 5 A",
                "Rest for 30 minutes",
                "Rest for 1 hour"
        ));

        // Create EquivalentCircuitModel with the simulation details
        EquivalentCircuitModel batteryModel = new EquivalentCircuitModel();
        batteryModel.setRC_pairs(1);  // Example: 1 RC pair for ECM
        batteryModel.setParameterValue("ECM_Example");  // ECM example parameter
        batteryModel.setSolver("IDAKLUSolver");  // Solver type
        batteryModel.setSimulationType(simulationType);  // Set simulation type
        batteryModel.setDisplayParams(List.of("Voltage [V]", "Current [A]", "Jig temperature [K]"));

        // Create the SimulationRequest using the EquivalentCircuitModel
        SimulationRequest simulationRequest = new SimulationRequest();
        simulationRequest.setEquivalentCircuitModel(batteryModel);

        // Send request to the simulation API (you will have the API client call here)
        simulationClient.runSimulation(simulationRequest);
    }
}
