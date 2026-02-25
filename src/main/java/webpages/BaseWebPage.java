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

/**
 * Base class for all web pages.
 */
public class BaseWebPage {
    protected final static Logger logger = LogManager.getLogger(BaseWebPage.class.getName());
    protected static WebDriver driver = null;

    // Shared page object constructor.
    public BaseWebPage(WebDriver driver) {
        BaseWebPage.driver = driver;
    }

    public static WebDriver getDriver(String browser) {
        if (driver != null)
            return driver;
        WebDriver initializedDriver;
        if (browser == null) {
            initializedDriver = createChromeDriver();
        } else {
            initializedDriver = switch (browser.toLowerCase()) {
                case "firefox" -> createFirefoxDriver();
                case "ie" -> createIEDriver();
                default -> createChromeDriver();
            };
        }

        if (initializedDriver == null) {
            throw new IllegalStateException(buildDriverInitErrorMessage(browser));
        }
        return initializedDriver;
    }

    private static WebDriver createChromeDriver() {
        String remoteWebDriverUrl = getRemoteWebDriverUrl();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--incognito");
        if (RunConfig.isHeadless()) {
            options.addArguments("--headless=new");          // Chrome modern headless
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu"); // mostly harmless; helps some environments
            options.addArguments("--window-size=1920,1080"); // IMPORTANT for headless stability
        }
        // Force browser UI/content language to English for stable locators and assertions.
        options.addArguments("--lang=en-US");

        java.util.Map<String, Object> prefs = new java.util.HashMap<>();
        prefs.put("intl.accept_languages", "en-US,en");
        options.setExperimentalOption("prefs", prefs);

        if (remoteWebDriverUrl != null && !remoteWebDriverUrl.isBlank()) {
            try {
                driver = new RemoteWebDriver(new URL(remoteWebDriverUrl), options);
            } catch (MalformedURLException e) {
                logger.fatal("Invalid SELENIUM_REMOTE_URL: " + remoteWebDriverUrl, e);
                return null;
            }
            return driver;
        }

        if (!OperationSystemDetector.isWindows() && !OperationSystemDetector.isMac()) {
            logger.fatal("Unsupported platform: " + OperationSystemDetector.getOS());
            return null;
        }

        driver = new ChromeDriver(options);
        // Extra safety: ensure size is applied even if args are ignored by the driver/platform.
        if (RunConfig.isHeadless()) {
            driver.manage().window().setSize(new Dimension(1920, 1080));
        }

        return driver;
    }

    private static WebDriver createFirefoxDriver() {
        return null;
    }

    private static WebDriver createIEDriver() {
        return null;
    }

    public static BaseWebPage gotoHomePage(String org) {
        WebDriver currentDriver = getDriver(PropertiesFileReader.getBrowser());
        if (org.equalsIgnoreCase("Amazon")) {
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
}
