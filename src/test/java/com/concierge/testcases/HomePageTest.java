package com.concierge.testcases;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.concierge.webpages.HomePage;
import com.concierge.webpages.LoginPage;
import com.framework.templates.GuiTestCase;

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

		homePage = new HomePage(driver, GuiTestCase.URL, GuiTestCase.userName, GuiTestCase.password);

		Assert.assertTrue(homePage.navigateTo());
	}
}
