# sloth-java

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/technologies/downloads/)
[![Build](https://img.shields.io/badge/Build-Maven-blue.svg)](https://maven.apache.org/)

`sloth-java` is a multi-module Java workspace for UI, API, and mobile test automation, plus a small Spring Boot demo app for local learning and integration checks.

## Modules

- `test-automation-framework`: main automation module (TestNG, Selenium, Appium, Playwright, Rest Assured, Cucumber).
- `spring-boot-demo`: lightweight Spring Boot service with sample endpoints.

## Tech Stack

- Java 21
- Maven (multi-module build)
- TestNG, Selenium, Appium, Playwright
- Cucumber + Extent Reports
- Spring Boot
- Docker Compose (optional local infrastructure)

## Prerequisites

- JDK 21+
- Maven 3.6+
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

Once the app is running, useful endpoints are:

- `GET /api/hello`
- `POST /api/login?username=username&password=password`
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

- Framework config: `test-automation-framework/src/main/resources/init-config.properties`
- Secrets template: `test-automation-framework/src/main/resources/secrets.properties`
- Report config: `test-automation-framework/src/main/resources/extent-report-config.xml`

Use environment variables for credentials and API keys in local and CI environments.

## Docker Workflows (Optional)

### Non-mobile suites

```powershell
docker compose up -d mysql selenium
docker compose run --rm --no-deps tests
docker compose run --rm --no-deps -e SUITE_XML_FILE=RegressionTest.xml tests
```

### Mobile local smoke suite

```powershell
docker compose --profile mobile-emulator-google up -d android-emulator-google appium-google selenium mysql
docker compose --profile mobile-emulator-google run --rm --no-deps -e SUITE_XML_FILE=MobileLocalSmokeTest.xml -e APPIUM_SERVER_URL=http://appium-google:4723 -e MOBILE_CONTAINER_NAME=android-emulator-google tests
```

## Repository Layout

```text
sloth-java/
|- test-automation-framework/
|  |- src/main/java/{config,utilities,webpages,...}
|  |- src/test/java/{testcases,scenarios,selfhealing,...}
|  |- testRunner/suiteFiles/{SmokeTest.xml,SanityTest.xml,RegressionTest.xml,...}
|- spring-boot-demo/
|  |- src/main/java/openqa/automation/framework/demo/
|  |- src/main/resources/application.yaml
|- docker-compose.yml
```

## Contribution Guidelines

- Keep PRs scoped and module-focused.
- Add or update tests for behavior changes.
- Run a relevant suite locally before opening a PR.
- Include clear verification steps in PR descriptions.

## Roadmap

- Expand CI checks for module-specific workflows.
- Continue improving mobile execution stability.
- Add more API + UI integration examples.
