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
    If calling API with a subscription key, add the key to os environment variables (e.g., API_MANAGER_SUBSCRIPTION_KEY)

---

## Code Conventions

Best practices to keep the codebase consistent and maintainable:

*   Prefer @getter and @setter annotations over manual getter/setter methods to reduce boilerplate.
*   Keep classes focused on a single responsibility; extract helpers when a class grows too large.
*   Keep methods short and focused on a single task.
*   Use descriptive names for tests and methods; avoid abbreviations that obscure intent.
*   Favor constructor injection for required dependencies and avoid field mutation after construction.

---

## Configuration & Secrets
*   All configuration is done via `src/main/resources/config.properties`.
*   Secrets are stored in `src/main/resources/secrets.properties`.
*   Load secrets from environment variables (e.g., API_KEY, BASE_URL, etc.)

## Running Tests

### Test Cases are grouped by TestNG groups: 
* smoke (fast, critical)
* regression 
* api 
* ui-web 
* ui-mobile 
* integration (API + UI thin slice)
* quarantine (flaky/under investigation)
  * Ensure these commands work and are documented:
    - mvn test -Dgroups=smoke
    - mvn test -Dgroups=api
    - mvn test -Dgroups=ui-web

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
*   To run test as TestNG on Eclipse, you need to install TestNG plugin (Help → Eclipse Marketplace)
*   GitHub Copilot for Eclipse recommended (Help → Eclipse Marketplace)

### Using Command Line
```bash
# Resolve dependencies
mvn dependency:resolve

# Execute tests (default smoke suite)
mvn test -DsuiteXmlFile=SmokeTest.xml
mvn test -DsuiteXmlFile=SmokeTest.xml -Dheadless=true

# Windows PowerShell examples (quoting is safe)
mvn test "-Dsurefire.suiteXmlFiles=testRunner/suiteFiles/SmokeTest.xml"
mvn test "-Dsurefire.suiteXmlFiles=testRunner/suiteFiles/SmokeTest.xml" "-Dheadless=true"
mvn test "-Dsurefire.suiteXmlFiles=testRunner/suiteFiles/RegressionTest.xml" "-Dheadless=true"
mvn test "-Dsurefire.suiteXmlFiles=testRunner/suiteFiles/RegressionTest.xml" "-Dheadless=true" "-Dgroups=api"
```

### Using CI/CD of GitHub Actions
*   Run smoke on PR 
*   Run regression nightly (or scheduled)
*   upload reports + artifacts (screenshots/logs) as build artifacts

## Run tests in local Docker environment

```bash
# Start dependencies
docker compose up -d mysql selenium

# Run tests (ephemeral Maven container)
docker compose run --rm --no-deps tests
```

Run specific suites quickly:

```bash
docker compose run --rm --no-deps -e SUITE_XML_FILE=RegressionTest.xml tests
```

Debug Selenium visually:

Open `http://localhost:7900` (VNC for the Selenium container).

Inspect failing runs:

```bash
docker compose logs -f selenium
docker compose logs -f tests
```

Validate CI parity before push:

Confirm local Docker run passes with same env/secrets as CI.

Keep environment clean/resettable:

```bash
docker compose down
docker compose down -v
```

(`docker compose down -v` also removes volumes.)

Pin/test image versions safely before changing CI (e.g., Selenium or Maven image tags).

## Share Tutorial MySQL DB with Teammates

This repository includes MySQL as a `docker compose` service. Seed SQL files are loaded from `docker/mysql/init` on first startup.

1. Copy env template:
```bash
cp .env.example .env
MYSQL_PORT=3306
MYSQL_ROOT_PASSWORD=rootpass123
MYSQL_DATABASE=slothdb
MYSQL_USER=slothuser
MYSQL_PASSWORD=slothpass123
```

Windows PowerShell:
```powershell
Copy-Item .env.example .env
```

2. Add your tutorial DB dump to:
* `docker/mysql/init/01_tutorial_db.sql`

Example dump command from your running local MySQL container:
```bash
docker compose exec -T mysql mysqldump -uroot -p"$MYSQL_ROOT_PASSWORD" --databases tutorial_db > docker/mysql/init/01_tutorial_db.sql
```

PowerShell example:
```powershell
docker compose exec -T mysql mysqldump -uroot -p"$env:MYSQL_ROOT_PASSWORD" --databases tutorial_db | Out-File -Encoding utf8 docker/mysql/init/01_tutorial_db.sql
```

3. Start MySQL:
```bash
docker compose up -d mysql
```

4. Start test stack (Selenium + tests + MySQL):
```bash
docker compose up --abort-on-container-exit --exit-code-from tests tests
```

Stop services:
```bash
docker compose down
```

Reset DB and reseed from `docker/mysql/init/*.sql`:
```bash
docker compose down -v
docker compose up -d mysql
```

## Run Android tests with Appium Docker

Use this flow to start an Appium server in Docker Compose and create a new `AndroidDriver` from Java.

1. Start Appium service:
```bash
docker compose up -d appium
```

2. Verify Appium is ready:
```bash
curl http://127.0.0.1:4723/status
```

3. Ensure your Android device or emulator is available:
* Start an emulator in Android Studio, or connect a real device with USB debugging enabled.
* For a real USB device, update `docker-compose.yml` with `privileged: true` and a USB mount, then verify:
```bash
docker compose exec appium adb devices
```

4. Example Appium server URL in tests:
```bash
http://127.0.0.1:4723
```

5. Stop Appium when done:
```bash
docker compose stop appium
```
