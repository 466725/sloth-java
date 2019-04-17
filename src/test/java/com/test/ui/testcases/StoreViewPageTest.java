package com.test.ui.testcases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.templates.GuiTestCase;
import com.relevantcodes.extentreports.LogStatus;
import com.test.ui.webpages.HomePage;
import com.test.ui.webpages.LoginPage;
import com.test.ui.webpages.StoreViewPage;

/**
 * Tests related to StoreViewPage
 * 
 * @author Weipeng Zheng
 *
 */
public class StoreViewPageTest extends GuiTestCase {
	protected final static Logger logger = LogManager.getLogger(StoreViewPageTest.class.getName());
	protected LoginPage loginPage = null;
	protected HomePage homePage = null;
	protected StoreViewPage storeViewPage = null;

	/**
	 * Test of navigating to StoreViewPage 
	 */
	@Test(priority = 1)
	public void gotoStoreViewPage() {
		test = extent.startTest("Navigate to StoreViewPage");
		test.log(LogStatus.INFO, "Navigate to StoreViewPage");

		storeViewPage = new StoreViewPage(driver, GuiTestCase.URL, GuiTestCase.userName, GuiTestCase.password);

		Assert.assertTrue(storeViewPage.navigateTo());
	}
}
