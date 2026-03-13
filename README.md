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

### Repository Modules

*   `test-automation-framework` - Main automation framework (TestNG, Selenium, Appium, API, tutorials)
*   `spring-boot-demo` - Spring Boot learning module

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
*   All framework configuration are done via `test-automation-framework/src/main/resources/init-config.properties`.
*   Framework secrets are stored in `test-automation-framework/src/main/resources/secrets.properties`.
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
* ......

### Playwright (UI-Web)
- Playwright tests are tagged with the `playwright` TestNG group (in addition to `ui-web`, `tangerine`, etc.).
- First run may download Playwright browser binaries into your user cache (typically `~/.cache/ms-playwright`).
- Install browsers explicitly (recommended for CI/locked-down networks):
  ```bash
  # Bash/zsh
  mvn -pl test-automation-framework -DskipTests exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install chromium"

  # Windows PowerShell (quote -D... properties with dots)
  mvn -pl test-automation-framework -DskipTests exec:java "-Dexec.mainClass=com.microsoft.playwright.CLI" "-Dexec.args=install chromium"
  ```
- Control headless mode with `-Dheadless=true` and browser with `-Dplaywright.browser=chromium|firefox|webkit|chrome|msedge`.

### Self-Healing Locators (Playwright)
The framework includes an opt-in locator self-healing wrapper for Playwright page objects. When an element cannot be found (typical timeout/waiting failures), the wrapper:

* parses the current DOM (`page.content()`) with JSoup
* finds and scores similar elements (Levenshtein-based similarity + tag/id/class/attribute signals)
* generates a replacement selector and retries the action

**How to use (Page Objects)**
* Page objects that extend `webpages.PlaywrightPageObject` can call `locator(...)` instead of `page.locator(...)`.
* `locator(...)` returns a `webpages.selfhealing.SelfHealingLocator` which exposes common actions like `waitFor()`, `click()`, `fill()`.

Example:
```java
public class TangerineHomePagePlaywright extends PlaywrightPageObject {
  private final SelfHealingLocator signinButton;

  public TangerineHomePagePlaywright(Page page) {
    super(page);
    this.signinButton = locator("#login");
  }

  public void gotoSigninPage() {
    signinButton.waitFor(new Locator.WaitForOptions().setTimeout(30_000));
    signinButton.click();
  }
}
```

**Optional: Provide Healing Hints**
If a selector has weak signals (for example a CSS path), you can pass hints to improve healing accuracy:
```java
HealingHints hints = HealingHints.builder()
  .expectedTag("button")
  .expectedText("Login")
  .build();

SelfHealingLocator login = locator("css=div.header >> button", hints);
login.click();
```

**Configuration**
* Enable/disable: `-Dself.healing.enabled=true|false` or environment variable `SELF_HEALING_ENABLED=true|false`
* Minimum acceptable match score: `-Dself.healing.minScore=0.35`
* Limit candidates scored per heal attempt: `-Dself.healing.maxCandidates=800`

### Retry on Failure
* Retry is enabled by `FailureListener` + `FailureRetryAnalyzer`.
* Listener is registered via ServiceLoader: `test-automation-framework/src/test/resources/META-INF/services/org.testng.ITestNGListener`.
* Reruns (`testng-failed.xml`) use the same listener in IDE and CLI.

### Extent Report Config
* Report styling/metadata is loaded by `config.ExtentReportHandler`.
* Default config file: `test-automation-framework/src/main/resources/extent-report-config.xml`.

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
mvn -pl test-automation-framework dependency:resolve
mvn -pl spring-boot-demo dependency:resolve

# Execute framework tests (default smoke suite)
mvn -pl test-automation-framework test -Dgroups=smoke
mvn -pl test-automation-framework test -Dgroups=api
mvn -pl test-automation-framework test -Dgroups=ui-web
mvn -pl test-automation-framework test -Dgroups=playwright
mvn -pl test-automation-framework test -DsuiteXmlFile=SmokeTest.xml
mvn -pl test-automation-framework test -DsuiteXmlFile=SmokeTest.xml -Dheadless=true

# Execute Spring Boot demo tests
mvn -pl spring-boot-demo test

# Windows PowerShell examples (quoting is safe)
mvn -pl test-automation-framework test "-DsuiteXmlFile=SmokeTest.xml"
mvn -pl test-automation-framework test "-DsuiteXmlFile=SmokeTest.xml" "-Dheadless=true"
mvn -pl test-automation-framework test "-Dgroups=playwright" "-DsuiteXmlFile=SmokeTest.xml" "-Dheadless=true"
mvn -pl test-automation-framework test "-DsuiteXmlFile=RegressionTest.xml" "-Dheadless=true" "-Dgroups=api"
mvn -pl test-automation-framework test "-DsuiteXmlFile=CucumberAmazonInvalidLogin.xml" "-Dheadless=true"
```

---

## Spring Boot Demo Module

### Prerequisites
* Java 21+

### Run Spring Boot demo
```bash
mvn -pl spring-boot-demo spring-boot:run
```
Then open `http://localhost:8080/` in your browser.

### Test Spring Boot demo
```bash
mvn -pl spring-boot-demo test
```

### Useful endpoints
* `GET /api/hello`
* `GET /actuator/health`

---

### Using CI/CD of GitHub Actions
*   Run smoke SmokeTest.xml on PR
*   Run mobile-web smoke MobileWebSmokeTest.xml on PR/push (Selenium mobile emulation on GitHub runner)
*   Run regression RegressionTest.xml nightly (or scheduled)
*   Upload reports + artifacts (screenshots/logs) as build artifacts

---

## Run non-mobile tests RegressionTest.xml in local Docker environment

```bash
# Start dependencies
docker compose up -d mysql selenium

# Run default non-mobile smoke suite
docker compose run --rm --no-deps tests

# Run a specific non-mobile suite
docker compose run --rm --no-deps -e SUITE_XML_FILE=RegressionTest.xml tests
```

Note:
`docker-compose.yml` pins most test infrastructure images by digest for reproducible runs. MySQL is intentionally set to `mysql:latest`.

---

## Run mobile web tests MobileWebSmokeTest.xml in local Docker environment

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

## Run mobile app and web tests MobileLocalSmokeTest.xml in local Docker environment

```bash
# Start Google emulator + dedicated Appium sidecar + MySQL
docker compose --profile mobile-emulator-google up -d android-emulator-google appium-google selenium mysql

# Optional sanity check: emulator is visible via adb inside appium sidecar
docker exec appium-google adb devices
```

PowerShell command to run MobileLocalSmokeTest.xml locally:
```powershell
docker compose --profile mobile-emulator-google run --rm --no-deps `
  -e SUITE_XML_FILE=MobileLocalSmokeTest.xml `
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
* But Appium with Android device emulator is executed only locally `MobileLocalSmokeTest.xml`. Reasons:
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
