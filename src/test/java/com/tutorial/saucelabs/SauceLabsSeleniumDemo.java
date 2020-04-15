package com.tutorial.saucelabs;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class SauceLabsSeleniumDemo {
	private static WebDriver androidDriver = null;

	@BeforeSuite
	public void beforeSuite() throws MalformedURLException {
		DesiredCapabilities androidCaps = new DesiredCapabilities();
		androidCaps.setCapability("testobject_api_key", "7B0F153A1C564E13934E7C5908228326");
		androidCaps.setCapability("testobject_app_id", "1");
		androidCaps.setCapability("platformName", "Android");
		androidCaps.setCapability("platformVersion", "10");
		androidDriver = new ChromeDriver();
	}

	@AfterSuite
	public void afterSuite() throws InterruptedException {
		androidDriver.quit();
	}

	@Test(enabled = true)
	public void androidLogin() throws InterruptedException {
		androidDriver.findElement(By.xpath("(//android.view.ViewGroup[@content-desc='NO THANKS'])[2]")).click();
		androidDriver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='Account']")).click();
		androidDriver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='LOGIN']")).click();
		androidDriver.findElement(By.xpath("(//android.widget.EditText)[1]")).sendKeys("cpxapitester@gmail.com");
		androidDriver.findElement(By.xpath("(//android.widget.EditText)[2]")).sendKeys("Cineplex123");
		androidDriver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='LOGIN']")).click();
	}
}
