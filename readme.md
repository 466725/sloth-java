# sloth-java

[![Java](https://img.shields.io/badge/Java-25-orange.svg)](https://www.oracle.com/java/technologies/downloads/)
[![Build](https://img.shields.io/badge/Build-Maven-blue.svg)](https://maven.apache.org/)

`sloth-java` is a multi-module Java workspace for UI, API, and mobile test automation, a Spring Boot demo app, and a collection of Spring and Java learning examples.

## Modules

- `test-automation-framework`: main automation module (TestNG, Selenium, Appium, Playwright, Rest Assured, Cucumber).
- `spring-boot-demo`: lightweight Spring Boot service with sample endpoints.
- `skill-spring`: Spring and Java learning examples covering AssertJ, ExtentReports, JavaFaker, JSON parsing, Selenium, and MySQL.

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

## Continuous Integration

GitHub Actions uses [ci.yml](.github/workflows/ci.yml), and GitLab uses the
root-level [.gitlab-ci.yml](.gitlab-ci.yml). Both build all modules with Temurin
Java 25, cache Maven dependencies, and run `mvn -B -ntp -DskipTests package`
(tests are compiled but not executed during the build job).

After a successful build, Selenium and Playwright run as separate, parallel test
jobs. Each selects only its `ui.tangerine.selenium` or `ui.tangerine.playwright`
package, including all test classes in that package, rather than the default
`SanityTest.xml` suite. Both run headlessly with Java 25.

| Test job | GitHub reusable workflow | GitLab included jobs |
|----------|--------------------------|----------------------|
| Selenium | [selenium.yml](.github/workflows/selenium.yml) | [selenium.yml](.gitlab/ci/selenium.yml) |
| Playwright | [playwright.yml](.github/workflows/playwright.yml) | [playwright.yml](.gitlab/ci/playwright.yml) |

Separate files are needed for each CI platform because their YAML schemas differ.
GitHub calls the reusable workflows from its main workflow; they can also be run
individually with **Run workflow**. GitLab includes both job files from its root
configuration.

Selenium uses a `selenium/standalone-chromium:4` service and waits for Grid
readiness. Playwright installs Chromium and its Linux dependencies using the
module's existing Playwright Java CLI, keeping the browser version aligned with
the Maven dependency. Runners need network access to Maven repositories, browser
downloads, and the public Tangerine and Google sites exercised by these tests;
site availability, regional restrictions, or bot protection can affect results.

Both platforms retain Surefire reports and `test-output/` (ExtentReports, logs,
and screenshots) for 14 days, including on test failure. GitLab also publishes
Surefire XML as JUnit test reports.

The GitLab pipeline runs for merge requests, pushes to `main` or `master`, and
manual pipelines started from the GitLab UI. It requires a Linux GitLab runner
with a Docker or Kubernetes executor that supports the configured Maven image.
GitLab automatically checks out the repository; no separate checkout step or
CI/CD variables are required. The runner must support service containers for
Selenium and package installation in the Maven job container for Playwright's
Linux dependencies and the Selenium readiness check.

## Just Commands

The repository includes a [`justfile`](justfile) with shortcuts for common Maven,
test, demo-app, and Docker workflows. Install [Just](https://just.systems/) and
ensure both `just` and Maven are available on your `PATH`. On Windows, you can
install Just with Scoop:

```powershell
scoop install just
```

Run `just` or `just --list` from the repository root to see the available
recipes. Common examples:

```powershell
just build
just test
just test-framework SmokeTest.xml
just test-framework-remote RegressionTest.xml
just test-cucumber
just run-demo
just docker-selenium
just docker-test RegressionTest.xml
just docker-down
```

`test-framework` defaults to `SanityTest.xml`; `docker-test` defaults to
`SmokeTest.xml`. The remote framework recipe expects the Docker Selenium service
to be running first (`just docker-selenium`). See [Docker Services](#docker-services)
for service prerequisites and other Docker workflows.

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

#### Cucumber tests

Cucumber scenarios run through TestNG with `cucumber-testng` and Playwright:

- Feature files: `test-automation-framework/src/test/resources/features`
- Step definitions and runner: `test-automation-framework/src/test/java/ui/tangerine/cucumber`

```powershell
# Run all Cucumber scenarios
mvn -pl test-automation-framework test "-Dtest=ui.tangerine.cucumber.CucumberRunnerTest"
```

To add scenarios, create a `.feature` file under `features` and implement any
new steps in the `ui.tangerine.cucumber` package. To include Cucumber in a suite,
add `<class name="ui.tangerine.cucumber.CucumberRunnerTest"/>` to the suite XML.

### `spring-boot-demo`

```powershell
# Run the demo module tests
mvn -pl spring-boot-demo test
```

To run only `DemoApplicationTests`, use the following command from the repository root
with JDK 25+ and Maven 3.9+ available on your `PATH`:

```bash
# Use the demo module's POM and run only DemoApplicationTests
mvn -f spring-boot-demo/pom.xml "-Dtest=DemoApplicationTests" test
```

`-f` selects the module's POM, and `-Dtest` selects the test class. Reports are written to
`spring-boot-demo/target/surefire-reports`.

### `skill-spring`

The skill-spring module contains learning examples organized under `tutorial/` and `concepts/` directories, covering topics like AssertJ, ExtentReports, JavaFaker, JSON parsing, Selenium browser automation, and MySQL database operations.

```powershell
# Run all skill-spring tests
mvn -pl skill-spring test
```

Source directories `tutorial/` and `concepts/` are registered via the `build-helper-maven-plugin` in the module's POM. Reports are written to `skill-spring/target/surefire-reports`.

### Run all modules

To run all three modules' tests:

```powershell
mvn test
```

To run specific modules together:

```powershell
mvn -pl test-automation-framework,spring-boot-demo,skill-spring test "-DsuiteXmlFile=SmokeTest.xml"
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
Start Docker Desktop with Linux containers enabled, then run the commands below
from the repository root. Keep `--project-directory .` so the `tests` service
mounts the whole repository at `/workspace`, not just the `docker` folder.

### Start services

**Windows (PowerShell):**

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

**macOS / Linux (Bash):**

```bash
# Start all services, including every optional profile and the test runner
docker compose --project-directory . -f ./docker/docker-compose.yml --profile "*" up -d

# Start Selenium in the background
docker compose --project-directory . -f ./docker/docker-compose.yml up -d selenium

# Optional: start the Android emulator and Appium as well
docker compose --project-directory . -f ./docker/docker-compose.yml --profile mobile-emulator-google up -d selenium android-emulator-google appium-google

# Check container status and health
docker compose --project-directory . -f ./docker/docker-compose.yml --profile mobile-emulator-google ps -a
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

On Apple Silicon (ARM64) Macs, the Selenium service uses `selenium/standalone-chromium`
instead of `selenium/standalone-chrome`, because Google Chrome is not available for
Linux/ARM64. Chromium behaves identically for WebDriver tests. The mobile-emulator
profile images are AMD64-only and will not run natively on Apple Silicon; use an
AMD64 host or Docker emulation (`--platform linux/amd64`) with reduced performance.

### Run tests in Docker

The `tests` service runs Maven with Java 25 and waits for Selenium to become healthy.
It runs the smoke suite by default and exits when the test run finishes.

**Windows (PowerShell):**

```powershell
docker compose --project-directory . -f .\docker\docker-compose.yml run --rm tests

# Select another suite for a subsequent run
$env:SUITE_XML_FILE = "RegressionTest.xml"
docker compose --project-directory . -f .\docker\docker-compose.yml run --rm tests
Remove-Item Env:\SUITE_XML_FILE
```

**macOS / Linux (Bash):**

```bash
docker compose --project-directory . -f ./docker/docker-compose.yml run --rm tests

# Select another suite for a subsequent run
SUITE_XML_FILE=RegressionTest.xml docker compose --project-directory . -f ./docker/docker-compose.yml run --rm tests
```

### Run tests locally against Docker Selenium

Use this when local ChromeDriver can't start, for example when a Windows Application
Control policy blocks `selenium-manager.exe` (`CreateProcess error=4551`) or when
macOS permissions prevent ChromeDriver from launching. Maven runs on the host, and
the browser runs in the Selenium container:

**Windows (PowerShell):**

```powershell
docker compose --project-directory . -f .\docker\docker-compose.yml up -d selenium
mvn -pl test-automation-framework test "-DsuiteXmlFile=RegressionTest.xml" "-Dselenium.remote.url=http://localhost:4444"
```

**macOS / Linux (Bash):**

```bash
docker compose --project-directory . -f ./docker/docker-compose.yml up -d selenium
mvn -pl test-automation-framework test -DsuiteXmlFile=RegressionTest.xml -Dselenium.remote.url=http://localhost:4444
```

Local ChromeDriver vs. Docker Selenium:

| Aspect | Local ChromeDriver | Docker Selenium |
| --- | --- | --- |
| How it's selected | Default when no remote URL is set | `-Dselenium.remote.url` or `SELENIUM_REMOTE_URL` is set |
| Driver class | `ChromeDriver` | `RemoteWebDriver` |
| Browser location | Chrome installed on the host (Windows/macOS only) | Chromium in the `selenium/standalone-chromium` container (Linux) |
| Driver resolution | Selenium Manager (`selenium-manager.exe`) finds or downloads `chromedriver` | Bundled in the image; no host executables run |
| Corporate policy impact | Can be blocked by Application Control / AppLocker | Unaffected (needs only Docker) |
| Prerequisites | Chrome on host; Selenium Manager allowed | Docker Desktop running; port `4444` free |
| Startup | Fast; no extra service | Container startup + health check (up to ~1 min); session creation retries automatically |
| Watching the browser | Browser window opens on the desktop | noVNC viewer at `http://localhost:7900` |
| Version consistency | Depends on the host's Chrome version | Pinned by image digest; same as CI |
| Debugging | Easiest: breakpoints, DevTools, local window | Breakpoints work; browser visible via noVNC only |
| Best for | Quick local debugging on an unrestricted machine | Restricted machines, reproducible runs, CI parity |

Alternatively, set the `SELENIUM_REMOTE_URL` environment variable for the terminal
session instead of passing `-Dselenium.remote.url`:

**Windows (PowerShell):**
```powershell
$env:SELENIUM_REMOTE_URL = "http://localhost:4444"
```

**macOS / Linux (Bash):**
```bash
export SELENIUM_REMOTE_URL="http://localhost:4444"
```

If the container is healthy but `http://localhost:4444/status` is unreachable,
recreate it with `up -d --force-recreate selenium`.

### Troubleshoot with logs

**Windows (PowerShell):**

```powershell
# Show recent service logs
docker compose --project-directory . -f .\docker\docker-compose.yml --profile mobile-emulator-google logs --tail 200

# Follow Selenium logs live (Ctrl+C stops following, not the service)
docker compose --project-directory . -f .\docker\docker-compose.yml logs -f --tail 100 selenium

# Follow mobile-service logs
docker compose --project-directory . -f .\docker\docker-compose.yml --profile mobile-emulator-google logs -f --tail 100 android-emulator-google appium-google
```

**macOS / Linux (Bash):**

```bash
# Show recent service logs
docker compose --project-directory . -f ./docker/docker-compose.yml --profile mobile-emulator-google logs --tail 200

# Follow Selenium logs live (Ctrl+C stops following, not the service)
docker compose --project-directory . -f ./docker/docker-compose.yml logs -f --tail 100 selenium

# Follow mobile-service logs
docker compose --project-directory . -f ./docker/docker-compose.yml --profile mobile-emulator-google logs -f --tail 100 android-emulator-google appium-google
```

`run --rm tests` prints Maven output directly and removes its container afterward.
For a test container whose logs should remain available, use
`docker compose --project-directory . -f ./docker/docker-compose.yml up tests`,
then inspect it with
`docker compose --project-directory . -f ./docker/docker-compose.yml logs --tail 200 tests`.
If startup fails, check Docker Desktop is running, inspect `ps -a` and service logs,
and check whether another process is already using a published port.

### Stop services

**Windows (PowerShell):**

```powershell
# Stop all services across every profile without removing containers
docker compose --project-directory . -f .\docker\docker-compose.yml --profile "*" stop

# Stop and remove all service containers and the Compose network
docker compose --project-directory . -f .\docker\docker-compose.yml --profile "*" down
```

**macOS / Linux (Bash):**

```bash
# Stop all services across every profile without removing containers
docker compose --project-directory . -f ./docker/docker-compose.yml --profile "*" stop

# Stop and remove all service containers and the Compose network
docker compose --project-directory . -f ./docker/docker-compose.yml --profile "*" down
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
