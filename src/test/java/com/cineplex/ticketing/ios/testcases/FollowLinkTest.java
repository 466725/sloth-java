package com.cineplex.ticketing.ios.testcases;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.cineplex.ticketing.ios.screens.GuineaPigPage;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;

public class FollowLinkTest extends TestBase {
	/**
	 * Runs a simple test verifying link can be followed.
	 *
	 * @throws InvalidElementStateException
	 */
	@Test(dataProvider = "hardCodedBrowsers")
	public void verifyLinkTest(String platformName, String deviceName, String platformVersion, String appiumVersion,
			String deviceOrientation, Method method)
			throws MalformedURLException, InvalidElementStateException, UnexpectedException {

		// create webdriver session
		this.createDriver(platformName, deviceName, platformVersion, appiumVersion, deviceOrientation,
				method.getName());
		WebDriver driver = this.getiosDriver();

		GuineaPigPage page = new GuineaPigPage(driver);

		page.followLink();

		Assert.assertFalse(page.isOnPage());
	}
}
