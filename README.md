# Sloth Java

[![Java Version](https://img.shields.io/badge/Java-25-orange.svg)](https://www.oracle.com/java/technologies/downloads/)
[![Build Status](https://img.shields.io/badge/Build-Maven-blue.svg)](https://maven.apache.org/)

## Overview

**Sloth Java** is an automation framework designed for maximum efficiency with minimal manual effort. Just like a sloth, we believe in doing things once and doing them right through automation.

This project provides robust capabilities for load, performance, and functional testing across various protocols and platforms:

*   **Web Services:** HTTP, HTTPS, SOAP, and REST
*   **Data & Middleware:** JDBC (Database), LDAP, and JMS (Message-oriented Middleware)
*   **File Transfer:** FTP
*   **Core Java Concepts:** Built-in examples for learning polymorphism, recursion, and data types.

---

## Getting Started

### Prerequisites

Ensure you have the following installed:

*   **JDK 25** or higher
*   **Maven 3.6+**
*   **Git**
*   An IDE (IntelliJ IDEA recommended)

### Environment Setup

1.  **Clone the Repository**
    ```bash
    git clone https://github.com/your-username/sloth-java.git
    cd sloth-java
    ```

2.  **Configure Java 25**
    Ensure your `settings.xml` or project properties are set to Java 25. If using Maven via CLI:
    ```bash
    mvn clean install -Dmaven.compiler.release=25
    ```

3.  **Set Credentials (Optional)**
    If running tests on Sauce Labs, export your credentials:
    ```bash
    export SAUCE_USERNAME=<your_username>
    export SAUCE_ACCESS_KEY=<your_access_key>
    ```

---

## Code Conventions

Best practices to keep the codebase consistent and maintainable:

*   Prefer @getter and @setter annotations over manual getter/setter methods to reduce boilerplate.
*   Keep classes focused on a single responsibility; extract helpers when a class grows too large.
*   Use descriptive names for tests and methods; avoid abbreviations that obscure intent.
*   Favor constructor injection for required dependencies and avoid field mutation after construction.

---

## Running Tests

### Retry on Failure
* Retry is enabled by `FailureListener` + `FailureRetryAnalyzer`.
* Listener is registered via ServiceLoader: `src/test/resources/META-INF/services/org.testng.ITestNGListener`.
* Reruns (`testng-failed.xml`) use the same listener in IDE and CLI.

### Extent Report Config
* Report styling/metadata is loaded by `config.ExtentReportHandler`.
* Default config file: `src/main/resources/extent-report-config.xml`.
* If your changes are not reflected, confirm the file is on the test classpath and rerun the suite.

### Using the IDE
*   Import the project as a **Maven Project**.
*   Right-click on your `testRunner/suiteFiles/SanityTest.xml` (or specific test classes) and select **Run as TestNG Suite**.

### Using Command Line
```bash
# Resolve dependencies
mvn dependency:resolve

# Execute all tests
mvn clean test
```
