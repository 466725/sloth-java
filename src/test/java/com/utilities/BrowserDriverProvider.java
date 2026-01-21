package com.utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Web browser driver provider
 *
 * @author Weipeng Zheng
 */
public class BrowserDriverProvider {
    private static final Logger logger = LogManager.getLogger(BrowserDriverProvider.class);

    private BrowserDriverProvider() {
        // Private constructor to prevent instantiation
    }

    /**
     * Create and return a web browser driver instance.
     *
     * @param browser Name of the browser (e.g., Chrome, Firefox, IE)
     * @return The created WebDriver instance
     */
    public static WebDriver createDriver(String browser) {
        return switch (browser.toLowerCase()) {
            case "firefox" -> createFirefoxDriver();
            case "ie" -> createIEDriver();
            default -> createChromeDriver();
        };
    }

    private static WebDriver createChromeDriver() {
        if (!PlatformDetector.isWindows() && !PlatformDetector.isMac()) {
            logger.fatal("Unsupported platform: " + PlatformDetector.getOS());
            return null;
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {return null;}

    private static WebDriver createIEDriver() {
        return null;
    }
}