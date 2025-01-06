package com.example.simulationmanager;

import com.example.simulationmanager.DTO.SimulationRequest;
import com.example.simulationmanager.DTO.EquivalentCircuitModel;
import com.example.simulationmanager.DTO.ParameterValues;
import com.example.simulationmanager.DTO.Solver;
import com.example.simulationmanager.DTO.SimulationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SimulationServiceTest {

    @Mock
    private SimulationClient simulationClient;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private SimulationRequestRepository repository;

    @InjectMocks
    private SimulationService simulationService;

    private SimulationRequest simulationRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Prepare the SimulationRequest
        simulationRequest = new SimulationRequest();

        EquivalentCircuitModel equivalentCircuitModel = new EquivalentCircuitModel();
        equivalentCircuitModel.setRC_pairs(1);
        simulationRequest.setEquivalentCircuitModel(equivalentCircuitModel);

        ParameterValues parameterValues = new ParameterValues();
        parameterValues.setBpx(false);
        parameterValues.setParameterValue("ECM_Example");
        simulationRequest.setParameterValues(parameterValues);

        Solver solver = new Solver();
        solver.setSolver("IDAKLUSolver");
        simulationRequest.setSolver(solver);

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
        simulation.setT_eval(Arrays.asList(0, 3600)); // 1hr simulation
        simulationRequest.setSimulation(simulation);

        simulationRequest.setDisplayParams(Arrays.asList("Voltage [V]", "Current [A]", "Jig temperature [K]"));
    }

    @Test
    void testSimulateBattery() {
        // Mocking the void method with doNothing bypass rabiitMQ
        doNothing().when(rabbitTemplate).convertAndSend(anyString(), anyString(), anyString());

        when(simulationClient.runSimulation(any(SimulationRequest.class))).thenReturn("Success");

        String response = simulationService.simulateBattery(simulationRequest);

        assertEquals("Success", response);
        verify(repository, times(1)).save(simulationRequest);
    }


    @Test
    void testSimulateBatteryWithTaskIdGeneration() {
        simulationRequest.setTaskId(null); // Simulate no taskId present

        when(simulationClient.runSimulation(any(SimulationRequest.class))).thenReturn("Success");

        String response = simulationService.simulateBattery(simulationRequest);

        assertNotNull(simulationRequest.getTaskId());  // Task ID should be generated
        assertEquals("Success", response);
    }
}