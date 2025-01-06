package com.example.simulationmanager;

import com.example.simulationmanager.DTO.SimulationRequest;
import feign.Param;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SimulationRequestRepository extends MongoRepository<SimulationRequest, Long> {
    List<SimulationRequest> findByUserId(String userId);
    Optional<SimulationRequest> findByTaskIdAndUserId(String taskId, String userId);
}
