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

### `spring-boot-demo`

```powershell
# Run the demo module tests
mvn -pl spring-boot-demo test
```

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
