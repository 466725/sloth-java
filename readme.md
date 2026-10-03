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

The default suite is `SanityTest.xml` (configured in `test-automation-framework/pom.xml`).

```powershell
# Run default suite
mvn -pl test-automation-framework test

# Run by suite file
mvn -pl test-automation-framework test "-DsuiteXmlFile=SmokeTest.xml"
mvn -pl test-automation-framework test "-DsuiteXmlFile=RegressionTest.xml"

# Run by TestNG group
mvn -pl test-automation-framework test "-Dgroups=smoke"
mvn -pl test-automation-framework test "-Dgroups=api"
mvn -pl test-automation-framework test "-Dgroups=playwright"

# Common option
mvn -pl test-automation-framework test "-DsuiteXmlFile=SmokeTest.xml" "-Dheadless=true"
```

Common groups include `smoke`, `regression`, `api`, `ui-web`, `ui-mobile-app`, `ui-mobile-web`, `integration`, `playwright`, and `quarantine`.

### `spring-boot-demo`

```powershell
# Run tests
mvn -pl spring-boot-demo test

# Start app
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

## Self-Healing Locators

The Playwright page-object layer supports optional self-healing locators that retry failed selectors using DOM similarity scoring.

- Design document: `Self-Healing Framework.md`
- Enable/disable: `-Dself.healing.enabled=true|false` or `SELF_HEALING_ENABLED=true|false`
- Tuning: `-Dself.healing.minScore=<value>`, `-Dself.healing.maxCandidates=<value>`

## Configuration
