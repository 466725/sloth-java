package com.concierge.testcases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.concierge.webpages.DeliveriesPage;
import com.concierge.webpages.HomePage;
import com.concierge.webpages.LoginPage;
import com.framework.templates.GuiTestCase;

public class DeliveriesPageTest extends GuiTestCase {
	protected final static Logger logger = LogManager.getLogger(HomePageTest.class.getName());
	protected LoginPage loginPage = null;
	protected HomePage homePage = null;
	protected DeliveriesPage deliveriesPage = null;

	/**
	 * Test of navigating to DeliveriesPage
	 */
	@Test(priority = 1)
	public void gotoDeliveriesPage() {
		test = extent.startTest("Navigate to DeliveriesPage");

		homePage = new HomePage(driver, GuiTestCase.URL, GuiTestCase.userName, GuiTestCase.password);
		Assert.assertTrue(homePage.navigateTo());

		deliveriesPage = homePage.gotoDeliveriesPage();
		Assert.assertNotNull(deliveriesPage);
	}
}
