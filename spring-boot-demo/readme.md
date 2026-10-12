# spring-boot-demo

A lightweight Spring Boot application showcasing basic REST endpoints, a static login page, and Spring Boot Actuator. This module is part of the [sloth-java](../readme.md) multi-module workspace.

## Overview

The demo application exposes two simple REST endpoints and serves a static login page that interacts with the login API.

## Endpoints

| Method | Path | Description | Response |
|--------|------|-------------|----------|
| `GET` | `/api/hello` | Returns a greeting message | `{"message": "Hello from spring-boot-demo"}` |
| `POST` | `/api/login` | Authenticates with username and password | `{"message": "login successful"}` or `{"message": "wrong credentials"}` |

The login endpoint accepts `application/x-www-form-urlencoded` body parameters:
- `username` — expected value: `username`
- `password` — expected value: `password`

### Actuator Endpoints

Spring Boot Actuator is configured to expose:

| Path | Description |
|------|-------------|
| `/actuator/health` | Application health status |
| `/actuator/info` | Application info |

### Static Page

Opening the root URL (`/`) serves a simple login form (`index.html`) that submits credentials to `/api/login` via fetch.

## Tech Stack

- **Java 25**
- **Spring Boot 4.1.1**
- **Spring Web** — REST controllers
- **Spring Boot Actuator** — health and info endpoints
- **Spring Boot Starter Test** — JUnit 5 and MockMvc testing

## Quick Start

### Prerequisites

- JDK 25+
- Maven 3.9+

### Run the Application

```bash
# From the repository root
mvn -pl spring-boot-demo spring-boot:run
```

The app starts on `http://localhost:8080` by default.

### Test the Endpoints

```bash
# Greeting endpoint
curl http://localhost:8080/api/hello

# Login endpoint (valid credentials)
curl -X POST http://localhost:8080/api/login -d "username=username&password=password"

# Health check
curl http://localhost:8080/actuator/health
```

### Run Tests

```bash
mvn -pl spring-boot-demo test
```

## Project Structure

```
spring-boot-demo/
├── pom.xml                          # Maven build configuration
└── src/
    ├── main/
    │   ├── java/demo/
    │   │   ├── DemoApplication.java     # Spring Boot entry point
    │   │   ├── HelloController.java     # GET /api/hello
    │   │   └── LoginController.java     # POST /api/login
    │   └── resources/
    │       ├── application.yaml         # App name and actuator config
    │       └── static/
    │           ├── index.html           # Login form page
    │           └── SpringBootLoginPage.png
    └── test/java/demo/
        ├── DemoApplicationTests.java    # Context loads test
        ├── HelloControllerTest.java     # MockMvc test for /api/hello
        └── LoginControllerTest.java     # MockMvc tests for /api/login
```
