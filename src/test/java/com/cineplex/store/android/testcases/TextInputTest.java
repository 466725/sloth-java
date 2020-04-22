package com.cineplex.store.android.testcases;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.cineplex.store.android.screens.GuineaPigPage;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;
import java.util.UUID;

public class TextInputTest extends TestBase {
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
		WebDriver driver = this.getAndroidDriver();

		String commentInputText = UUID.randomUUID().toString();

		GuineaPigPage page = new GuineaPigPage(driver);

		page.submitComment(commentInputText);

		Assert.assertTrue(page.getSubmittedCommentText().contains(commentInputText));
	}
}