compose := "docker compose --project-directory . -f docker/docker-compose.yml"

default: list

# List available commands
list:
    @just --list

# Build all modules and install them in the local Maven repository
build:
    mvn -q -DskipTests clean install

# Clean generated build output
clean:
    mvn clean

# Run tests for all modules
test:
    mvn test

# Run a TestNG suite in test-automation-framework
test-framework suite="SanityTest.xml":
    mvn -pl test-automation-framework test "-DsuiteXmlFile={{ suite }}"

# Run a TestNG suite against Docker Selenium
test-framework-remote suite="SmokeTest.xml":
    mvn -pl test-automation-framework test "-DsuiteXmlFile={{ suite }}" "-Dselenium.remote.url=http://localhost:4444"

# Run tests for spring-boot-demo
test-demo:
    mvn -pl spring-boot-demo test

# Run tests for skill-spring
test-skill-spring:
    mvn -pl skill-spring test

# Run Cucumber scenarios in test-automation-framework
test-cucumber:
    mvn -pl test-automation-framework test "-Dtest=ui.tangerine.cucumber.CucumberRunnerTest"

# Start the Spring Boot demo application
run-demo:
    mvn -pl spring-boot-demo spring-boot:run

# Start Docker Selenium
docker-selenium:
    {{ compose }} up -d selenium

# Start Selenium, the Android emulator, and Appium
docker-mobile:
    {{ compose }} --profile mobile-emulator-google up -d selenium android-emulator-google appium-google

# Run the Docker test service with the selected TestNG suite
docker-test suite="SmokeTest.xml":
    {{ compose }} run --rm -e SUITE_XML_FILE={{ suite }} tests

# Show Docker service status
docker-status:
    {{ compose }} --profile mobile-emulator-google ps -a

# Show recent Docker service logs
docker-logs:
    {{ compose }} --profile mobile-emulator-google logs --tail 200

# Stop and remove Docker services and their network
docker-down:
    {{ compose }} --profile "*" down

# Install Playwright's Chromium browser
playwright-install:
    mvn -pl test-automation-framework -DskipTests exec:java "-Dexec.mainClass=com.microsoft.playwright.CLI" "-Dexec.args=install chromium"
