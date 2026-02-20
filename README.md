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

*   **JDK 21** or higher
*   **Maven 3.6+**
*   **Git**
*   An IDE (IntelliJ IDEA recommended)

### Environment Setup

1.  **Clone the Repository**
    ```bash
    git clone https://github.com/your-username/sloth-java.git
    cd sloth-java
    ```

2.  **Configure Java 21**
    Ensure your `settings.xml` or project properties are set to Java 21. If using Maven via CLI:
    ```bash
    mvn clean install -Dmaven.compiler.release=21
    ```

3.  **Set Credentials (Optional)**
    If running tests on Sauce Labs, export your credentials:
    ```bash
    export SAUCE_USERNAME=<your_username>
    export SAUCE_ACCESS_KEY=<your_access_key>
    ```
    If calling API with a subscription key, add it to OS environment variables (e.g., `API_MANAGER_SUBSCRIPTION_KEY`).

---

## Code Conventions

Best practices to keep the codebase consistent and maintainable:

*   Prefer `@Getter` and `@Setter` annotations over manual getter/setter methods to reduce boilerplate.
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

Group examples:
```bash
mvn test -Dgroups=smoke
mvn test -Dgroups=api
mvn test -Dgroups=ui-web
```

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
*   To run tests as TestNG on Eclipse, install the TestNG plugin (`Help -> Eclipse Marketplace`).
*   GitHub Copilot for Eclipse is recommended (`Help -> Eclipse Marketplace`).

### Using Command Line
```bash
# Resolve dependencies
mvn dependency:resolve

# Execute tests (default smoke suite)
mvn test -DsuiteXmlFile=SmokeTest.xml
mvn test -DsuiteXmlFile=SmokeTest.xml -Dheadless=true

# Windows PowerShell examples (quoting is safe)
mvn test "-DsuiteXmlFile=SmokeTest.xml"
mvn test "-DsuiteXmlFile=SmokeTest.xml" "-Dheadless=true"
mvn test "-DsuiteXmlFile=RegressionTest.xml" "-Dheadless=true"
mvn test "-DsuiteXmlFile=RegressionTest.xml" "-Dheadless=true" "-Dgroups=api"
```

### Using CI/CD of GitHub Actions
*   Run smoke on PR
*   Run mobile-web smoke on PR/push (Selenium mobile emulation on GitHub runner)
*   Run regression nightly (or scheduled)
*   Upload reports + artifacts (screenshots/logs) as build artifacts

## Run non-mobile tests in local Docker environment

```bash
# Start dependencies
docker compose up -d mysql selenium

# Run default non-mobile smoke suite
docker compose run --rm --no-deps tests

# Run a specific non-mobile suite
docker compose run --rm --no-deps -e SUITE_XML_FILE=RegressionTest.xml tests
```

## Run mobile web tests in local Docker environment

```bash
# Start dependencies
docker compose up -d mysql selenium

# Match GitHub CI mobile-web mode (Selenium mobile emulation)
docker compose run --rm --no-deps -e SUITE_XML_FILE=MobileWebSmokeTest.xml -e MOBILE_WEB_RUN_MODE=selenium tests
```

Debug Selenium visually:
Open `http://localhost:7900` (VNC for the Selenium container).

Note:
This path does not require an Appium container. It uses Selenium mobile emulation, same as CI.

## Run mobile app tests in local Docker environment

```bash
# Start Android emulator + MySQL (Appium endpoint is inside android-emulator container)
docker compose --profile mobile-emulator up -d android-emulator mysql

# Run mobile app suite in Docker
docker compose --profile mobile-emulator run --rm --no-deps -e SUITE_XML_FILE=MobileAppSmokeTest.xml -e APPIUM_SERVER_URL=http://android-emulator:4723 tests
```

Note:
In `mobile-emulator` mode, Appium is provided by the `android-emulator` container image (`budtmo/docker-android`).
You may not see a separate `appium-container` running in Docker Desktop, and that is expected.
`appium-container` is used by the `mobile-host` profile.
Emulator UI is available at `http://localhost:6080`.

Inspect failing runs:

```bash
docker compose logs -f selenium
docker compose --profile mobile-emulator logs -f android-emulator
docker compose logs -f tests
```

Keep environment clean/resettable:

```bash
docker compose down
docker compose --profile mobile-emulator down
docker compose down -v
```

(`docker compose down -v` also removes volumes.)

## Share Tutorial MySQL DB with Teammates

This repository includes MySQL as a `docker compose` service. Seed SQL files are loaded from `docker/mysql/init` on first startup.

1. Copy env template:
```bash
cp .env.example .env
```

Then set these values in `.env`:
```env
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

Use one of these local profiles when running tests from your host machine (without the Docker `tests` container).

### Option A: Dockerized emulator (local only)

1. Start Android emulator container (includes Appium endpoint):
```bash
docker compose --profile mobile-emulator up -d android-emulator
```

2. Run the local mobile suite from host:
```bash
$env:APPIUM_SERVER_URL="http://127.0.0.1:4723"
$env:ANDROID_DEVICE_NAME="Android"
mvn test "-DsuiteXmlFile=MobileSmokeTest.xml"
```

3. Inspect emulator UI (noVNC):
Open `http://localhost:6080`

### Option B: Host Android device/emulator + Appium container

1. Start Appium service:
```bash
docker compose --profile mobile-host up -d appium
```

2. Run mobile web suite from host (pointing to Appium on localhost):
```bash
$env:APPIUM_SERVER_URL="http://127.0.0.1:4723"
$env:MOBILE_WEB_RUN_MODE="appium"
mvn test "-DsuiteXmlFile=MobileWebSmokeTest.xml"
```

Stop services:
```bash
docker compose --profile mobile-emulator down
docker compose --profile mobile-host down
```

### Why Mobile App + Emulator is local-only (not GitHub hosted runners)

`TestAndroidDeviceConnectivity` and other Appium+real-Android-session flows are kept for local execution.

Reason:
* On GitHub-hosted runners, Dockerized Android emulator startup is not stable/reliable enough for this project.
* The emulator image is large and boot readiness is inconsistent in the hosted CI environment.
* This caused repeated `unhealthy` container states and Appium connection failures during CI.

Current CI strategy:
* GitHub runs `MobileWebSmokeTest.xml` with Selenium mobile emulation (`MOBILE_WEB_RUN_MODE=selenium`).
* Appium + Android emulator/device validation is executed locally.
