package webpages;

import config.PropertiesFileReader;
import config.RunConfig;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utilities.OperationSystemDetector;
import webpages.amazon.AmazonHomePage;
import webpages.tangerine.TangerineHomePage;

/**
 * Base class for all web pages.
 */
public class BaseWebPage {
    protected final static Logger logger = LogManager.getLogger(BaseWebPage.class.getName());
    protected static WebDriver driver = null;

    // Constructor
    public BaseWebPage(WebDriver driver) {
        BaseWebPage.driver = driver;
    }

    public static WebDriver getDriver(String browser) {
        if (driver != null)
            return driver;
        if (browser == null) {
            return createChromeDriver();
        }
        return switch (browser.toLowerCase()) {
            case "firefox" -> createFirefoxDriver();
            case "ie" -> createIEDriver();
            default -> createChromeDriver();
        };
    }

    private static WebDriver createChromeDriver() {
        if (!OperationSystemDetector.isWindows() && !OperationSystemDetector.isMac()) {
            logger.fatal("Unsupported platform: " + OperationSystemDetector.getOS());
            return null;
        }
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
        // Force English
        options.addArguments("--lang=en-US");

        java.util.Map<String, Object> prefs = new java.util.HashMap<>();
        prefs.put("intl.accept_languages", "en-US,en");
        options.setExperimentalOption("prefs", prefs);

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
        getDriver(PropertiesFileReader.getBrowser());
        if (org.equalsIgnoreCase("Amazon")) {
            driver.get(PropertiesFileReader.getAmazonURL());
            return new AmazonHomePage(driver);
        }
        driver.get(PropertiesFileReader.getTangerineURL());
        return new TangerineHomePage(driver);
    }

    public BaseWebPage gotoSigninPage() {
        return null;
    }
}