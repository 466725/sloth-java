import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.framework.helpers.DatabaseConnectionManager;
import com.utilities.PlatformDetector;

import config.Constants;

public class BlockBrowserPopup {
	protected final static Logger logger = LogManager.getLogger(DatabaseConnectionManager.class.getName());
	private static WebDriver driver;
	private static String URL = "https://blog.csdn.net/cool_soup29/article/details/90412610";

	@BeforeTest
	public void setUp() {
		logger.info("Test started!");
	}

	@AfterTest
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
		logger.info("Test ended!");
	}

	// @Test(priority = 3)
	public void testBlockingPopupWindow() throws Exception {
		driver = createChromeDriver();
		driver.get(URL);
		Thread.sleep(25000);
	}

	@Test(priority = 3)
	public void testDismissingPopupWindow() throws Exception {
		driver = createFirefoxDriver();
		driver.get(URL);
		Thread.sleep(25000);
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

			FirefoxOptions options = new FirefoxOptions();
			options.setCapability("dom.webnotifications.enabled", false);
			options.setCapability("dom.push.enabled", false);

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
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_setting_values.notifications", 2);
			options.setExperimentalOption("prefs", prefs);
			options.addArguments("--start-maximized");
			options.addArguments("disable-popup-blocking");
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
}
