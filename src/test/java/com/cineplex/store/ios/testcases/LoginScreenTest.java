package com.cineplex.store.ios.testcases;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;
import java.util.UUID;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.cineplex.store.ios.screens.LoginScreen;
import com.framework.templates.IOSTestCase;

public class LoginScreenTest extends IOSTestCase {
	protected final static Logger logger = LogManager.getLogger(LoginScreenTest.class.getName());
	
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

		LoginScreen page = new LoginScreen(driver);

		page.followLink();

		Assert.assertFalse(page.isOnPage());
	}
	/**
	 * Runs a simple test verifying if the comment input is functional.
	 * 
	 * @throws InvalidElementStateException
	 */
	@org.testng.annotations.Test(dataProvider = "hardCodedBrowsers")
	public void verifyCommentInputTest(String platformName, String deviceName, String platformVersion,
			String appiumVersion, String deviceOrientation, Method method)
			throws MalformedURLException, InvalidElementStateException, UnexpectedException {

		this.createDriver(platformName, deviceName, platformVersion, appiumVersion, deviceOrientation,
				method.getName());
		WebDriver driver = this.getiosDriver();

		String commentInputText = UUID.randomUUID().toString();

		LoginScreen page = new LoginScreen(driver);

		page.submitComment(commentInputText);

		Assert.assertTrue(page.getSubmittedCommentText().contains(commentInputText));
	}
}
