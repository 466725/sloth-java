package com.framework.helpers;

import com.utilities.PlatformDetector;
import config.Constants;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.net.HttpCookie;
import java.util.*;

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
        if (PlatformDetector.isWindows() || PlatformDetector.isMac()) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            return new ChromeDriver(options);
        } else {
            logger.fatal("Platform is: " + PlatformDetector.getOS());
        }
        return null;
    }

    /**
     * Create a web browser driver, per Chrome
     *
     * @return WebDriver, create a driver for Chrome and then return
     */
    private static WebDriver createChromeDriver(boolean blockPopup) {
        if (!blockPopup)
            return createChromeDriver();
        if (PlatformDetector.isWindows() || PlatformDetector.isMac()) {
            ChromeOptions options = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("profile.default_content_setting_values.notifications", 2);
            options.setExperimentalOption("prefs", prefs);
            options.addArguments("--start-maximized");
            return new ChromeDriver(options);
        } else {
            logger.fatal("Platform is: " + PlatformDetector.getOS());
        }
        return null;
    }

    /**
     * Create a web browser driver, per Firefox
     *
     * @return WebDriver, create a driver for Firefox and then return
     */
    private static WebDriver createFirefoxDriver()  {
        return null;
    }

    /**
     * Create a web browser driver, per Firefox
     *
     * @return WebDriver, create a driver for Firefox and then return
     */
    private static WebDriver createFirefoxDriver(boolean blockPopup)  {
        return null;
    }

    /**
     * Create a web browser driver, per IE
     *
     * @return WebDriver, create a driver for IE and then return
     */
    private static WebDriver createIEDriver() {
        return null;
    }

    /**
     * Retrieves all http cookies associated with current login session
     *
     * @param driver web driver
     * @return List of http cookies
     */
    public static List<HttpCookie> getCookies(WebDriver driver) {
        List<HttpCookie> cookies = new ArrayList<HttpCookie>();
        if (driver != null) {
            Iterator<Cookie> driverCookies = driver.manage().getCookies().iterator();
            while (driverCookies.hasNext()) {
                Cookie c = driverCookies.next();
                HttpCookie hc = new HttpCookie(c.getName(), c.getValue());
                cookies.add(hc);
            }
        }
        return cookies;
    }
}