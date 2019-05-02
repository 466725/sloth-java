package com.test.ui.testcases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.templates.GuiTestCase;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Tests related to LoginPage
 * 
 * @author Weipeng Zheng
 *
 */
public class LoginPageTest extends GuiTestCase {
	protected final static Logger logger = LogManager.getLogger(LoginPageTest.class.getName());
	protected LoginPage loginPage = null;

	/**
	 * Test of navigating to LoginPage 
	 */
	@Test(priority = 1)
	public void gotoLoginPage() {
		test = extent.startTest("Navigate to LoginPage");
		test.log(LogStatus.INFO, "Navigate to LoginPage");
		
		loginPage = new LoginPage(driver, GuiTestCase.URL, GuiTestCase.userName, GuiTestCase.password);
		
		Assert.assertTrue(loginPage.navigateTo());
	}
}
