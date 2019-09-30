package com.jmeter;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.Description;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class CineplexLoadTestDemoOneGUI {
	private final static String API_KEY = "4761df1b4dc8ecb8e0a50337";
	private final static String API_SECRET = "d1a41bb1f0c1235fb4215fbaf47566f38bd3fb08ef5fb6d0ff004e09c3744ed4777948eb";
	private final static String BASE = "a.blazemeter.com";
	private final static String curl = String.format("https://%s/api/v4/grid/wd/hub", BASE);

	private static RemoteWebDriver driver;

	@Rule
	public final TestName bzmTestCaseReporter = new TestName() {
		@Override
		protected void starting(Description description) {
			Map<String, String> map = new HashMap<>();
			map.put("testCaseName", description.getMethodName());
			map.put("testSuiteName", description.getClassName());
			driver.executeAsyncScript("/* FLOW_MARKER test-case-start */", map);
		}

		@Override
		protected void succeeded(Description description) {
			if (driver != null) {
				Map<String, String> map = new HashMap<>();
				map.put("status", "success");
				map.put("message", "");
				driver.executeAsyncScript("/* FLOW_MARKER test-case-stop */", map);
			}
		}

		@Override
		protected void failed(Throwable e, Description description) {
			Map<String, String> map = new HashMap<>();
			if (e instanceof AssertionError) {
				map.put("status", "failed");
			} else {
				map.put("status", "broken");
			}
			map.put("message", e.getMessage());
			driver.executeAsyncScript("/* FLOW_MARKER test-case-stop */", map);
		}
	};

	@BeforeClass
	public static void setUp() throws MalformedURLException {
		URL url = new URL(curl);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("blazemeter.apiKey", API_KEY);
		capabilities.setCapability("blazemeter.apiSecret", API_SECRET);
		capabilities.setCapability("blazemeter.reportName", "Demo Grid test");
		capabilities.setCapability("blazemeter.sessionName", "Chrome browser test");
		capabilities.setCapability("blazemeter.projectId", "");
		capabilities.setCapability("blazemeter.testId", "");
		capabilities.setCapability("blazemeter.buildId", "randomString");
		capabilities.setCapability("blazemeter.locationId", "harbor-");
		capabilities.setCapability("browserName", "chrome");
		capabilities.setCapability("browserVersion", "69");
		driver = new RemoteWebDriver(url, capabilities);

		String reportURL = String.format("https://%s/api/v4/grid/sessions/%s/redirect/to/report", BASE,
				driver.getSessionId());
		System.out.println("Report url: " + reportURL);
		openInBrowser(reportURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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

	@AfterClass
	public static void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testCasePassed() {
		driver.get("http://www.google.com");
	}

	public static void main(String[] args) {
		System.out.print("Hello, BlazeMeter!");
	}
}