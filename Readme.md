# Task Manager API

<p>
  <img src="https://img.shields.io/github/actions/workflow/status/mertso13/task-manager-api/maven.yml?branch=main&label=tests" alt="Build Status"/>
  <img src="https://img.shields.io/badge/Java-25-blue.svg" alt="Java 25"/>
  <img src="https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=fff"alt="Docker"/>
  <img src="https://img.shields.io/badge/License-MIT-green.svg" alt="License"/>
</p>

## Project Overview
Although we frequently used Java in class, I didn't have a personal project. 

Inspired by courses I recently completed, such as Software Design and Architecture and Software Engineering Management, I created a simple Task Management API using the MVC design pattern to test whether design patterns really work.

## Features
- Full support for CRUD operations on tasks.
- Handles edge cases such as invalid input and requests for non-existent tasks.
- Globalized error handling ensures user-friendly and informative error messages.
- In-memory H2 database for easy setup and testing.
- API documentation available through Swagger/OpenAPI.
- Unit tests implemented with JUnit and Mockito.
- Uses Lombok to reduce boilerplate code.
- Supports task status tracking (TODO, IN_PROGRESS, DONE).

## Technologies Used
- Java 25
- Spring Boot
- Spring Data JPA
- H2 Database
- Lombok
- Maven
- Swagger / OpenAPI
- JUnit, Mockito

## Architecture & Design
As I mentioned in the Project Overview, I tried to adhere to the MVC design pattern as much as possible, but since this is an API, there is no “View” part.

The Controller is the layer that handles communication with the client, managing HTTP requests and responses. It maps incoming requests to specific endpoints, validates input and determines the appropriate actions. After processing the request, it forwards it to the Service layer for business logic execution. 

The Service layer contains the core business logic of the application. It processes data, applies rules, and coordinates actions between the Controller and Repository. The Service ensures that operations are performed correctly, handles complex workflows, and enforces business constraints. It acts as an intermediary, isolating the Controller from direct data access and providing a clean interface for application functionality.

The Repository is dedicated to database operations such as fetching, storing, updating and deleting data. It abstracts the data access layer, allowing the Service to interact with the database without worrying about implementation details. The Repository uses Spring Data JPA to provide convenient methods for CRUD operations and custom queries, ensuring efficient and reliable data management.

The Model represents the application's data structure. In this project, it includes entities like Task and TaskStatus, which define the properties and relationships of tasks managed by the API. Models are used by the Repository for database operations and by the Service for business logic.

Exception Handling ensures that errors are managed gracefully.

The GlobalExceptionHandler class centralizes error handling, catching exceptions thrown by Controllers or Services and returning meaningful error responses to the client. This improves API reliability and user experience by providing clear feedback when something goes wrong.



## API Endpoints
| Method | Endpoint | Description |
|---     |---       |---          |
| GET	 | /api/v1/tasks |	Get all tasks |
| GET	| /api/v1/tasks/{id} | Get task by ID |
| POST | /api/v1/tasks | Create new task |
| PUT |	/api/v1/tasks/{id} | Update existing task |
| DELETE | /api/v1/tasks/{id} | Delete task |

## API Documentation
Interactive API documentation is available through Swagger UI. After starting the application, you can access the documentation by visiting [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) in your web browser.

If you don't want to bother, I've put how it looks below:
![API Documentation](images/API_Documentation.jpg)

## Getting Started
### Prerequisites
- Java 25+
- Maven
### Installation & Setup
```console
foo@bar:~$ git clone git@github.com:mertso13/task-manager-api.git
foo@bar:~$ cd task-manager-api
foo@bar:~/task-manager-api$ ./mvnw clean install
foo@bar:~/task-manager-api$ ./mvnw spring-boot:run
```
