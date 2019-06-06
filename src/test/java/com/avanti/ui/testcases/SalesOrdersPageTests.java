package com.avanti.ui.testcases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.avanti.ui.webpages.HomePage;
import com.avanti.ui.webpages.LoginPage;
import com.avanti.ui.webpages.SalesOrdersPage;
import com.framework.templates.GuiTestCase;
import com.relevantcodes.extentreports.LogStatus;

public class SalesOrdersPageTests extends GuiTestCase
{
	
	protected final static Logger logger = LogManager.getLogger(SalesOrdersPageTests.class.getName());
	protected LoginPage loginPage;
	protected HomePage homePage;
	protected SalesOrdersPage salesOrdersPage;
	
	@Test(priority = 1)
	public void launchSalesOrdersPage()
	{
		test = extent.startTest(" Sales Orders: Launch Sales Orders page ");
		test.log(LogStatus.INFO, "Sales Orders: Launch Sales Orders page ");
		salesOrdersPage = new SalesOrdersPage(driver);
		Assert.assertNotNull(salesOrdersPage);
	}
}
