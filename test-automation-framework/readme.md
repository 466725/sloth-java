# test-automation-framework

A comprehensive test automation framework supporting **UI**, **API**, and **mobile** testing. This module is part of the [sloth-java](../readme.md) multi-module workspace.

## Overview

The framework provides a unified foundation for automated testing across multiple platforms and tools:

| Layer | Technologies | Purpose |
| ------- | ------------- | --------- |
| **UI Testing** | Selenium, Playwright, Cucumber (BDD) | Web browser automation |
| **API Testing** | RestAssured, OkHttp, WireMock | Backend API validation |
| **Mobile Testing** | Appium | Android app and mobile web |
| **Reporting** | ExtentReports, Log4j2 | Test execution reports and logs |
| **Infrastructure** | Docker Compose | Selenium grid, Android emulator, CI test runner |

## Architecture

### Base Test Classes

The framework uses an inheritance hierarchy for shared lifecycle and reporting:

```text
TestCase (base — ExtentReports + Log4j2 lifecycle)
├── GuiTestCase          — Selenium UI tests with auto-screenshots
├── PlaywrightGuiTestCase — Playwright UI tests
├── ApiTestCase          — API tests with OkHttp cookie jar
├── UnitTestCase         — Unit tests
└── TestCase (mobile)
    ├── MobileTestCase
    ├── MobileAppTestCase
    └── MobileWebTestCase
```

### Self-Healing Locators

A built-in self-healing mechanism for Playwright locators — when a selector fails, the framework parses the DOM, scores alternative selectors using Levenshtein distance and similarity algorithms, and automatically applies the best match. Healed selectors are cached to avoid repeated computation.

| Class | Role |
| ------- | ------ |
| `SelfHealingLocator` | Wrapper around Playwright `Locator` with retry-and-heal logic |
| `LocatorHealer` | Parses HTML and finds the best alternative selector |
| `Levenshtein` / `Similarity` | String similarity algorithms for scoring candidates |
| `SelfHealingConfig` | Toggle and configure self-healing behavior |

### Retry Mechanism

Failed tests are automatically retried using TestNG's `IRetryAnalyzer`. The max retry count is configurable via `init-config.properties` (`MAX_RETRY_COUNT = 3`). Skipped tests are not retried.

### HTTP Server Error Monitoring

Use [`ServerErrorMonitor`](src/test/java/utils/ServerErrorMonitor.java) to log
HTTP 500-599 responses and refresh the page once with either Selenium or Playwright:

```java
import utils.ServerErrorMonitor;

// Selenium: register once for the current tab before navigation.
ServerErrorMonitor.monitor(driver);
driver.get("https://example.test");

// Playwright: register once per Page before navigation.
ServerErrorMonitor.monitor(page);
page.navigate("https://example.test");
```

Both overloads log `SERVER ERROR <status> <url>` at ERROR level through the
framework logger. The first 5xx from any response (including API requests,
images, and other resources) requests a full-page refresh. Further 5xx responses
are still logged, but never trigger another refresh for that registration,
even if the refresh request fails. Register only once per page/tab to preserve
this limit. Transport failures without an HTTP response are not included.

Selenium sends a CDP reload command and waits for its acknowledgment, not for
page load completion. Playwright schedules `window.location.reload()` in the browser
instead of performing a nested blocking navigation. Refresh completion is not
awaited; tests should wait for their expected page state. Refreshing can interrupt
an ongoing navigation or interaction and can repeat requests. Refresh-request
errors are logged and propagated; response status alone does not fail the test.

Selenium requires a CDP-capable Chromium driver implementing `HasDevTools` and
a compatible browser/CDP version. The utility uses Selenium's bundled `latest`
network binding, not a literal `vNN` import. Unsupported drivers are rejected;
session/connection errors propagate to the caller. Remote drivers may require
Selenium's `Augmenter` and a Grid exposing CDP. The listener remains attached to
the DevTools session until driver teardown; register separately for other tabs.
Playwright supports all its browser engines; callbacks run while Playwright
processes events (use Playwright waits rather than `Thread.sleep`). The page
owns its listener lifecycle. Existing test base classes are not changed; monitoring
is opt-in.

### Page Object Pattern

Page objects are organized by browser engine and application:

| Package | Browser | Pages |
| --------- | --------- | ------- |
| `webpages/selenium/tangerine/` | Selenium | HomePage, SigninPage, SignupPage |
| `webpages/selenium/google/` | Selenium | GoogleSearchHomePage |
| `webpages/playwright/tangerine/` | Playwright | HomePage, SigningPage, SignupPage |

### Configuration

| File | Purpose |
| ------ | --------- |
| `init-config.properties` | URLs, browser, timeouts, retry count |
| `secrets.properties` | Sensitive credentials (keep out of version control) |
| `.env` | Environment variables loaded via `dotenv-java` |
| `extent.properties` / `extent-report-config.xml` | ExtentReports styling and layout |

### Test Suites

Three pre-defined TestNG suites are available under `testRunner/suiteFiles/`:

| Suite | Groups | Packages | Description |
| ------- | -------- | ---------- | ------------- |
| **SmokeTest.xml** | `smoke` | `api.*`, `ui.*`, `demo.*`, `unit.*` | Fast smoke checks across all layers |
| **RegressionTest.xml** | `regression`, `playwright` | `api.*`, `ui.*`, `demo.*`, `unit.*` | Full regression with Playwright tests |
| **SanityTest.xml** | `smoke` | `unit.*` | Lightweight unit-only sanity check |

The default suite is controlled by the Maven property `suiteXmlFile` (default: `SanityTest.xml`).

Surefire and its explicit TestNG provider share the `surefire.version` property, pinned to 3.5.6. Keep them aligned on the 3.5.x line: Surefire 3.6.0 removed support for TestNG XML suites, which these suite commands require.

## Test Examples

### UI Tests (Selenium)

| Test | Description |
| ------ | ------------- |
| `HomePageSeleniumTest` | Validates Tangerine home page title |
| `SigninPageSeleniumTest` | Tests login flow |
| `SignupPageSeleniumTest` | Tests signup flow |
| `GoogleSearchForAmazonTest` | Searches Google and verifies Amazon result |

### UI Tests (Playwright)

| Test | Description |
| ------ | ------------- |
| `HomePagePlaywrightTest` | Tangerine home page with Playwright |
| `SigningPagePlaywrightTest` | Login flow with Playwright |
| `SignupPagePlaywrightTest` | Signup flow with Playwright |
| `HomePageConsoleErrorPlaywrightTest` | Captures console errors on page load |

### Cucumber BDD

| File | Description |
| ------ | ------------- |
| `TangerineHomePage.feature` | Gherkin feature file |
| `HomePageSteps.java` | Step definitions |
| `CucumberRunnerTest.java` | TestNG runner for Cucumber |

### API Tests

| Test | Description |
| ------ | ------------- |
| `HealthCheckTest` | API health check endpoint |
| `SigninWithRestAssuredTest` | Login API via RestAssured |
| `SigninWithOKHttpTest` | Login API via OkHttp |
| `SpringBootDemoLoginTest` | Tests the `spring-boot-demo` login endpoint |
| `WireMockDemoTest` | Demonstrates API mocking with WireMock |

### Mobile Tests

| Test | Description |
| ------ | ------------- |
| `AndroidDeskClockLaunchTest` | Launches Android Desk Clock app |
| `AndroidDeskClockSetAlarmTest` | Sets an alarm via Appium |

## Docker Compose

The `docker/` directory at the repository root provides the infrastructure for running tests in containers.

### Services

| Service | Image | Ports | Purpose |
| --------- | ------- | ------- | --------- |
| **selenium** | `selenium/standalone-chromium:4` | 4444, 7900 | Headless Selenium grid |
| **tests** | `maven:3.9.12-eclipse-temurin-25` | — | Runs Maven test command |
| **android-emulator-google** | Android emulator image (profile) | 5555, 8554 | Android emulator for mobile tests |
| **appium-google** | `appium/appium` (profile) | 4724 | Appium server connected to the emulator |

### Run Tests in Docker

```bash
# From the repository root
cd docker

# Run Smoke tests against Selenium grid
docker compose up --abort-on-container-exit

# Run a specific suite
SUITE_XML_FILE=RegressionTest.xml docker compose up --abort-on-container-exit

# Include mobile emulator (Android + Appium)
docker compose --profile mobile-emulator-google up --abort-on-container-exit
```

The `tests` service mounts the repository root as `/workspace` and runs:

```bash
mvn -B -ntp -pl test-automation-framework test -DsuiteXmlFile=${SUITE_XML_FILE} -Dheadless=${HEADLESS}
```

### Environment Variables

| Variable | Default | Description |
| ---------- | --------- | ------------- |
| `HEADLESS` | `true` | Run browsers in headless mode |
| `SELENIUM_REMOTE_URL` | `http://selenium:4444/wd/hub` | Remote WebDriver URL |
| `SUITE_XML_FILE` | `SmokeTest.xml` | TestNG suite file to run |
| `MOBILE_WEB_RUN_MODE` | `selenium` | Mode for mobile web tests |

## Tech Stack

| Dependency | Version | Purpose |
| ------------ | --------- | --------- |
| **Java** | 25 | Runtime |
| **TestNG** | 7.12.0 | Test framework |
| **Selenium** | 4.50.0 | Browser automation |
| **Playwright** | 1.63.0 | Modern browser automation |
| **Cucumber** | 8.0.4 | BDD testing |
| **RestAssured** | 6.0.1 | REST API testing |
| **OkHttp** | 5.5.0 | HTTP client |
| **Appium** | 10.1.1 | Mobile automation |
| **WireMock** | 3.0.1 | API mocking |
| **ExtentReports** | 2.41.2 | HTML test reports |
| **Log4j2** | 2.26.1 | Logging |
| **AssertJ** | 4.0.0-M1 | Fluent assertions |
| **JSoup** | 1.23.2 | HTML parsing (self-healing) |
| **Apache POI** | 5.5.1 | Excel test data |
| **JavaFaker** | 1.0.2 | Fake data generation |
| **Guice** | 7.0.0 | Dependency injection |
| **Lombok** | 1.18.48 | Boilerplate reduction |

## Quick Start

### Prerequisites

- JDK 25+
- Maven 3.9+
- Docker (for containerized test execution)

### Run Locally

```bash
# Run the default Sanity suite (unit tests only)
mvn -pl test-automation-framework test

# Run Smoke tests
mvn -pl test-automation-framework test -DsuiteXmlFile=SmokeTest.xml

# Run Regression tests
mvn -pl test-automation-framework test -DsuiteXmlFile=RegressionTest.xml
```

### Install Playwright Browsers

```bash
mvn -pl test-automation-framework exec:java -Dexec.mainClass="com.microsoft.playwright.CLI" -Dexec.args="install"
```

### Generate ExtentReport

After running tests, the HTML report is available at:

```text
test-automation-framework/test-output/ExtentReport/ExtentReport.html
```

## Project Structure

```text
test-automation-framework/
├── pom.xml                              # Maven build configuration
├── testRunner/
│   └── suiteFiles/                      # TestNG suite definitions
│       ├── SmokeTest.xml
│       ├── RegressionTest.xml
│       └── SanityTest.xml
├── test-output/                         # Generated test reports
│   └── ExtentReport/
│       ├── ExtentReport.html            # HTML report
│       ├── ScreenShot/                  # Failure screenshots
│       └── Test-Automation.log
└── src/test/
    ├── java/
    │   ├── api/                         # API tests
    │   │   ├── dsa/                     # Health checks
    │   │   └── examples/                # RestAssured, OkHttp, WireMock demos
    │   ├── config/                      # Configuration loaders
    │   │   ├── Constants.java
    │   │   ├── EnvironmentConfig.java   # .env variable loader
    │   │   ├── ExtentReportHandler.java
    │   │   ├── PropertiesFileReader.java
    │   │   ├── ResourceLoader.java
    │   │   └── RunConfig.java
    │   ├── core/                        # Base test case classes
    │   │   ├── TestCase.java            # Base: ExtentReports + lifecycle
    │   │   ├── GuiTestCase.java         # Selenium UI base
    │   │   ├── PlaywrightGuiTestCase.java
    │   │   ├── ApiTestCase.java         # API test base
    │   │   ├── UnitTestCase.java
    │   │   ├── FailureListener.java     # TestNG listener
    │   │   ├── FailureRetryAnalyzer.java
    │   │   ├── TestGroups.java
    │   │   └── mobile/                  # Mobile test bases
    │   ├── selfhealing/                 # Self-healing locator engine
    │   │   ├── SelfHealingLocator.java
    │   │   ├── LocatorHealer.java
    │   │   ├── Levenshtein.java
    │   │   ├── Similarity.java
    │   │   ├── HealingHints.java
    │   │   ├── HealedSelector.java
    │   │   └── SelfHealingConfig.java
    │   ├── ui/                          # UI test cases
    │   │   ├── app/                     # Mobile app tests (Android)
    │   │   └── tangerine/
    │   │       ├── selenium/            # Selenium UI tests
    │   │       ├── playwright/          # Playwright UI tests
    │   │       └── cucumber/            # Cucumber BDD tests
    │   ├── unit/                        # Unit tests
    │   ├── utils/                       # Utility classes
    │   │   ├── CommandUtils.java
    │   │   ├── ExcelFileReader.java
    │   │   ├── OperationSystemDetector.java
    │   │   ├── ScreenShotHandler.java
    │   │   ├── SeleniumWrapper.java
    │   │   └── StringUtils.java
    │   └── webpages/                    # Page Object models
    │       ├── selenium/
    │       │   ├── SeleniumBasePage.java
    │       │   ├── google/
    │       │   └── tangerine/
    │       └── playwright/
    │           ├── PlaywrightBasePage.java
    │           └── tangerine/
    └── resources/
        ├── ChromeDrivers/               # Browser drivers
        ├── init-config.properties       # URLs, browser, timeouts
        ├── secrets.properties           # Credentials
        ├── extent.properties            # ExtentReports config
        ├── extent-report-config.xml
        ├── log4j2.xml                   # Logging config
        ├── features/                    # Cucumber feature files
        ├── excel-test-data.xlsx         # Excel test data
        └── theatre-data.csv             # CSV test data
```

## Adding New Tests

1. **UI Test (Selenium):** Extend `GuiTestCase` and annotate with `@Test(groups = {"smoke"})` or `@Test(groups = {"regression"})`.
2. **UI Test (Playwright):** Extend `PlaywrightGuiTestCase` and use `@Test(groups = {"playwright"})`.
3. **API Test:** Extend `ApiTestCase` and use RestAssured or OkHttp.
4. **Page Object:** Create a new class under `webpages/selenium/` or `webpages/playwright/` following the existing pattern.
5. **Cucumber Feature:** Add a `.feature` file under `src/test/resources/features/` and corresponding step definitions.
6. **New Test Suite:** Add an XML file under `testRunner/suiteFiles/` and reference it via `-DsuiteXmlFile=YourSuite.xml`.

Tests annotated with recognized group names (`smoke`, `regression`, `playwright`) are automatically picked up by the existing suite files via package scanning.
