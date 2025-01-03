package com.example.simulationmanager;

import com.example.simulationmanager.DTO.SimulationResults;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SimulationResultRepository extends MongoRepository<SimulationResults, Long> {
}
