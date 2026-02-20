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

*   Prefer `@Getter` and `@Setter` annotations over manual getter/setter methods.
*   Keep classes focused on a single responsibility.
*   Extract helpers when a class grows too large.
*   Keep methods short and focused on a single task.
*   Use descriptive names for tests and methods.
*   Avoid abbreviations that obscure intent.

---

## Configuration & Secrets
*   All configuration are done via `src/main/resources/config.properties`.
*   Secrets are stored in `src/main/resources/secrets.properties`.
*   Load secrets from environment variables (e.g., API_KEY, BASE_URL, etc.)

---

## Running Tests

### Test Cases are grouped by TestNG groups:
* smoke (fast, critical)
* regression
* api
* ui-web
* ui-mobile
* integration (API + UI thin slice)
* quarantine (flaky/under investigation)

### Retry on Failure
* Retry is enabled by `FailureListener` + `FailureRetryAnalyzer`.
* Listener is registered via ServiceLoader: `src/test/resources/META-INF/services/org.testng.ITestNGListener`.
* Reruns (`testng-failed.xml`) use the same listener in IDE and CLI.

### Extent Report Config
* Report styling/metadata is loaded by `config.ExtentReportHandler`.
* Default config file: `src/main/resources/extent-report-config.xml`.

---

### Using the IDE
*   Import the project as a **Maven Project**.
*   Install the TestNG plugin (`Help -> Eclipse Marketplace`) to Eclipse.
*   IntelliJ IDEA shows run icons by default. 
*   Right-click test methods with TestNG @test annotation -> select **Run as TestNG Suite**.

---

### Using Command Line
```bash
# Resolve dependencies
mvn dependency:resolve

# Execute tests (default smoke suite)
mvn test -Dgroups=smoke
mvn test -Dgroups=api
mvn test -Dgroups=ui-web
mvn test -DsuiteXmlFile=SmokeTest.xml
mvn test -DsuiteXmlFile=SmokeTest.xml -Dheadless=true

# Windows PowerShell examples (quoting is safe)
mvn test "-DsuiteXmlFile=SmokeTest.xml"
mvn test "-DsuiteXmlFile=SmokeTest.xml" "-Dheadless=true"
mvn test "-DsuiteXmlFile=RegressionTest.xml" "-Dheadless=true" "-Dgroups=api"
```

---

### Using CI/CD of GitHub Actions
*   Run smoke on PR
*   Run mobile-web smoke on PR/push (Selenium mobile emulation on GitHub runner)
*   Run regression nightly (or scheduled)
*   Upload reports + artifacts (screenshots/logs) as build artifacts

---

## Run non-mobile tests in local Docker environment

```bash
# Start dependencies
docker compose up -d mysql selenium

# Run default non-mobile smoke suite
docker compose run --rm --no-deps tests

# Run a specific non-mobile suite
docker compose run --rm --no-deps -e SUITE_XML_FILE=RegressionTest.xml tests
```

Note:
`docker-compose.yml` pins images by digest for reproducible runs. To intentionally refresh to newer image versions, update digests in `docker-compose.yml` after validation.

---

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

---

## Run mobile app tests in local Docker environment

```bash
# Start Google emulator + dedicated Appium sidecar + MySQL
docker compose --profile mobile-emulator-google up -d android-emulator-google appium-google mysql

# Optional sanity check: emulator is visible via adb inside appium sidecar
docker exec appium-google adb devices
```

PowerShell command to run MobileAppSmokeTest.xml locally:
```powershell
docker compose --profile mobile-emulator-google run --rm --no-deps `
  -e SUITE_XML_FILE=MobileAppSmokeTest.xml `
  -e APPIUM_SERVER_URL=http://appium-google:4723 `
  -e MOBILE_CONTAINER_NAME=android-emulator-google `
  tests
```

Notes:
* Host ports used by this profile: emulator `8554/5555`, appium `4724` (inside Docker network tests still use `http://appium-google:4723`).

Inspect failing runs:

```bash
docker compose --profile mobile-emulator-google logs -f android-emulator-google
docker compose --profile mobile-emulator-google logs -f appium-google
docker compose logs -f tests
```

Keep environment clean/resettable:

```bash
docker compose down
docker compose --profile mobile-emulator-google down
docker compose down -v
docker image prune -f
```

Why Mobile App + Emulator is local-only (not GitHub hosted runners)

* GitHub CI runs `MobileWebSmokeTest.xml`
* But Appium with Android device emulator is executed only locally `MobileAppSmokeTest.xml`. Reasons:
    * On GitHub-hosted runners, Dockerized Android emulator startup is slow.
    * The emulator image is 9GB large.

---

## Share Tutorial MySQL DB with Teammates

This repository includes MySQL as a `docker compose` service. Seed SQL files are loaded from `docker/mysql/init` on first startup.

Then set these values in `.env`:
```env
MYSQL_PORT=3306
MYSQL_ROOT_PASSWORD=rootpass123
MYSQL_DATABASE=slothdb
MYSQL_USER=slothuser
MYSQL_PASSWORD=slothpass123
```

Add your tutorial DB dump to:
* `docker/mysql/init/01_tutorial_db.sql`

Example dump command from your running local MySQL container:
```bash
docker compose exec -T mysql mysqldump -uroot -p"$MYSQL_ROOT_PASSWORD" --databases tutorial_db > docker/mysql/init/01_tutorial_db.sql
```

PowerShell example:
```powershell
docker compose exec -T mysql mysqldump -uroot -p"$env:MYSQL_ROOT_PASSWORD" --databases tutorial_db | Out-File -Encoding utf8 docker/mysql/init/01_tutorial_db.sql
```
