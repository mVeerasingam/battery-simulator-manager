package com.example.simulationmanager;

import com.example.simulationmanager.DTO.SimulationRequest;
import com.example.simulationmanager.DTO.EquivalentCircuitModel;
import com.example.simulationmanager.DTO.ParameterValues;
import com.example.simulationmanager.DTO.Solver;
import com.example.simulationmanager.DTO.SimulationType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class SimulationRequestTest {

    @Test
    public void testSimulationRequestSerialization() throws Exception {
        // Construct the SimulationRequest object
        SimulationRequest request = new SimulationRequest();

        // Set EquivalentCircuitModel
        EquivalentCircuitModel equivalentCircuitModel = new EquivalentCircuitModel();
        equivalentCircuitModel.setRC_pairs(1);
        request.setEquivalentCircuitModel(equivalentCircuitModel);

        // Set ParameterValues
        ParameterValues parameterValues = new ParameterValues();
        parameterValues.setBpx(false);
        parameterValues.setParameterValue("ECM_Example");

        request.setParameterValues(parameterValues);

        // Set Solver
        Solver solver = new Solver();
        solver.setSolver("IDAKLUSolver");
        request.setSolver(solver);

        // Set Simulation
        SimulationType simulation = new SimulationType();
        simulation.setExperiment(Arrays.asList(
                "Discharge at C/10 for 1 hour or until 3.3 V",
                "Rest for 30 minutes",
                "Rest for 2 hours",
                "Charge at 100 A until 4.1 V",
                "Hold at 4.1 V until 5 A",
                "Rest for 30 minutes",
                "Rest for 1 hour"
        ));
        simulation.setT_eval(Arrays.asList(0, 3600));

        request.setSimulation(simulation);

        // Set DisplayParams
        request.setDisplayParams(Arrays.asList("Voltage [V]", "Current [A]", "Jig temperature [K]"));

        // Serialize to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(request);

        // Print JSON to validate
        System.out.println(jsonString);
    }
}
