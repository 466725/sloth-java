package com.jmeter;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CineplexLoadTestDemoTwoGUI {
	private final static String API_KEY = "4761df1b4dc8ecb8e0a50337";
	private final static String API_SECRET = "d1a41bb1f0c1235fb4215fbaf47566f38bd3fb08ef5fb6d0ff004e09c3744ed4777948eb";
	private final static String BASE = "a.blazemeter.com";
	String appUrl = "https://uat-www.cineplex.com";
	WebDriverWait wait;
	private final static String curl = String.format("https://%s/api/v4/grid/wd/hub", BASE);
	private static RemoteWebDriver driver;

	@BeforeTest
	public static void setUp() throws MalformedURLException {
		URL url = new URL(curl);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("blazemeter.apiKey", API_KEY);
		capabilities.setCapability("blazemeter.apiSecret", API_SECRET);
		capabilities.setCapability("blazemeter.reportName", "Demo Grid test");
		capabilities.setCapability("blazemeter.sessionName", "Chrome browser test");
		capabilities.setCapability("browserName", "chrome");
		capabilities.setCapability("browserVersion", "69");
		driver = new RemoteWebDriver(url, capabilities);
		String reportURL = String.format("https://%s/api/v4/grid/sessions/%s/redirect/to/report", BASE,
				driver.getSessionId());
		System.out.println("Report url: " + reportURL);
		openInBrowser(reportURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@AfterTest
	public static void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	public static void openInBrowser(String string) {
		if (java.awt.Desktop.isDesktopSupported()) {
			try {
				java.awt.Desktop.getDesktop().browse(new URI(string));
			} catch (Exception ex) {
				System.out.println("Failed to open in browser");
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("Hello, BlazeMeter!");
	}

	@Test
	public void testCasePassed() {
		Assert.assertTrue(true);
	}

	@Test
	public void testCaseFailed() {
		Assert.assertTrue(false);
	}

	@Test
	public void testCaseTranctionWithoutSeatSelection() {
		driver.get("http://www.google.com");
	}
}
