package com.test.ui.testcases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.templates.GuiTestCase;
import com.relevantcodes.extentreports.LogStatus;
import com.test.ui.webpages.HomePage;
import com.test.ui.webpages.LoginPage;

public class LoginPageTest extends GuiTestCase {
	protected final static Logger logger = LogManager.getLogger(LoginPageTest.class.getName());
	protected HomePage homePage;
	protected LoginPage loginPage;

	@Test(priority = 1)
	public void launchHomePage() {
		test = extent.startTest("Home: Home Page with Login and Signup Buttons");
		test.log(LogStatus.INFO, "Home: Home Page with Login and Signup Buttons");
		homePage = new HomePage(driver);
		Assert.assertNotNull(homePage);
		Assert.assertTrue(homePage.isLoginButtonDisplayed());
	}

	@Test(priority = 2)
	public void navigateToLoginPage() {
		test = extent.startTest("Login: Navigate to Login page");
		test.log(LogStatus.INFO, "Login: Navigate to Login page");
		Assert.assertNotNull(homePage.gotoLoginPage());
	}

	@Test(priority = 3)
	public void verifyUsernameInput() {
		test = extent.startTest("Login: Verify Username input");
		test.log(LogStatus.INFO, "Login: Verify Username input");
		Assert.assertTrue(LoginPage.inputUsername(GuiTestCase.userName));
	}

	@Test(priority = 4)
	public void verifyPasswordInput() {
		test = extent.startTest("Login: Verify Password input");
		test.log(LogStatus.INFO, "Login: Verify Password input");
		Assert.assertTrue(LoginPage.inputPassword(GuiTestCase.password));
	}

	@Test(priority = 5)
	public void clickLoginButton() {
		test = extent.startTest("Login: Click Login button");
		test.log(LogStatus.INFO, "Login: Click Login button");
		Assert.assertTrue(LoginPage.clickLoginButton());
	}

	@Test(priority = 6)
	public void verifyInvalidEmailMessage() throws Exception {
		test = extent.startTest("Login: Verify invalid email message");
		test.log(LogStatus.INFO, "Login: Verify invalid email message");
		Assert.assertTrue(LoginPage.verifyInvalidEmailMessage());
		throw new Exception();
	}
}
