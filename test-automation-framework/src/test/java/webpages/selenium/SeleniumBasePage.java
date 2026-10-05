package webpages.selenium;

import config.PropertiesFileReader;
import config.RunConfig;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.NoSuchDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.OperationSystemDetector;
import webpages.selenium.tangerine.HomePage;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Base class for all Selenium web pages.
 */
public class SeleniumBasePage {
    private static final Logger logger = LogManager.getLogger(SeleniumBasePage.class.getName());
    private static final String BROWSER_CHROME = "chrome";
    private static final String BROWSER_FIREFOX = "firefox";
    private static final String BROWSER_IE = "ie";
    private static final String WD_HUB_SUFFIX = "/wd/hub";

    protected static WebDriver driver = null;

    // Shared page object constructor.
    public SeleniumBasePage(WebDriver driver) {
        SeleniumBasePage.driver = driver;
    }

    public static WebDriver getDriver(String browser) {
        if (driver != null) {
            return driver;
        }

        String normalizedBrowser = normalize(browser, BROWSER_CHROME);
        WebDriver initializedDriver;
        initializedDriver = switch (normalizedBrowser) {
            case BROWSER_FIREFOX -> createFirefoxDriver();
            case BROWSER_IE -> createIEDriver();
            default -> createChromeDriver();
        };

        if (initializedDriver == null) {
            throw new IllegalStateException(buildDriverInitErrorMessage(normalizedBrowser));
        }

        driver = initializedDriver;
        return initializedDriver;
    }

    private static WebDriver createChromeDriver() {
        String remoteWebDriverUrl = getRemoteWebDriverUrl();
        ChromeOptions options = buildChromeOptions();

        if (remoteWebDriverUrl != null && !remoteWebDriverUrl.isBlank()) {
            return createRemoteChromeDriver(remoteWebDriverUrl, options);
        }

        if (!OperationSystemDetector.isWindows() && !OperationSystemDetector.isMac()) {
            logger.fatal("Unsupported platform: " + OperationSystemDetector.getOS());
            return null;
        }

        WebDriver localDriver;
        try {
            localDriver = new ChromeDriver(options);
        } catch (NoSuchDriverException exception) {
            throw new IllegalStateException(
                    "Local ChromeDriver could not be resolved (Selenium Manager failed or was blocked). "
                            + "Run against Docker Selenium instead: start it with "
                            + "'docker compose --project-directory . -f .\\docker\\docker-compose.yml up -d selenium' "
                            + "and pass -Dselenium.remote.url=http://localhost:4444 (or set SELENIUM_REMOTE_URL), "
                            + "or provide an approved driver via -Dwebdriver.chrome.driver=<path>.",
                    exception
            );
        }
        // Extra safety: ensure size is applied even if args are ignored by the driver/platform.
        if (RunConfig.isHeadless()) {
            localDriver.manage().window().setSize(new Dimension(1920, 1080));
        }

        return localDriver;
    }

    private static WebDriver createRemoteChromeDriver(String remoteWebDriverUrl, ChromeOptions options) {
        int maxAttempts = getIntSystemOrEnvOrDefault("selenium.session.retry.count", "SELENIUM_SESSION_RETRY_COUNT", 4);
        int retryDelaySeconds = getIntSystemOrEnvOrDefault("selenium.session.retry.delay.seconds", "SELENIUM_SESSION_RETRY_DELAY_SECONDS", 10);
        List<String> candidateUrls = buildCandidateSeleniumUrls(remoteWebDriverUrl);
        Exception lastError = null;

        logger.info("web.session.create.start | requestedUrl=" + remoteWebDriverUrl
                + " | candidateUrls=" + candidateUrls
                + " | maxAttempts=" + maxAttempts
                + " | retryDelaySeconds=" + retryDelaySeconds);

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            for (Iterator<String> iterator = candidateUrls.iterator(); iterator.hasNext(); ) {
                String candidateUrl = iterator.next();
                try {
                    WebDriver remoteDriver = new RemoteWebDriver(URI.create(candidateUrl).toURL(), options);
                    logger.info("web.session.create.success | seleniumUrl=" + candidateUrl + " | attempt=" + attempt + "/" + maxAttempts);
                    return remoteDriver;
                } catch (IllegalArgumentException | MalformedURLException e) {
                    logger.fatal("Invalid SELENIUM_REMOTE_URL: " + candidateUrl, e);
                    return null;
                } catch (Exception exception) {
                    lastError = exception;
                    String reason = exception.getMessage();

                    if (reason != null
                            && reason.contains("Response code 404")
                            && candidateUrl.endsWith(WD_HUB_SUFFIX)
                            && candidateUrls.size() > 1) {
                        iterator.remove();
                        logger.info("web.session.create.fallback | reason=legacy_404 | droppedUrl=" + candidateUrl);
                        continue;
                    }

                    logger.warn("web.session.create.retry | attempt=" + attempt + "/" + maxAttempts
                            + " | seleniumUrl=" + candidateUrl + " | reason=" + reason);
                }
            }

            if (attempt < maxAttempts) {
                try {
                    Thread.sleep(Duration.ofSeconds(retryDelaySeconds).toMillis());
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    throw new IllegalStateException("Interrupted while retrying Selenium session creation.", interruptedException);
                }
            }
        }

        throw new IllegalStateException(
                "Unable to start Selenium WebDriver session after " + maxAttempts + " attempts. Selenium URLs tried: " + candidateUrls,
                lastError
        );
    }

    private static WebDriver createFirefoxDriver() {
        return null;
    }

    private static WebDriver createIEDriver() {
        return null;
    }

    public static SeleniumBasePage gotoHomePage(String org) {
        WebDriver currentDriver = getDriver(PropertiesFileReader.getBrowser());
        currentDriver.get(PropertiesFileReader.getTangerineURL());
        return new HomePage(currentDriver);
    }

    public SeleniumBasePage gotoSigninPage() {
        return null;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static WebDriver getCurrentDriver() {
        return driver;
    }

    private static String getRemoteWebDriverUrl() {
        String prop = System.getProperty("selenium.remote.url");
        if (prop != null && !prop.isBlank()) {
            return prop;
        }
        String env = System.getenv("SELENIUM_REMOTE_URL");
        if (env != null && !env.isBlank()) {
            return env;
        }
        return null;
    }

    private static List<String> buildCandidateSeleniumUrls(String remoteWebDriverUrl) {
        String normalized = remoteWebDriverUrl.endsWith("/")
                ? remoteWebDriverUrl.substring(0, remoteWebDriverUrl.length() - 1)
                : remoteWebDriverUrl;

        List<String> urls = new ArrayList<>();
        urls.add(normalized);

        if (!normalized.endsWith(WD_HUB_SUFFIX)) {
            urls.add(normalized + WD_HUB_SUFFIX);
        } else {
            urls.add(normalized.substring(0, normalized.length() - WD_HUB_SUFFIX.length()));
        }
        return urls;
    }

    private static int getIntSystemOrEnvOrDefault(String propertyKey, String envKey, int defaultValue) {
        String value = System.getProperty(propertyKey);
        if (value == null || value.isBlank()) {
            value = System.getenv(envKey);
        }
        if (value == null || value.isBlank()) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException exception) {
            logger.warn("Invalid integer configuration | property=" + propertyKey + " | env=" + envKey
                    + " | value=" + value + " | usingDefault=" + defaultValue);
            return defaultValue;
        }
    }

    private static String buildDriverInitErrorMessage(String browser) {
        return String.format(
                "WebDriver initialization failed. browser=%s, os.name=%s, SELENIUM_REMOTE_URL=%s. " +
                        "On Linux CI, provide SELENIUM_REMOTE_URL (e.g. http://selenium:4444 or http://selenium:4444/wd/hub) " +
                        "or use a supported local browser setup.",
                browser,
                System.getProperty("os.name"),
                System.getenv("SELENIUM_REMOTE_URL")
        );
    }

    private static ChromeOptions buildChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--incognito");

        if (RunConfig.isHeadless()) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }

        // Force browser UI/content language to English for stable locators and assertions.
        options.addArguments("--lang=en-US");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("intl.accept_languages", "en-US,en");
        options.setExperimentalOption("prefs", prefs);
        return options;
    }

    private static String normalize(String value, String defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value.trim().toLowerCase(Locale.ROOT);
    }
}
