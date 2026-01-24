package webpages;

import config.PropertiesFileReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import webpages.amazon.HomePage;

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
        if (browser == null){
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

        // Force English
        options.addArguments("--lang=en-US");

        java.util.Map<String, Object> prefs = new java.util.HashMap<>();
        prefs.put("intl.accept_languages", "en-US,en");
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        return driver;
    }

    private static WebDriver createFirefoxDriver() {
        return null;
    }

    private static WebDriver createIEDriver() {
        return null;
    }

    // Quit the driver
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Verify logo
    public boolean verifyLogo() {
        return false;
    }

    public HomePage gotoHomePage() {
        getDriver(PropertiesFileReader.getBrowser());
        driver.get(PropertiesFileReader.getURL());
        return new HomePage(driver);
    }
}