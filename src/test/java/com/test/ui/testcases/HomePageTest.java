package com.test.ui.testcases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.templates.GuiTestCase;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Tests related to HomePage
 * 
 * @author Weipeng Zheng
 *
 */
public class HomePageTest extends GuiTestCase {
	protected final static Logger logger = LogManager.getLogger(HomePageTest.class.getName());
	protected LoginPage loginPage = null;
	protected HomePage homePage = null;

	/**
	 * Test of navigating to HomePage
	 */
	@Test(priority = 1)
	public void gotoHomePage() {
		test = extent.startTest("Navigate to HomePage");
		test.log(LogStatus.INFO, "Navigate to HomePage");

		homePage = new HomePage(driver, GuiTestCase.URL, GuiTestCase.userName, GuiTestCase.password);

		Assert.assertTrue(homePage.navigateTo());
	}
}
