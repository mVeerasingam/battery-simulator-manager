package com.example.simulationmanager;

import com.example.simulationmanager.DTO.SimulationRequest;
import com.example.simulationmanager.DTO.EquivalentCircuitModel;
import com.example.simulationmanager.DTO.ParameterValues;
import com.example.simulationmanager.DTO.Solver;
import com.example.simulationmanager.DTO.SimulationType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class SimulationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SimulationClient simulationClient;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private SimulationRequestRepository repository;

    @Test
    public void testSimulateBatteryIntegration() throws Exception {
        SimulationRequest request = new SimulationRequest();

        request.setUserId("test-user");

        // Set the EquivalentCircuitModel - note this shouldn't be changed
        EquivalentCircuitModel equivalentCircuitModel = new EquivalentCircuitModel();
        equivalentCircuitModel.setRC_pairs(1);
        request.setEquivalentCircuitModel(equivalentCircuitModel);

        // Set the ParameterValues - note "ECM_Example" is the only supported parameter set
        ParameterValues parameterValues = new ParameterValues();
        parameterValues.setBpx(false);
        parameterValues.setParameterValue("ECM_Example");
        request.setParameterValues(parameterValues);

        // Set Solver "IDAKLUSolver" or "CasadiSolver"
        Solver solver = new Solver();
        solver.setSolver("IDAKLUSolver");
        request.setSolver(solver);

        // Set Simulation
        SimulationType simulation = new SimulationType();
        // experiement simulation. if this is wrong, rabbitMQ would respond with a recomended string based experiment
        simulation.setExperiment(Arrays.asList(
                "Discharge at C/10 for 1 hour or until 3.3 V",
                "Rest for 30 minutes",
                "Charge at 100 A until 4.1 V",
                "Hold at 4.1 V until 5 A",
                "Rest for 30 minutes"
        ));
        simulation.setT_eval(Arrays.asList(0, 3600)); // 1 hr simulation
        request.setSimulation(simulation);

        // Set DisplayParams
        request.setDisplayParams(Arrays.asList("Voltage [V]", "Current [A]", "Jig temperature [K]"));

        String mockResponse = "Simulation successful!";
        Mockito.when(simulationClient.runSimulation(any(SimulationRequest.class)))
                .thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/simulations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(mockResponse));

        // mock open feign connection to the batterySimulator API
        Mockito.verify(simulationClient, Mockito.times(1)).runSimulation(any(SimulationRequest.class));
        // mock the convertAndSend Direct Exchange on rabbitMQ. in action this exaction is between SimulationManager and User Creation
        Mockito.verify(rabbitTemplate, Mockito.times(1))
                .convertAndSend("user_validation_exchange", "user.validation.request", "test-user");
        Mockito.verify(repository, Mockito.times(1)).save(any(SimulationRequest.class));
    }
}
