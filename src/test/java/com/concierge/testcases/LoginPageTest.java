package com.concierge.testcases;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.concierge.webpages.LoginPage;
import com.framework.templates.GuiTestCase;

public class LoginPageTest extends GuiTestCase {
	protected final static Logger logger = LogManager.getLogger(LoginPageTest.class.getName());
	protected LoginPage loginPage = null;

	/**
	 * Test of navigating to LoginPage
	 */
	@Test(priority = 1)
	public void gotoLoginPage() {
		test = extent.startTest("Navigate to LoginPage");

		loginPage = new LoginPage(driver, GuiTestCase.URL, GuiTestCase.userName, GuiTestCase.password);

		Assert.assertTrue(loginPage.navigateTo());
	}
}
