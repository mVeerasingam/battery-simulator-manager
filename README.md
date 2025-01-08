# Battery Simulation Manager

## Documentation

### Prerequisites

Ensure the following prerequisites are met to get started:

1. Clone the required repositories:
   - [**Simulation Manager Microservice**](https://github.com/mVeerasingam/battery-simulator-manager/tree/master)
   - [**User Creation Microservice**](https://github.com/mVeerasingam)
   - [**Simulation Notification Microservice**](https://github.com/mVeerasingam/battery-simulator-notification-listener)
   - [**Battery Simulation API Microservice**](https://github.com/MarkVeerasingam/Battery-Simulator)

2. Install **Docker** and **Docker Desktop** on your system.

---

### How to Set Up the Application

#### Step 1: Clone the Repositories
- Clone the above microservices into a single folder on your local machine.

#### Step 2: Build Docker Images for Each Microservice
To build Docker images for the microservices, follow these steps:

1. Open a command prompt (CMD).
2. Navigate to the repository folder of each microservice you cloned.
3. Execute the corresponding `docker build` command:

   - **Simulation Manager Microservice**:  
     ```
     docker build -t simulation_manager:latest .
     ```

   - **User Creation Microservice**:  
     ```
     docker build -t user_creation_battery_simulator:latest .
     ```

   - **Simulation Notification Microservice**:  
     ```
     docker build -t simulation_notification_service:latest .
     ```

   - **Battery Simulation API Microservice**:  
     ```
     docker build -t batterysimulator:latest .
     ```

#### Step 3: Build Docker-Compose
To buld the docker-compose file...

1. Navigate to the location of folder where you cloned the [**Simulation Manager Microservice**](https://github.com/mVeerasingam/battery-simulator-manager/tree/master)
2. Ensure that the `docker-compose.yml` file is present in this directory.
3.  Open a command prompt (CMD) in this directory and execute the following command to build and start all services:
   ```
   docker-compose up --build
   ```
4. Docker Compose will:
  - Start containers for each microservice and the required dependencies (e.g., RabbitMQ, MongoDB, PostgreSQL).
5. Once the services are up, you should see logs from all the running containers. You can access the services via the ports specified in the `docker-compose.yml` file.

Default Ports Specified:
   - **Simulation Manager Microservice**: 8080
   - **User Creation Microservice**: 8085
   - **Simulation Notification Microservice**: 8082
   - **Battery Simulation API Microservice**: 8084 
---
