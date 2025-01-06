package com.example.simulationmanager;

import com.example.simulationmanager.DTO.SimulationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {
    private final SimulationRequestRepository repository;

    @Autowired
    public ManagerService(SimulationRequestRepository repository) {
        this.repository = repository;
    }

    public List<SimulationRequest> getSimulationsByUser(String userId) {
        return repository.findByUserId(userId);
    }

    public Optional<SimulationRequest> getSimulationByTaskIdAndUserId(String taskId, String userId) {
        return repository.findByTaskIdAndUserId(taskId, userId);
    }

    public boolean deleteSimulation(String taskId, String userId) {
        Optional<SimulationRequest> simulationRequest = repository.findByTaskIdAndUserId(taskId, userId);
        if (simulationRequest.isPresent()) {
            repository.delete(simulationRequest.get());
            return true;
        }
        return false;
    }
}
