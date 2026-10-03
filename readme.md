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

- Design and prototype: [Self-Healing Framework](#self-healing-framework)
- Enable/disable: `-Dself.healing.enabled=true|false` or `SELF_HEALING_ENABLED=true|false`
- Tuning: `-Dself.healing.minScore=<value>`, `-Dself.healing.maxCandidates=<value>`

## Configuration

## Self-Healing Framework

This Java locator auto-healing prototype uses Selenium WebDriver, JSoup, and
Levenshtein similarity for scoring. It illustrates a design that can be integrated
into a Java test framework; the snippets below are not a complete standalone
implementation.

### Locator Auto-Healing Workflow

```text
Try original locator
        |
        v
Element not found
        |
        v
Parse DOM
        |
        v
Find similar elements
        |
        v
Score candidates
        |
        v
Pick best match
        |
        v
Retry action
```

### Maven Dependencies

The prototype requires Selenium and JSoup. These dependencies are already
declared in `test-automation-framework/pom.xml`; use the versions maintained there
rather than adding duplicate declarations.

```xml
<dependencies>
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.50.0</version>
    </dependency>
    <dependency>
        <groupId>org.jsoup</groupId>
        <artifactId>jsoup</artifactId>
        <version>1.17.2</version>
    </dependency>
</dependencies>
```

### Example Test: Simulating a Broken Locator

```java
WebDriver driver = new ChromeDriver();
driver.get("https://example.com");

WebElement element = SmartFinder.findElement(
        driver, By.xpath("//button[text()='Login']"));
element.click();
```

If the locator breaks, `SmartFinder` attempts to heal it.

### Smart Finder

```java
public class SmartFinder {

    public static WebElement findElement(WebDriver driver, By locator) {
        try {
            return driver.findElement(locator);
        } catch (NoSuchElementException e) {
            System.out.println("Locator broken. Attempting auto-healing...");
            return healLocator(driver, locator);
        }
    }
}
```

### Healing Engine

The prototype searches button elements and uses the hard-coded expected text
`Login` to score candidates.

```java
private static WebElement healLocator(WebDriver driver, By locator) {
    String html = driver.getPageSource();
    Document doc = Jsoup.parse(html);
    Elements candidates = doc.select("button");

    Element bestMatch = null;
    double bestScore = 0;
    String targetText = "Login";

    for (Element e : candidates) {
        String candidateText = e.text();
        double score = similarity(targetText, candidateText);

        if (score > bestScore) {
            bestScore = score;
            bestMatch = e;
        }
    }

    if (bestMatch != null) {
        String healedXpath = generateXpath(bestMatch);
        System.out.println("Healed locator: " + healedXpath);
        return driver.findElement(By.xpath(healedXpath));
    }

    throw new NoSuchElementException("Unable to heal locator");
}
```

### Text Similarity

```java
private static double similarity(String s1, String s2) {
    int distance = levenshtein(s1, s2);
    int maxLength = Math.max(s1.length(), s2.length());
    return 1.0 - ((double) distance / maxLength);
}
```

### Levenshtein Distance

```java
public static int levenshtein(String a, String b) {
    int[][] dp = new int[a.length() + 1][b.length() + 1];

    for (int i = 0; i <= a.length(); i++)
        dp[i][0] = i;

    for (int j = 0; j <= b.length(); j++)
        dp[0][j] = j;

    for (int i = 1; i <= a.length(); i++) {
        for (int j = 1; j <= b.length(); j++) {
            int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
            dp[i][j] = Math.min(
                    Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                    dp[i - 1][j - 1] + cost);
        }
    }

    return dp[a.length()][b.length()];
}
```

### Generating XPath for a Healed Element

```java
private static String generateXpath(Element element) {
    String tag = element.tagName();
    String text = element.text();
    return "//" + tag + "[text()='" + text + "']";
}
```

### Example Scenario

The original locator is:

```xpath
//button[text()='Login']
```

If a developer changes the button text to `Sign In`, the engine compares `Login`
with candidate text and may generate:

```xpath
//button[text()='Sign In']
```

It then retries with that locator. Text similarity alone does not guarantee the
correct match: the prototype has no confidence threshold and may select an
unrelated button. Production use also requires handling empty strings, escaping
XPath text literals, and checking that a candidate identifies a unique element.

### Improving Candidate Scoring

Combine multiple features instead of relying on text alone. One proposed
weighting is:

```text
score =
    0.3 * tag match
  + 0.3 * text similarity
  + 0.2 * class similarity
  + 0.1 * id similarity
  + 0.1 * DOM position
```

An alternative weighting is:

```java
score += textSimilarity * 0.4;
score += classSimilarity * 0.2;
score += idSimilarity * 0.2;
score += tagMatch * 0.2;
```

These additional features can improve match accuracy; validate the weights and
confidence threshold against representative DOM changes before using automatic
healing in tests.
