package com.avanti.ui.testcases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.avanti.ui.webpages.LoginPage;
import com.framework.templates.GuiTestCase;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.JavascriptExecutor;

public class LoginPageTests extends GuiTestCase
{
	
	protected final static Logger logger = LogManager.getLogger(LoginPageTests.class.getName());
	protected LoginPage loginPage;
	
	@Test(priority = 1)
	public void launchLoginPage()
	{
		test = extent.startTest(" Login: Launch Login page ");
		test.log(LogStatus.INFO, "Login: Launch Login page ");
		loginPage = new LoginPage(driver);
		Assert.assertNotNull(loginPage);
	}
	
	@Test(priority = 2)
	public void verifyUsernameInput()
	{
		test = extent.startTest(" Login: Verify Username input ");
		test.log(LogStatus.INFO, "Login: Verify Username input ");
		Assert.assertTrue(LoginPage.inputUsername(GuiTestCase.userName));
	}
	
	@Test(priority = 3)
	public void verifyPasswordInput()
	{
		test = extent.startTest(" Login: Verify Password input ");
		test.log(LogStatus.INFO, "Login: Verify Password input ");
		Assert.assertTrue(LoginPage.inputPassword(GuiTestCase.password));
	}
	
	@Test(priority = 5)
	public void clickLoginButton()
	{
		test = extent.startTest(" Login: Click Login button ");
		test.log(LogStatus.INFO, "Login: Click Login button ");
		Assert.assertTrue(LoginPage.clickLoginButton());
	}
	
	// For fun
	@Test(priority = 6)
	public void verifyJavaScriptExecution()
	{
		test = extent.startTest(" Login: Verify Java Script execution ");
		test.log(LogStatus.INFO, "Login: Verify Java Script execution ");
		try
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeAsyncScript("alert('How are you, bro?');");
			js.executeAsyncScript("window.confirm('OK?');");
		}
		catch (Exception e)
		{
			logger.error("Exception is: ", e);
		}
	}
}
