package com.example.simulationmanager;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "consumer.enabled", havingValue = "true")
public class SimulationConsumer {

}