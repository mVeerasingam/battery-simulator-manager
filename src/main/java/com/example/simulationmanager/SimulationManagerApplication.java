package com.example.simulationmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SimulationManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimulationManagerApplication.class, args);
    }

}
