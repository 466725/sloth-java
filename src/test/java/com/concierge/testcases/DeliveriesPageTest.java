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

	/**
	 * Verify sort per column ID
	 */
	@Test(priority = 3)
	public void testSortColumnID() {
		test = extent.startTest("Verify sort per column ID");

		Assert.assertTrue(deliveriesPage.sortByColumnID());
	}

	/**
	 * Verify sort per column Received
	 */
	@Test(priority = 5)
	public void testSortColumnReceived() {
		test = extent.startTest("Verify sort per column Received");

		Assert.assertTrue(deliveriesPage.sortByColumnReceived());
	}

	/**
	 * Verify sort per column Unit
	 */
	@Test(priority = 7)
	public void testSortColumnUnit() {
		test = extent.startTest("Verify sort per column Unit");

		Assert.assertTrue(deliveriesPage.sortByColumnUnit());
	}

	/**
	 * Verify sort per column Location
	 */
	@Test(priority = 9)
	public void testSortColumnLocation() {
		test = extent.startTest("Verify sort per column Location");

		Assert.assertTrue(deliveriesPage.sortByColumnLocation());
	}

	/**
	 * Verify sort per column Type
	 */
	@Test(priority = 11)
	public void testSortColumnType() {
		test = extent.startTest("Verify sort per column Type");

		Assert.assertTrue(deliveriesPage.sortByColumnType());
	}

	/**
	 * Verify sort per column Recipient
	 */
	@Test(priority = 13)
	public void testSortColumnRecipient() {
		test = extent.startTest("Verify sort per column Recipient");

		Assert.assertTrue(deliveriesPage.sortByColumnRecipient());
	}

	/**
	 * Verify sort per column Item
	 */
	@Test(priority = 15)
	public void testSortColumnItem() {
		test = extent.startTest("Verify sort per column Item");

		Assert.assertTrue(deliveriesPage.sortByColumnItem());
	}
}
