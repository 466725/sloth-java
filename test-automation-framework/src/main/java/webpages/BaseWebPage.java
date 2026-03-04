package webpages;

import config.PropertiesFileReader;
import config.RunConfig;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import utilities.OperationSystemDetector;
import webpages.amazon.AmazonHomePage;
import webpages.tangerine.TangerineHomePage;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Base class for all web pages.
 */
public class BaseWebPage {
    private static final Logger logger = LogManager.getLogger(BaseWebPage.class.getName());
    private static final String BROWSER_CHROME = "chrome";
    private static final String BROWSER_FIREFOX = "firefox";
    private static final String BROWSER_IE = "ie";
    private static final String ORG_AMAZON = "amazon";
    private static final String ORG_TANGERINE = "tangerine";

    protected static WebDriver driver = null;

    // Shared page object constructor.
    public BaseWebPage(WebDriver driver) {
        BaseWebPage.driver = driver;
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
            try {
                return new RemoteWebDriver(new URL(remoteWebDriverUrl), options);
            } catch (MalformedURLException e) {
                logger.fatal("Invalid SELENIUM_REMOTE_URL: " + remoteWebDriverUrl, e);
                return null;
            }
        }

        if (!OperationSystemDetector.isWindows() && !OperationSystemDetector.isMac()) {
            logger.fatal("Unsupported platform: " + OperationSystemDetector.getOS());
            return null;
        }

        WebDriver localDriver = new ChromeDriver(options);
        // Extra safety: ensure size is applied even if args are ignored by the driver/platform.
        if (RunConfig.isHeadless()) {
            localDriver.manage().window().setSize(new Dimension(1920, 1080));
        }

        return localDriver;
    }

    private static WebDriver createFirefoxDriver() {
        return null;
    }

    private static WebDriver createIEDriver() {
        return null;
    }

    public static BaseWebPage gotoHomePage(String org) {
        WebDriver currentDriver = getDriver(PropertiesFileReader.getBrowser());
        String normalizedOrg = normalize(org, ORG_TANGERINE);
        if (ORG_AMAZON.equals(normalizedOrg)) {
            currentDriver.get(PropertiesFileReader.getAmazonURL());
            return new AmazonHomePage(currentDriver);
        }
        currentDriver.get(PropertiesFileReader.getTangerineURL());
        return new TangerineHomePage(currentDriver);
    }

    public BaseWebPage gotoSigninPage() {
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

    private static String buildDriverInitErrorMessage(String browser) {
        return String.format(
                "WebDriver initialization failed. browser=%s, os.name=%s, SELENIUM_REMOTE_URL=%s. " +
                        "On Linux CI, provide SELENIUM_REMOTE_URL (e.g. http://localhost:4444/wd/hub) " +
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
