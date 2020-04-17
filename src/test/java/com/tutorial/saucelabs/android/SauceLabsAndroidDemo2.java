package com.tutorial.saucelabs.android;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.UnexpectedException;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;

public class SauceLabsAndroidDemo2 {
	AndroidDriver androidDriver;
	public String username = "Weipeng";
	public String accesskey = "a9fe7cd4-fd12-4c99-aeee-826a7e511008";
	public String seleniumURI = "@ondemand.saucelabs.com:443";
	
	@BeforeSuite
	public void beforeSuite() throws MalformedURLException, UnexpectedException {
		androidDriver = createDriver("Android", "Samsung Galaxy Tab S3 GoogleAPI Emulator", "8.1", "1.9.1", "portrait", "testMethodName");
	}

	protected AndroidDriver createDriver(String platformName, String deviceName, String platformVersion, String appiumVersion,
			String deviceOrientation, String methodName) throws MalformedURLException, UnexpectedException {
		MutableCapabilities capabilities = new MutableCapabilities();
		capabilities.setCapability("platformName", platformName);
		capabilities.setCapability("platformVersion", platformVersion);
		capabilities.setCapability("deviceName", deviceName);
		capabilities.setCapability("browserName", "");
		capabilities.setCapability("deviceOrientation", deviceOrientation);
		capabilities.setCapability("appiumVersion", appiumVersion);
		capabilities.setCapability("name", methodName);
		capabilities.setCapability("testobject_api_key", "7B0F153A1C564E13934E7C5908228326");
		capabilities.setCapability("testobject_app_id", "1");

		// Launch remote browser and set it as the current thread
		return new AndroidDriver(new URL("https://" + username + ":" + accesskey + seleniumURI + "/wd/hub"), capabilities);
	}
	
	@AfterSuite
	public void afterSuite() throws InterruptedException {
		androidDriver.quit();
	}

	@Test(enabled = true)
	public void androidLogin() throws InterruptedException {
		androidDriver.findElementByXPath("(//android.view.ViewGroup[@content-desc='NO THANKS'])[2]").click();
		androidDriver.findElementByXPath("//android.view.ViewGroup[@content-desc='Account']").click();
		androidDriver.findElementByXPath("//android.view.ViewGroup[@content-desc='LOGIN']").click();
		androidDriver.findElementByXPath("(//android.widget.EditText)[1]").sendKeys("cpxapitester@gmail.com");
		androidDriver.findElementByXPath("(//android.widget.EditText)[2]").sendKeys("Cineplex123");
		androidDriver.findElementByXPath("//android.view.ViewGroup[@content-desc='LOGIN']").click();
	}
}
