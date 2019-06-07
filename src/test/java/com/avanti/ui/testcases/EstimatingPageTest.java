package com.avanti.ui.testcases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.avanti.ui.webpages.EstimatingPage;
import com.framework.templates.GuiTestCase;
import com.relevantcodes.extentreports.LogStatus;

public class EstimatingPageTest extends GuiTestCase
{
	protected final static Logger logger = LogManager.getLogger(EstimatingPageTest.class.getName());
	protected EstimatingPage estimatingPage;
	
	@Test(priority = 1)
	public void launchEstimatingPage()
	{
		test = extent.startTest(" Estimating: Launch Estimating page ");
		test.log(LogStatus.INFO, "Estimating: Launch Estimating page ");
		estimatingPage = new EstimatingPage(driver);
		Assert.assertTrue(estimatingPage.isTableViewDisplayed());
	}
	
	@Test(priority = 4)
	public void verifyQuickSearch()
	{
		test = extent.startTest(" Estimating: Verify quick search with estimate ID ");
		test.log(LogStatus.INFO, "Estimating: Verify quick search with estimate ID ");
		Assert.assertTrue(estimatingPage.quickSearch("ST132113"));
	}
}
