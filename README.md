# Configuration Management Service
## Task Overview
Configuration Management Service that allows teams to manage 
and distribute application configurations 
across a microservices ecosystem.

## Technical Stack
- Java 21
- Spring Boot 3.4.5
- Gradle 8.0+
- PostgresSQL 15
- Kafka
- Redis
- Docker
- Docker Compose

## Features
- RESTful endpoints for CRUD operations on configurations
  (attached postman collection)
- Publishes configuration updates to Kafka topic named "config-updates"
- Client microservice that subscribe to this topic to receive updates
- Redis caching for frequently accessed configurations.
  Cache is invalidated on POST, PUT, and DELETE.
  Performance improved, especially for high-read endpoints.
  Caches on GET requests, frequently accessed configurations based on UUID and service name.
- Database migrations using Flyway
- An efficient schema for storing configurations
  CREATE TABLE configurations (
  id UUID PRIMARY KEY,
  service_name VARCHAR(255) NOT NULL,
  config_key VARCHAR(255) NOT NULL,
  config_value VARCHAR(255) NOT NULL,
  created_at TIMESTAMP,
  updated_at TIMESTAMP
  );
- Proper indexing for fast retrieval by service name:
  CREATE INDEX idx_config_service_name ON configurations(service_name);
- Resilience: Implemented retry mechanisms for failed Kafka publications
- API documentation using Swagger/OpenAPI

## How to Run
1. Clone the repository
2. Navigate to the project directory
3. Build the projects using Gradle
   ```bash
   ./gradlew clean build
   ```
4. Start the application using Docker Compose
   ```bash
    docker-compose up
    ```