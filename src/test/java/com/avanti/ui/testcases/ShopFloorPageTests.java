package com.avanti.ui.testcases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.avanti.ui.webpages.HomePage;
import com.avanti.ui.webpages.LoginPage;
import com.avanti.ui.webpages.ShopFloorPage;
import com.framework.templates.GuiTestCase;
import com.relevantcodes.extentreports.LogStatus;
import config.Constants;

public class ShopFloorPageTests extends GuiTestCase
{
	
	protected final static Logger logger = LogManager.getLogger(ShopFloorPageTests.class.getName());
	protected LoginPage loginPage;
	protected HomePage homePage;
	protected ShopFloorPage shopFloorPage;
	
	@Test(priority = 1)
	public void verifyShopFloorPage()
	{
		test = extent.startTest(" Shop Floor: Verify that Shop Floor page object created ");
		test.log(LogStatus.INFO, "Shop Floor: Verify that Shop Floor page object created ");
		shopFloorPage = new ShopFloorPage(driver);
		Assert.assertNotNull(shopFloorPage);
	}
	
	@Test(priority = 2)
	public void gotoShopFloorEmbedLoginPage()
	{
		test = extent.startTest(" Shop Floor: Navigate to Shop Floor embedded login page ");
		test.log(LogStatus.INFO, "Shop Floor: Navigate to Shop Floor embedded login page ");
		Assert.assertTrue(shopFloorPage.gotoSlingshotModules(Constants.SlingshotMudules.PRODUCTION));
		Assert.assertTrue(shopFloorPage.isShopFloorLaunched());
	}
	
	@Test(priority = 3)
	public void loginWithEmbedLoginPage()
	{
		test = extent.startTest(" Shop Floor: Login to Shop Floor on embeded login page ");
		test.log(LogStatus.INFO, "Shop Floor: Login to Shop Floor on embeded login page ");
		Assert.assertTrue(ShopFloorPage.loginShopFloor(Constants.shopFloorEmployeeCode, Constants.shopFloorPassword));
	}
	
	@Test(priority = 5)
	public void verifyShopFloorPageDisplayedIndependently()
	{
		test = extent.startTest(" Shop Floor: Verify Shop Floor page displayed independent of Slingshot ");
		test.log(LogStatus.INFO, "Shop Floor: Verify Shop Floor page displayed independent of Slingshot ");
		Assert.assertTrue(shopFloorPage.isShopFloorPageDisplayedIndependently());
	}
}
