package com.framework.helpers;

import com.utilities.PlatformDetector;
import config.Constants;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Web browser driver provider
 *
 * @author Weipeng Zheng
 *
 */
public class BrowserDriverProvider {
    protected final static Logger logger = LogManager.getLogger(BrowserDriverProvider.class.getName());

    /**
     * Private Constructor, to make this class a singleton one.
     *
     */
    private BrowserDriverProvider() {
        logger.info("I am here to guarantee singleton! ");
    }

    /**
     * Inner class, to encapsulate database statement securely
     *
     */
    private static class DriverMaster {
        private static WebDriver driver = null;
    }

    /**
     * Create a web browser driver
     *
     * @param browser Firefox or Chrome or IE...
     * @return WebDriver, create one of them and then return
     */
    public static WebDriver createDriver(String browser) {
        if (browser.toString().equalsIgnoreCase("Firefox")) {
            DriverMaster.driver = createFirefoxDriver();
        } else if (browser.toString().equalsIgnoreCase("IE")) {
            DriverMaster.driver = createIEDriver();
        } else {
            DriverMaster.driver = createChromeDriver();
        }
        //DriverMaster.driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT_TIME, TimeUnit.SECONDS);
        return DriverMaster.driver;
    }

    /**
     * Create a web browser driver
     *
     * @param browser Firefox or Chrome or IE...
     * @return WebDriver, create one of them and then return
     */
    public static WebDriver createDriver(String browser, boolean blockPopup) {
        if (browser.toString().equalsIgnoreCase("Firefox")) {
            if (blockPopup)
                DriverMaster.driver = createFirefoxDriver(true);
            DriverMaster.driver = createFirefoxDriver();
        } else if (browser.toString().equalsIgnoreCase("IE")) {
            DriverMaster.driver = createIEDriver();
        } else {
            if (blockPopup)
                DriverMaster.driver = createChromeDriver(true);
            DriverMaster.driver = createChromeDriver();
        }
        //DriverMaster.driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT_TIME, TimeUnit.SECONDS);
        return DriverMaster.driver;
    }

    /**
     * Create a web browser driver, per Chrome
     *
     * @return WebDriver, create a driver for Chrome and then return
     */
    private static WebDriver createChromeDriver() {
        if (PlatformDetector.isWindows()) {
            File file = new File(Constants.WIN64_DRIVER_CHROME);
            System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
            logger.info(System.getProperty("webdriver.chrome.driver"));
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            return new ChromeDriver(options);
        } else if (PlatformDetector.isMac()) {
            File file = new File(Constants.OSX64_DRIVER_CHROME);
            System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
            logger.info(System.getProperty("webdriver.chrome.driver"));
            return new ChromeDriver();
        } else {
            logger.fatal("Platform is: " + PlatformDetector.getOS());
            logger.fatal("oops ^_^, failed to validate OS version!");
            logger.fatal("Driver is null!");
            return null;
        }
    }

    /**
     * Create a web browser driver, per Chrome
     *
     * @return WebDriver, create a driver for Chrome and then return
     */
    private static WebDriver createChromeDriver(boolean blockPopup) {
        if (!blockPopup)
            return createChromeDriver();
        if (PlatformDetector.isWindows()) {
            File file = new File(Constants.WIN64_DRIVER_CHROME);
            System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
            logger.info(System.getProperty("webdriver.chrome.driver"));
            ChromeOptions options = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("profile.default_content_setting_values.notifications", 2);
            options.setExperimentalOption("prefs", prefs);
            options.addArguments("--start-maximized");
            return new ChromeDriver(options);
        } else if (PlatformDetector.isMac()) {
            File file = new File(Constants.OSX64_DRIVER_CHROME);
            System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
            logger.info(System.getProperty("webdriver.chrome.driver"));
            return new ChromeDriver();
        } else {
            logger.fatal("Platform is: " + PlatformDetector.getOS());
            logger.fatal("oops ^_^, failed to validate OS version!");
            logger.fatal("Driver is null!");
            return null;
        }
    }

    /**
     * Create a web browser driver, per Firefox
     *
     * @return WebDriver, create a driver for Firefox and then return
     */
    private static WebDriver createFirefoxDriver() {
        if (PlatformDetector.isWindows()) {
            File file = new File(Constants.WIN64_DRIVER_FIREFOX);
            System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
            logger.info(System.getProperty("webdriver.gecko.driver"));
            WebDriver driver = new FirefoxDriver();
            driver.manage().window().maximize();
            ((JavascriptExecutor) driver).executeScript("window.focus();");
            return driver;
        } else if (PlatformDetector.isMac()) {
            File file = new File(Constants.OSX64_DRIVER_FIREFOX);
            System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
            logger.info(System.getProperty("webdriver.gecko.driver"));
            return new FirefoxDriver();
        } else {
            logger.fatal("Platform is: " + PlatformDetector.getOS());
            logger.fatal("oops ^_^, failed to validate OS version!");
            logger.fatal("Driver is null!");
            return null;
        }
    }

    /**
     * Create a web browser driver, per Firefox
     *
     * @return WebDriver, create a driver for Firefox and then return
     */
    private static WebDriver createFirefoxDriver(boolean blockPopup) {
        if (!blockPopup)
            return createFirefoxDriver();
        if (PlatformDetector.isWindows()) {
            File file = new File(Constants.WIN64_DRIVER_FIREFOX);
            System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
            logger.info(System.getProperty("webdriver.gecko.driver"));

            FirefoxOptions options = new FirefoxOptions();
//			options.setCapability("dom.webnotifications.enabled", false);
//			options.setCapability("dom.push.enabled", false);

            WebDriver driver = new FirefoxDriver(options);
            driver.manage().window().maximize();
            ((JavascriptExecutor) driver).executeScript("window.focus();");
            return driver;
        } else if (PlatformDetector.isMac()) {
            File file = new File(Constants.OSX64_DRIVER_FIREFOX);
            System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
            logger.info(System.getProperty("webdriver.gecko.driver"));
            return new FirefoxDriver();
        } else {
            logger.fatal("Platform is: " + PlatformDetector.getOS());
            logger.fatal("oops ^_^, failed to validate OS version!");
            logger.fatal("Driver is null!");
            return null;
        }
    }

    /**
     * Create a web browser driver, per IE
     *
     * @return WebDriver, create a driver for IE and then return
     */
    private static WebDriver createIEDriver() {
        return null;
    }
}