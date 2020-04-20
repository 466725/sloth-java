package com.tutorial.saucelabs.ios;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.appium.java_client.ios.IOSDriver;
import junit.framework.Assert;

public class SauceLabsIOSDemo {

	private IOSDriver<WebElement> driver;

	@BeforeSuite
	public void setupAppium() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
        // Set my TestObject API Key
 
        capabilities.setCapability("testobject_api_key", "39E6A80366FE4DE381102AB9A852B32B");
         
        // Dynamic device allocation of an iPhone 7, running iOS 10.3 device
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "10.3.2");
        capabilities.setCapability("deviceName", "iPhone 7");
  
        // Set allocation from private device pool only
//        capabilities.setCapability("privateDevicesOnly", "false");
         
        // Set Application under test
        capabilities.setCapability("testobject_app_id", "1");
//        capabilities.setCapability("name", "My Test 1!");
 
        // Set Appium version
//        capabilities.setCapability("appiumVersion", "1.6.4");
             
        // Set Appium end point
        driver = new IOSDriver<WebElement>(new URL("https://us1.appium.testobject.com/wd/hub"), capabilities);
	}

	@AfterSuite
	public void uninstallApp() throws InterruptedException {
		driver.quit();
	}

	@Test(enabled = true)
	public void myFirstTest() throws InterruptedException {
		Assert.assertTrue(true);
//		driver.findElementByXPath("(//android.view.ViewGroup[@content-desc='NO THANKS'])[2]").click();
//		driver.findElementByXPath("//android.view.ViewGroup[@content-desc='Account']").click();
//		driver.findElementByXPath("//android.view.ViewGroup[@content-desc='LOGIN']").click();
//		driver.findElementByXPath("(//android.widget.EditText)[1]").sendKeys("cpxapitester@gmail.com");
//		driver.findElementByXPath("(//android.widget.EditText)[2]").sendKeys("Cineplex123");
//		driver.findElementByXPath("//android.view.ViewGroup[@content-desc='LOGIN']").click();
	}
}
