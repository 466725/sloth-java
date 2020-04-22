package com.cineplex.ticketing.android;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;

public class SauceLabsAndroidDemo {

	private AndroidDriver<WebElement> driver;

	@BeforeSuite
	public void setupAppium() throws MalformedURLException {
//		https://wiki.saucelabs.com/display/DOCS/Appium+Capabilities+for+Real+Device+Testing
		DesiredCapabilities caps = new DesiredCapabilities();
//      9BF029C703084859B7A85F653A502A87 -> Ticketing PROD
//      9C862FE0E2624DD2BAC1B293A753A63B -> Ticketing UAT
//      EB0105D50CF04B7E9E6F7A514B4A1F60 -> Store PROD
//      A37A095D95FD4A38B6FFA7258BC63BB3 -> Store UAT
		caps.setCapability("testobject_api_key", "EB0105D50CF04B7E9E6F7A514B4A1F60");
		caps.setCapability("platformName", "Android");
		caps.setCapability("platformVersion", "9");
		caps.setCapability("deviceName", "Huawei P30");
		caps.setCapability("phoneOnly", "true");
//		caps.setCapability("tabletOnly", "false");
//		caps.setCapability("privateDevicesOnly", "false");
//		caps.setCapability("appiumVersion", "1.17.0");
//		caps.setCapability("noReset", "false");
//		caps.setCapability("cacheId", "1719330c118");
//		caps.setCapability("testobject_session_creation_timeout", "900000");
		caps.setCapability("testobject_app_id", "1");
		caps.setCapability("testobject_suite_name", "Demo suite");
//		caps.setCapability("testobject_test_name", "Demo test");
		caps.setCapability("name", "Sauce labs demo with Android");

		driver = new AndroidDriver<WebElement>(new URL("https://us1.appium.testobject.com/wd/hub"), caps);
	}

	@AfterSuite
	public void uninstallApp() throws InterruptedException {
		driver.quit();
	}

	@Test(enabled = true)
	public void myFirstTest() throws InterruptedException {
		driver.findElementByXPath("(//android.view.ViewGroup[@content-desc='NO THANKS'])[2]").click();
		driver.findElementByXPath("//android.view.ViewGroup[@content-desc='Account']").click();
		driver.findElementByXPath("//android.view.ViewGroup[@content-desc='LOGIN']").click();
		driver.findElementByXPath("(//android.widget.EditText)[1]").sendKeys("cpxapitester@gmail.com");
		driver.findElementByXPath("(//android.widget.EditText)[2]").sendKeys("Cineplex123");
		driver.findElementByXPath("//android.view.ViewGroup[@content-desc='LOGIN']").click();
	}
}
