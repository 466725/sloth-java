# spring-boot-demo

A Spring Boot API and Next.js login UI, with Spring Boot Actuator. This module is part of the [sloth-java](../readme.md) multi-module workspace.

## Overview

The demo application exposes two REST endpoints and serves a Next.js static export when packaged. During development, run the Next.js dev server separately; it proxies API requests to Spring Boot.

## Endpoints

| Method   | Path         | Description                              | Response                                                                |
|----------|--------------|------------------------------------------|-------------------------------------------------------------------------|
| `GET`    | `/api/hello` | Returns a greeting message               | `{"message": "Hello from spring-boot-demo"}`                            |
| `POST`   | `/api/login` | Authenticates with username and password | `{"message": "login successful"}` or `{"message": "wrong credentials"}` |

The login endpoint accepts `application/x-www-form-urlencoded` body parameters:

- `username` — expected value: `username`
- `password` — expected value: `password`

### Actuator Endpoints

Spring Boot Actuator is configured to expose:

| Path               | Description               |
|--------------------|---------------------------|
| `/actuator/health` | Application health status |
| `/actuator/info`   | Application info          |

### Next.js UI

The `ui/` directory contains the Next.js App Router application. Its login form submits credentials to `/api/login`. The Next.js dev server proxies `/api/*` to `http://localhost:8080`; Maven packages the static export into the Spring Boot JAR.

## Tech Stack

- **Java 25**
- **Spring Boot 4.1.2**
- **Spring Web** — REST controllers
- **Spring Boot Actuator** — health and info endpoints
- **Spring Boot Starter Test** — JUnit 5 and MockMvc testing
- **Next.js, React, and TypeScript** — UI

## Quick Start

### Prerequisites

- JDK 25+
- Maven 3.9+
- Node.js 24+ and npm 11+ (for UI development; Maven downloads these versions when packaging)

### Run in Development

```bash
# Terminal 1: start the API
mvn -pl spring-boot-demo spring-boot:run

# Terminal 2: start the Next.js UI
cd spring-boot-demo/ui
npm ci
npm run dev
```

Open `http://localhost:3000`. The Spring Boot API is available at `http://localhost:8080`.

### Package and Run

```bash
# From the repository root; the Maven build creates and includes the UI export
mvn -pl spring-boot-demo package
java -jar spring-boot-demo/target/spring-boot-demo-0.0.1-SNAPSHOT.jar
```

The packaged app serves the UI and API together at `http://localhost:8080`.

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

```text
spring-boot-demo/
├── pom.xml                          # Maven build configuration, including UI packaging
├── ui/                              # Next.js application
│   ├── app/                         # App Router pages and styles
│   ├── next.config.ts               # Static export and development API proxy
│   └── package.json
└── src/
    ├── main/
    │   ├── java/demo/
    │   │   ├── DemoApplication.java     # Spring Boot entry point
    │   │   ├── HelloController.java     # GET /api/hello
    │   │   └── LoginController.java     # POST /api/login
    │   └── resources/
    │       ├── application.yaml         # App name and actuator config
    │       └── static/
    │           └── SpringBootLoginPage.png
    └── test/java/demo/
        ├── DemoApplicationTests.java    # Context loads test
        ├── HelloControllerTest.java     # MockMvc test for /api/hello
        └── LoginControllerTest.java     # MockMvc tests for /api/login
```
