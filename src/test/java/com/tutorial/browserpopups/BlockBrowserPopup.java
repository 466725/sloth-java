package com.tutorial.browserpopups;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.framework.helpers.BrowserDriverProvider;

//https://www.browserstack.com/automate/handle-popups-alerts-prompts-in-automated-tests
public class BlockBrowserPopup {
	protected final static Logger logger = LogManager.getLogger(BlockBrowserPopup.class.getName());
	private static WebDriver driver;
	private static String URL = "https://blog.csdn.net/cool_soup29/article/details/90412610";

	@BeforeTest
	public void setUp() {
		logger.info("Test started!");
	}

	@AfterTest
	public void tearDown() {
		if (driver != null)
			driver.quit();
		logger.info("Test ended!");
	}

	// Popup window will show up
	@Test(priority = 1)
	public void testPopupWindowFirefox() throws Exception {
		driver = BrowserDriverProvider.createDriver("Firefox");
		driver.get(URL);
		Thread.sleep(25000);
		driver.quit();
	}

	// Popup window will show up
	@Test(priority = 3)
	public void testPopupWindowChrome() throws Exception {
		driver = BrowserDriverProvider.createDriver("Chrome");
		driver.get(URL);
		Thread.sleep(25000);
		driver.quit();
	}

	// Popup window will show up, and will be dismissed
	@Test(priority = 5)
	public void testDismissingPopupWindowFirefox() throws Exception {
		driver = BrowserDriverProvider.createDriver("Firefox");
		driver.get(URL);
		Thread.sleep(25000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ESCAPE);
		Thread.sleep(25000);
		driver.quit();
	}

	// Popup window will show up, and will be dismissed
	@Test(priority = 7)
	public void testDismissingPopupWindowChrome() throws Exception {
		driver = BrowserDriverProvider.createDriver("Chrome");
		driver.get(URL);
		Thread.sleep(25000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ESCAPE);
		Thread.sleep(25000);
		driver.quit();
	}

	// Popup window will not show up, it's been blocked
	@Test(priority = 9)
	public void testBlockingPopupWindowFirefox() throws Exception {
		driver = BrowserDriverProvider.createDriver("Firefox", true);
		driver.get(URL);
		Thread.sleep(25000);
		driver.quit();
	}

	// Popup window will not show up, it's been blocked
	@Test(priority = 11)
	public void testBlockingPopupWindowChrome() throws Exception {
		driver = BrowserDriverProvider.createDriver("Chrome", true);
		driver.get(URL);
		Thread.sleep(25000);
		driver.quit();
	}
}
