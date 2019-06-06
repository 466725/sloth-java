package com.avanti.ui.testcases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.avanti.ui.webpages.HomePage;
import com.avanti.ui.webpages.LoginPage;
import com.framework.templates.GuiTestCase;
import com.relevantcodes.extentreports.LogStatus;

public class HomePageTests extends GuiTestCase
{
	
	protected final static Logger logger = LogManager.getLogger(HomePageTests.class.getName());
	protected LoginPage loginPage;
	protected HomePage homePage;
	
	@Test(priority = 1)
	public void launchHomePage()
	{
		test = extent.startTest(" Home: Launch Home page ");
		test.log(LogStatus.INFO, "Home: Launch Home page ");
		homePage = new HomePage(driver);
		Assert.assertNotNull(homePage);
	}
	
	@Test(priority = 2)
	public void verifyUsernameDisplayed()
	{
		test = extent.startTest(" Home: Verify Username displayed ");
		test.log(LogStatus.INFO, "Home: Verify Username displayed ");
		Assert.assertTrue(homePage.isUsernameDisplayed());
	}
	
	@Test(priority = 3)
	public void verifyLaunchEstimating()
	{
		test = extent.startTest(" Home: Verify launch Estimating ");
		test.log(LogStatus.INFO, "Home: Verify launch Estimating ");
		Assert.assertTrue(homePage.isEstimatingLaunched());
	}
}
