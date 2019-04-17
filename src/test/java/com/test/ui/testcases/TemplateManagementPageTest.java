package com.test.ui.testcases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.templates.GuiTestCase;
import com.relevantcodes.extentreports.LogStatus;
import com.test.ui.webpages.HomePage;
import com.test.ui.webpages.LoginPage;
import com.test.ui.webpages.TemplateManagementPage;

/**
 * Tests related to TemplateManagementPage
 * 
 * @author Weipeng Zheng
 *
 */
public class TemplateManagementPageTest extends GuiTestCase {
	protected final static Logger logger = LogManager.getLogger(TemplateManagementPageTest.class.getName());
	protected LoginPage loginPage = null;
	protected HomePage homePage = null;
	protected TemplateManagementPage templateManagementPage = null;

	/**
	 * Test of navigating to HospitalityPage 
	 */
	@Test(priority = 1)
	public void gotoHospitalityPage() {
		test = extent.startTest("Navigate to HospitalityPage");
		test.log(LogStatus.INFO, "Navigate to HospitalityPage");

		templateManagementPage = new TemplateManagementPage(driver, GuiTestCase.URL, GuiTestCase.userName, GuiTestCase.password);

		Assert.assertTrue(templateManagementPage.navigateTo());
	}
}
