
# Wallet-microservices


## Installation, Testing, and Execution

### Installation
To set up the project, follow these steps:

`Prerequisites:`

- Ensure you have Java 21 installed on your machine
- Install Maven to manage dependencies and build the project.
- Install Docker and Docker Compose to run the required dependencies (e.g., Kafka).

2. `Installing Dependencies:`

- Navigate to the root directories of the following microservices: wallet, authorization, gateway, and serviceRegistry.
- Run the following command in each directory to install the dependencies:

```
mvn clean install -DskipTests
```
- This command will compile the project and install the necessary dependencies without running the tests.

3. `Database Setup:`

- The wallet and authorization microservices use the H2 in-memory database, so no additional setup is required for the database.


### Testing
The wallet microservice includes automated integration tests. You can run these tests in two ways:

`Using an IDE:`

- Open the project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse).

- Locate the test files in the src/test directory and run them directly from the IDE.

`Using the Terminal:`

- Navigate to the wallet microservice directory.

- Run the following command to execute the tests:

```
mvn test
```

### Execution
To run the microservices, follow these steps:

1. Start Dependencies:

- Before running the wallet microservice, you need to start the Kafka dependencies using Docker Compose.

- Navigate to the root directory of the project and run:
```
docker-compose up
```
- This command will start Kafka and any other required services.

2. Running the Microservices:

- Each microservice can be started individually using the following command:

```
mvn spring-boot:run
```
- Run this command in the directories of the wallet, authorization, gateway, and serviceRegistry microservices.

3. Order of Execution:

Start the microservices in the following order:

- ServiceRegistry

- Gateway

- Authorization

- Wallet

4. Accessing the Services:

- The wallet microservice provides a Swagger UI for API documentation, which can be accessed at:

```
http://localhost:8082/swagger-ui.html
```

The H2 database console for the wallet microservice can be accessed at:

```
http://localhost:8082/h2-console/
```

The necessary connection settings for the H2 database are defined in the application.yml file.


## Design Choices and Implementation
`Functional Requirements`

The project was designed to meet the following functional requirements:

- Create Wallet: Allow users to create wallets.

- Retrieve Balance: Retrieve the current balance of a user's wallet.

- Retrieve Historical Balance: Retrieve the balance of a user's wallet at a specific point in the past.

- Deposit Funds: Allow users to deposit money into their wallets.

- Withdraw Funds: Allow users to withdraw money from their wallets.

- Transfer Funds: Facilitate money transfers between user wallets.

The architecture is based on microservices, utilizing the following components:

- Authorization Service: Handles transaction authorization.

- API Gateway: Manages routing and API requests.

- Service Registry: Facilitates service discovery.

- Wallet Service: Manages wallet operations.

`Non-Functional Requirements`

Given the critical nature of the service, the following non-functional requirements were addressed:

- Authorization Service: The Authorization microservice maintains a list of blocked users. Transactions involving users on this list are not permitted.

- Transaction Types: The system supports three types of transactions: DEPOSIT, WITHDRAW, and TRANSFER. TRANSFER transactions are sent asynchronously to a Kafka topic to ensure non-blocking operations.

`Design Patterns and Principles``

- SOLID Principles: The code adheres to SOLID principles to enhance maintainability and scalability.

- Strategy Pattern: This pattern is used to define transaction types (DEPOSIT, WITHDRAW, TRANSFER) at runtime. It is also used in the retrieveHistoricalBalance functionality to calculate balances, though this may require refactoring in the future.
# Trade-offs and Future Improvements

Due to time constraints, the following trade-offs were made:

- Domain Classes: To reduce boilerplate code, domain classes were implemented using Java Records. However, future updates could separate business logic from framework-specific code by using traditional domain classes.

- DTO Validation: DTOs could be enhanced with bean validation to ensure data integrity.

- Test Coverage: Integration tests do not cover all scenarios. Expanding test coverage is a priority for future updates.

- Kafka Integration: Currently, only TRANSFER transactions are sent to Kafka. Future updates could include DEPOSIT and WITHDRAW transactions as well.

- Historical Balance Calculation: The retrieveHistoricalBalance functionality uses the Strategy pattern, which may require refactoring for better performance and maintainability.

# Future Work

- Enhanced Validation: Implement bean validation for DTOs to ensure data integrity.

- Improved Test Coverage: Expand integration tests to cover all scenarios.

- Kafka Integration: Extend Kafka integration to include DEPOSIT and WITHDRAW transactions.

- Refactoring: Refactor the retrieveHistoricalBalance functionality for better performance and maintainability.
