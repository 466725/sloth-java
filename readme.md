# sloth-java

[![Java](https://img.shields.io/badge/Java-25-orange.svg)](https://www.oracle.com/java/technologies/downloads/)
[![Build](https://img.shields.io/badge/Build-Maven-blue.svg)](https://maven.apache.org/)

`sloth-java` is a multi-module Java workspace for UI, API, and mobile test automation, plus a small Spring Boot demo app for local learning and integration checks.

## Modules

- `test-automation-framework`: main automation module (TestNG, Selenium, Appium, Playwright, Rest Assured, Cucumber).
- `spring-boot-demo`: lightweight Spring Boot service with sample endpoints.

## Tech Stack

- Java 25
- Maven (multi-module build)
- TestNG, Selenium, Appium, Playwright
- Cucumber + Extent Reports
- Spring Boot
- Docker Compose (optional local infrastructure)

## Prerequisites

- JDK 25+
- Maven 3.9+
- Git
- Docker Desktop (optional)
- IntelliJ IDEA or Eclipse (optional)

## Quick Start

```powershell
git clone https://github.com/<your-org>/sloth-java.git
Set-Location sloth-java
mvn -q -DskipTests clean install
```

## Running Tests

### `test-automation-framework`

The framework uses TestNG suite files in `test-automation-framework/testRunner/suiteFiles`.
Select a suite explicitly when running tests:

```powershell
# Smoke tests
mvn -pl test-automation-framework test "-DsuiteXmlFile=SmokeTest.xml"

# Regression tests
mvn -pl test-automation-framework test "-DsuiteXmlFile=RegressionTest.xml"
```

The POM's default suite value is `SanityTest.xml`. TestNG reports are written to `test-automation-framework/target/surefire-reports`.

The framework's Surefire plugin explicitly uses the TestNG provider to execute
these XML suites and fails if no tests run. The provider dependency belongs inside
the plugin's `<dependencies>`, not the project's dependency list. JUnit tests in
this module are not executed by this TestNG configuration.

#### API test configuration

Copy `.env.example` to `.env` in the repository root and set
`API_MANAGER_SUBSCRIPTION_KEY` to your local API subscription key. The loader
finds `.env` when tests run from either the repository root or the framework
module. An environment variable with the same name takes precedence, so CI can
inject the key without a file. API test setup fails explicitly if the key is
missing or blank; non-API tests do not require it.

`.env` is ignored by Git. Never put real secrets in `.env.example` or test logs.
Rotate any key previously committed to source control; removing it from the
current files does not remove it from Git history.

### `spring-boot-demo`

```powershell
# Run the demo module tests
mvn -pl spring-boot-demo test
```

To run only `DemoApplicationTests`, use PowerShell from the repository root
with JDK 25+ and Maven 3.9+ available on your `PATH`:

```powershell
# Use the demo module's POM and run only DemoApplicationTests
mvn -f .\spring-boot-demo\pom.xml "-Dtest=DemoApplicationTests" test
```

`-f` selects the module's POM, and `-Dtest` selects the test class. Keep the
`-Dtest` argument quoted in PowerShell. Reports are written to
`spring-boot-demo/target/surefire-reports`.

To run both modules' tests with the smoke suite:

```powershell
mvn -pl test-automation-framework,spring-boot-demo test "-DsuiteXmlFile=SmokeTest.xml"
```

### Start the demo app

```powershell
mvn -pl spring-boot-demo spring-boot:run
```

Once the app is running, useful endpoints include:

- `GET /api/hello`
- `POST /api/login`
- `GET /actuator/health`

## Docker Services

The Compose file is [docker/docker-compose.yml](docker/docker-compose.yml).
Start Docker Desktop with Linux containers enabled, then run these PowerShell
commands from the repository root. Keep `--project-directory .` so the `tests`
service mounts the whole repository at `/workspace`, not just the `docker` folder.

### Start services

```powershell
# Start all services, including every optional profile and the test runner
docker compose --project-directory . -f .\docker\docker-compose.yml --profile "*" up -d

# Start Selenium in the background
docker compose --project-directory . -f .\docker\docker-compose.yml up -d selenium

# Optional: start the Android emulator and Appium as well
docker compose --project-directory . -f .\docker\docker-compose.yml --profile mobile-emulator-google up -d selenium android-emulator-google appium-google

# Check container status and health
docker compose --project-directory . -f .\docker\docker-compose.yml --profile mobile-emulator-google ps -a
```

Starting all services also runs the `tests` service, which exits after its Maven
test run; it is not a long-running service. Use the Selenium-only or mobile-service
commands above if you want infrastructure without automatically running tests.

Service endpoints:

- Selenium: `http://localhost:4444` (status: `/status`).
- Selenium browser viewer: `http://localhost:7900`.
- Appium (mobile profile): `http://localhost:4724`.
- Android emulator (mobile profile): ADB on port `5555`, gRPC on port `8554`.

The Android emulator requires a host that supports its virtualization requirements;
the mobile profile may need a suitably configured Linux host.

### Run tests in Docker

The `tests` service runs Maven with Java 25 and waits for Selenium to become healthy.
It runs the smoke suite by default and exits when the test run finishes.

```powershell
docker compose --project-directory . -f .\docker\docker-compose.yml run --rm tests

# Select another suite for a subsequent run
$env:SUITE_XML_FILE = "RegressionTest.xml"
docker compose --project-directory . -f .\docker\docker-compose.yml run --rm tests
Remove-Item Env:\SUITE_XML_FILE
```

### Troubleshoot with logs

```powershell
# Show recent service logs
docker compose --project-directory . -f .\docker\docker-compose.yml --profile mobile-emulator-google logs --tail 200

# Follow Selenium logs live (Ctrl+C stops following, not the service)
docker compose --project-directory . -f .\docker\docker-compose.yml logs -f --tail 100 selenium

# Follow mobile-service logs
docker compose --project-directory . -f .\docker\docker-compose.yml --profile mobile-emulator-google logs -f --tail 100 android-emulator-google appium-google
```

`run --rm tests` prints Maven output directly and removes its container afterward.
For a test container whose logs should remain available, use
`docker compose --project-directory . -f .\docker\docker-compose.yml up tests`,
then inspect it with
`docker compose --project-directory . -f .\docker\docker-compose.yml logs --tail 200 tests`.
If startup fails, check Docker Desktop is running, inspect `ps -a` and service logs,
and check whether another process is already using a published port.

### Stop services

```powershell
# Stop all services across every profile without removing containers
docker compose --project-directory . -f .\docker\docker-compose.yml --profile "*" stop

# Stop and remove all service containers and the Compose network
docker compose --project-directory . -f .\docker\docker-compose.yml --profile "*" down
```

## Playwright Setup

Install browser binaries explicitly (recommended for CI or restricted networks):

```powershell
mvn -pl test-automation-framework -DskipTests exec:java "-Dexec.mainClass=com.microsoft.playwright.CLI" "-Dexec.args=install chromium"
```

Useful runtime properties:

- `-Dplaywright.browser=chromium|firefox|webkit|chrome|msedge`
- `-Dheadless=true|false`

## Debugging

### Debug a framework test

Start the selected TestNG suite with Surefire's debug mode. Maven pauses the forked test JVM until a debugger attaches on port `5005`:

```powershell
mvn -pl test-automation-framework -Dmaven.surefire.debug test "-DsuiteXmlFile=SmokeTest.xml"
```

Create or select a **Remote JVM Debug** configuration in your IDE and attach to `localhost:5005`. Replace `SmokeTest.xml` with `RegressionTest.xml` to debug the regression suite.

### Debug the Spring Boot demo

Start the app suspended on port `5005`:

```powershell
mvn -pl spring-boot-demo spring-boot:run "-Dspring-boot.run.jvmArguments=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005"
```

Attach your IDE's remote debugger to `localhost:5005`; the application resumes after the debugger connects.

## Self-Healing Locators

Playwright page objects can wrap selectors with `SelfHealingLocator`. When an action on a wrapped locator fails because the element cannot be found, the wrapper scores DOM candidates and retries the action once with the best match.

Self-healing is enabled by default for wrapped locators. Configure it with a JVM property or environment variable:

```powershell
# Disable self-healing
mvn -pl test-automation-framework test "-DsuiteXmlFile=SmokeTest.xml" "-Dself.healing.enabled=false"
```

- `self.healing.enabled` / `SELF_HEALING_ENABLED`: enable or disable healing.
- `self.healing.minScore`: minimum similarity score (default `0.35`).
- `self.healing.maxCandidates`: maximum DOM candidates to score (default `800`).
