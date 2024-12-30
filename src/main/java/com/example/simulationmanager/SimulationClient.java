package com.example.simulationmanager;

import com.example.simulationmanager.DTO.SimulationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "battery-simulation-api", url = "http://localhost:8084")
public interface SimulationClient {
    @PostMapping("/simulate/ecm")
    String runSimulation(@RequestBody SimulationRequest simulationRequest);
}
