package com.example.simulationmanager;

import com.example.simulationmanager.DTO.SimulationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/simulations")
public class SimulationController {

    private final SimulationService simulationService;
    private final ManagerService managerService;

    @Autowired
    public SimulationController(SimulationService simulationService, ManagerService managerService) {
        this.simulationService = simulationService;
        this.managerService = managerService;
    }

    @PostMapping
    public ResponseEntity<String> simulate(@RequestBody SimulationRequest simulationRequest) {
        String response = simulationService.simulateBattery(simulationRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SimulationRequest>> getSimulationsByUser(@PathVariable String userId) {
        List<SimulationRequest> simulations = managerService.getSimulationsByUser(userId);
        if (simulations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(simulations);
    }

    @GetMapping("/{taskId}/user/{userId}")
    public ResponseEntity<SimulationRequest> getSimulationByTaskIdAndUserId(@PathVariable String taskId, @PathVariable String userId) {
        Optional<SimulationRequest> simulationRequest = managerService.getSimulationByTaskIdAndUserId(taskId, userId);
        return simulationRequest.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{taskId}/user/{userId}")
    public ResponseEntity<Void> deleteSimulation(@PathVariable String taskId, @PathVariable String userId) {
        boolean deleted = managerService.deleteSimulation(taskId, userId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}