package com.example.simulationmanager;

import com.example.simulationmanager.DTO.SimulationRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SimulationRequestRepository extends MongoRepository<SimulationRequest, Long> {
}
