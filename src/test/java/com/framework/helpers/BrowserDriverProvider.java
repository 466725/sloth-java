package com.framework.helpers;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.framework.utilities.PlatformDetector;

import config.Constants;

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
		DriverMaster.driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT_TIME, TimeUnit.SECONDS);
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
	 * Create a web browser driver, per IE
	 * 
	 * @return WebDriver, create a driver for IE and then return
	 */
	private static WebDriver createIEDriver() {
		if (PlatformDetector.isWindows()) {
			File file = new File(Constants.WIN64_DRIVER_IE);
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			logger.info(System.getProperty("webdriver.ie.driver"));
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			return new InternetExplorerDriver();
		} else if (PlatformDetector.isMac()) {
			logger.fatal("Platform is: " + PlatformDetector.getOS()); 
			logger.fatal("oops ^_^, mission impossible, ie not available on osx!");
			logger.fatal("Driver is null!");
			return null;
		} else {
			logger.fatal("Platform is: " + PlatformDetector.getOS()); 
			logger.fatal("oops ^_^, failed to validate OS version!");
			logger.fatal("Driver is null!");
			return null;
		}
	}
}