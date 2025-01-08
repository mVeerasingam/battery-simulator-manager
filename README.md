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

# Architecture Diagram:

![Architecture Diagram](cicd2_diagram%20(1).jpg)

## **User Creation Microservice**: 
- Creates a user for the application via POST ```http://localhost:8085/users/create```
- Example Post Request Body:
   - ```
     {
       "userName": "TestAccount",
       "firstName": "Test",
       "lastName": "Account",
       "email": "Test@Mail.com",
       "password": "Password1!"
     }
     ```
- A response with a ```user_id``` will be generated. **You will need this to simulate a battery**
- GET ```http://localhost:8085/users/{user_id}``` gets the details of a user account.
- GET ```http://localhost:8085/users/username/{username}``` gets the user_id by username
- DELETE ```http://localhost:8085/users/{user_id}``` deletes a user from the database
- PUT ```http://localhost:8085/users/{user_id}``` updates a users details by user_id

### **Tests Overview for User Creation Service**
#### **1. Unit Test: UserServiceTest**
The `UserServiceTest` validates core functionalities, including user creation, duplicate user detection, and user retrieval.

---

#### **Test Case: User Creation**
- **Objective**: Validate successful creation of a new user account.
- **Key Assertions**:
  - Ensures the user is saved in the repository.
  - Confirms that the created user has the correct email and username.
  - Verifies that the repository's `save` method is called once.

---

#### **Test Case: Duplicate User Creation by Email**
- **Objective**: Handle cases where a user with the same email already exists.
- **Key Assertions**:
  - Ensures `UserAlreadyExistsException` is thrown with the appropriate message.
  - Verifies that the repository's `save` method is **not** called for duplicate emails.

---

#### **Test Case: Duplicate User Creation by Username**
- **Objective**: Handle cases where a user with the same username already exists.
- **Key Assertions**:
  - Ensures `UserAlreadyExistsException` is thrown with the appropriate message.
  - Verifies that the repository's `save` method is **not** called for duplicate usernames.

---

#### **Test Case: Retrieve User by Valid ID**
- **Objective**: Validate the retrieval of an existing user by their ID.
- **Key Assertions**:
  - Ensures the retrieved user matches the expected details (ID and email).
  - Confirms that the repository's `findById` method returns the user.

---

#### **Test Case: Retrieve User by Invalid ID**
- **Objective**: Handle cases where a user with the given ID does not exist.
- **Key Assertions**:
  - Ensures `UserNotFoundException` is thrown with the correct message.
  - Confirms that the repository's `findById` method returns an empty result for non-existent users.

---

## **Simulation Manager Microservice**: 
- Sends a Post request to the **Battery Simulation API Microservice** via POST ```http://localhost:8080/simulations```
- Example Post Request Body:
   - ```
     {
        "user_id": "INSERT user_id HERE",  
        "equivalent_circuit_model": {
          "RC_pairs": 1
        },
        "parameter_values": {
          "parameter_value": "ECM_Example"
        },
        "solver": {
          "solver": "IDAKLUSolver"
        },
        "simulation": {
          "experiment": [
            "Discharge at C/10 for 1 hour or until 3.3 V",
            "Charge at 100 A until 3.1 V",
            "Hold at 4.1 V until 5 A",
            "Rest for 30 minutes",
            "Rest for 1 hour"
          ]
        },
        "display_params": [
          "Voltage [V]",
          "Current [A]",
          "Jig temperature [K]"
        ]
      }
     ```
- A ```task_id``` will be generated, this is the simulation task you just performed.     
- A Direct Exchange with RabbitMQ is used to validate if a user_id exists on the **User Creation Microservice**
   - **Note:** Simulation Manager is non-blocking and asynchronus, so a simulation will still proceed even if a result from the user validation is pending. This is because a user_id validation process took longer in most instances than it did to simulate a battery.
- After a post is made, the Simulation Request is saved to a mongoDB database on ```mongodb://mongodb_simulationManager:27017/simulationManagerDB``` 
- You can access your simulation requests with the following urls:
   - GET ```http://localhost:8080/simulations/user/{user_id}/task/{task_id}``` For a specific simulation generated for a given user
   - GET ```http://localhost:8080/simulations/user/{user_id}``` All simulations generated for a given user
- Do not be afraid of producing errors in the post request i.e. incorrect simulation experiment or invalid solver, display_params etc... a request will still be made and the **Simulation Notification Microservice** will inform you of the failure by taking the errors produced in the simulation from  the **Battery Simulation API Microservice**.

### **Test Overview for Simulation Manager Microservice**:
---
#### **1. Integration Test: SimulationControllerIntegrationTest**
This test verifies the integration between the controller, RabbitMQ, and the repository by simulating a full battery simulation request.

- **Objective**: Ensure the complete flow of a simulation request from the API endpoint (`/simulations`) works correctly. 
- **Key Assertions**:
  - Validates the API returns a successful response when a valid simulation request is made.
  - Confirms RabbitMQ is invoked to validate the user ID.
  - Checks that the simulation request is stored in MongoDB.
  - Mocks interaction with the Battery Simulation API microservice to simulate external dependencies.
---

#### **2. Unit Test: SimulationRequestTest**
---
This test validates the serialization and structure of the `SimulationRequest` object.

- **Objective**: Ensure the `SimulationRequest` object can be correctly serialized into JSON format.
- **Key Actions**:
  - Constructs a sample simulation request with predefined values for equivalent circuit model, parameters, solver, and experiment setup.
  - Serializes the request into a JSON string and outputs it for verification.
- **Use Case**: Useful for debugging and ensuring the API's payload structure matches the expectations of downstream services.
---

#### **3. Service Test: SimulationServiceTest**
---
This test validates the core logic of the `SimulationService`, including interactions with RabbitMQ, the repository, and the Battery Simulation API microservice.

- **Test Case: Simulate Battery**:
  - **Objective**: Ensure the `simulateBattery` method correctly handles a simulation request.
  - **Key Assertions**:
    - The simulation request is passed to the Battery Simulation API microservice.
    - RabbitMQ is invoked to send user validation messages.
    - The simulation request is saved to MongoDB.

- **Test Case: Simulate Battery with Task ID Generation**:
  - **Objective**: Verify that a `taskId` is correctly generated if missing in the request.
  - **Key Assertions**:
    - Ensures `taskId` is generated and set for the simulation request.
    - Validates the response returned by the Battery Simulation API microservice.
---
 
## **Battery Simulation API Microservice**:
- This is a python based battery simulation api microservice that generates a battery through equivalent circuit modelling in PyBaMM (python battery mathamatical modelling)
- An endpoint is expossed which **Simulation Manager Microservice** will post to with the above example post body.
- The simulation microservice will serialize this request and asynchronusly task queued with celery. Celery is a distributed task queueing package that uses a rabbitMQ broker so multiple users can queue simulation asynchronusly.
- Celery will perform the simulation and once completed, it is prdouces a payload of the simulation response to a RabbitMQ Queue called **simulation_results**
         
## **Simulation Notification Microservice**: 
- A rabbitMQ consumer is setup to consume from the **simulation_results** queue.
- Once a simulation in complete, regardless if the simulation was succesful or not, it's saved to ```mongodb://mongodb_simulation_results:27017/simulationResultsDB```
- GET ```http://localhost:8082/simulations/user/{user_id}/task/{task_id}``` retrieves a specific simulation for a given user
- GET ```http://localhost:8082/simulations/user/{user_id}``` retrieves all simulations performed for a given user

### **Tests Overview for Simulation Notification Service**

The Simulation Notification Service tests validate the functionality of handling simulation notifications, including retrieval, saving, and deletion of simulation results for a user.

---

#### **1. Unit Test: SimulationNotificationServiceTest**
The `SimulationNotificationServiceTest` ensures the notification service behaves as expected when interacting with the repository for managing simulation results.

---

#### **Test Case: Get Simulations by User**
- **Objective**: Retrieve all simulation results associated with a specific user.
- **Key Assertions**:
  - Ensures the correct number of simulations is returned for the user.
  - Confirms the task ID of the retrieved simulation matches expectations.
  - Verifies the repository's `findByUserId` method is called once.

---

#### **Test Case: Get Simulation by Task ID and User ID**
- **Objective**: Retrieve a specific simulation result by task ID and user ID.
- **Key Assertions**:
  - Ensures the returned simulation result is not null.
  - Confirms the retrieved simulation's status matches the expected value (`"Completed"`).
  - Verifies the repository's `findByUserIdAndTaskId` method is called once.

---

#### **Test Case: Save Simulation**
- **Objective**: Save a new simulation result to the repository.
- **Key Assertions**:
  - Ensures the saved simulation is not null.
  - Confirms the task ID of the saved simulation matches the expected value.
  - Verifies the repository's `save` method is called once.

---

#### **Test Case: Delete Simulation**
- **Objective**: Delete an existing simulation result for a specific task ID and user ID.
- **Key Assertions**:
  - Ensures the deletion operation returns a successful result (`true`).
  - Verifies the repository's `findByUserIdAndTaskId` method is called to locate the simulation.
  - Confirms the repository's `delete` method is called once to remove the simulation.

---

#### **Mocked Interactions and Dependencies**
The tests rely on the following mocked dependencies:
- **`SimulationResultsRepository`**:
  - Mocked to simulate data retrieval, saving, and deletion operations for simulation results.
- **Service Logic**:
  - Ensures proper handling of CRUD operations and their outcomes.

---
