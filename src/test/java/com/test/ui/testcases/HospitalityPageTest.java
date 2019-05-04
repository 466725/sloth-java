package com.test.ui.testcases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.templates.GuiTestCase;
import com.relevantcodes.extentreports.LogStatus;
import com.test.ui.webpages.HomePage;
import com.test.ui.webpages.HospitalityPage;
import com.test.ui.webpages.LoginPage;
import com.test.ui.webpages.StoreViewPage;

/**
 * Tests related to HospitalityPage
 * 
 * @author Weipeng Zheng
 *
 */
public class HospitalityPageTest extends GuiTestCase {
	protected final static Logger logger = LogManager.getLogger(HospitalityPageTest.class.getName());
	protected LoginPage loginPage = null;
	protected HomePage homePage = null;
	protected StoreViewPage storeViewPage = null;
	protected HospitalityPage hospitalityPage = null;

	/**
	 * Test of navigating to HospitalityPage 
	 */
	@Test(priority = 1)
	public void gotoHospitalityPage() {
		test = extent.startTest("Navigate to HospitalityPage");
		test.log(LogStatus.INFO, "Navigate to HospitalityPage");

		hospitalityPage = new HospitalityPage(driver, GuiTestCase.URL, GuiTestCase.userName, GuiTestCase.password);

		Assert.assertTrue(hospitalityPage.navigateTo());
	}
}
