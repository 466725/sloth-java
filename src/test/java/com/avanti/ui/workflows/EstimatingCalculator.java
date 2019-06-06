package com.avanti.ui.workflows;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.avanti.ui.webpages.EstimatingPage;
import com.framework.templates.GuiTestCase;
import com.relevantcodes.extentreports.LogStatus;

import config.Constants;

/**
 * Test objective: Test steps: Step 01:
 * 
 * @author Weipeng Zheng Jira#:
 */
public class EstimatingCalculator extends GuiTestCase {
	protected final static Logger logger = LogManager.getLogger(EstimatingCalculator.class.getName());
	protected EstimatingPage estimatingPage;

	@Test(priority = 1)
	public void launchEstimatingPage() {
		test = extent.startTest(" Estimating: Launch Estimating page ");
		test.log(LogStatus.INFO, "Estimating: Launch Estimating page ");
		estimatingPage = new EstimatingPage(driver);
		Assert.assertTrue(estimatingPage.isTableViewDisplayed());
	}

	@Test(priority = 2)
	public void verifyQuickSearch() {
		test = extent.startTest(" Estimating: Verify quick search with estimate ID ");
		test.log(LogStatus.INFO, "Estimating: Verify quick search with estimate ID ");
		Assert.assertTrue(estimatingPage.quickSearch("ST132113"));
	}

	@Test(priority = 3)
	public void verifyEstimateDetails() {
		test = extent.startTest(" Estimating: Verify estimate details ");
		test.log(LogStatus.INFO, "Estimating: Verify estimate details ");
		Assert.assertTrue(estimatingPage.showEstimateDetails("ST132113"));
	}

	@Test(priority = 4)
	public void duplicateEstimate() {
		test = extent.startTest(" Estimating: Verify estimate duplicating in detail view ");
		test.log(LogStatus.INFO, "Estimating: Verify estimate duplicating in detail view ");
		Assert.assertTrue(EstimatingPage.handleConfirmPopup(driver, Constants.AlertMethods.OK));
		Assert.assertTrue(EstimatingPage.handleUpdateCostPopup(driver, Constants.AlertMethods.YES));
	}

	@Test(priority = 5)
	public void gotoLineItemsTab() {
		test = extent.startTest(" Estimating: Navigate to Line Items Tab ");
		test.log(LogStatus.INFO, "Estimating: Navigate to Line Items Tab ");
		Assert.assertTrue(estimatingPage.gotoLineItemsTab());
	}

	@Test(priority = 6)
	public void launchLineItemCalculator() {
		test = extent.startTest(" Estimating: Launch Line Items Calculator ");
		test.log(LogStatus.INFO, "Estimating: Launch Line Items Calculator ");
		Assert.assertTrue(estimatingPage.launchLineItemsCalculator(true));
	}

	@Test(priority = 7)
	public void overRideLaborAdjustedPrice() {
		test = extent.startTest(" Estimating: Override Line Items Calculator Labor Adjusted Price ");
		test.log(LogStatus.INFO, "Estimating: Override Line Items Calculator Labor Adjusted Price ");
		Assert.assertTrue(estimatingPage.overRideLaborAdjustedPrice());
	}

	@Test(priority = 11)
	public void closeLineItemCalculator() {
		test = extent.startTest(" Estimating: Close Line Items Calculator ");
		test.log(LogStatus.INFO, "Estimating: Close Line Items Calculator ");
		Assert.assertTrue(estimatingPage.closeLineItemsCalculator(true));
	}

	@Test(priority = 12)
	public void saveDuplicatedEstimate() {
		test = extent.startTest(" Estimating: Save the duplicated estimate ");
		test.log(LogStatus.INFO, "Estimating: Save the duplicated estimate ");
		Assert.assertTrue(estimatingPage.saveDuplicatedEstimate());
	}

	/*
	 * @Test(priority = 13) public void gotoSectionsTab() { test =
	 * extent.startTest(" Estimating: Navigate to Sections Tab ");
	 * test.log(LogStatus.INFO, "Estimating: Navigate to Sections Tab ");
	 * Assert.assertTrue(estimatingPage.gotoSectionsTab()); }
	 * 
	 * @Test(priority = 14) public void openSectionDetailsPopup() { test =
	 * extent.startTest(" Estimating: Navigate to Section Details ");
	 * test.log(LogStatus.INFO, "Estimating: Navigate to Section Details ");
	 * Assert.assertTrue(estimatingPage.gotoSectionDetails()); }
	 * 
	 * @Test(priority = 15) public void gotoPressTab() { test =
	 * extent.startTest(" Estimating: Navigate to Press Tab on Section Details ");
	 * test.log(LogStatus.INFO,
	 * "Estimating: Navigate to Press Tab on Section Details ");
	 * Assert.assertTrue(estimatingPage.gotoPressTab(false)); }
	 * 
	 * @Test(priority = 16) public void gotoPressTabWithActions() { test =
	 * extent.startTest(" Estimating: Navigate to Press Tab on Section Details ");
	 * test.log(LogStatus.INFO,
	 * "Estimating: Navigate to Press Tab on Section Details ");
	 * Assert.assertTrue(estimatingPage.gotoPressTab(true)); }
	 * 
	 * @Test(priority = 17) public void recalculateSelectedSection() { test =
	 * extent.startTest(" Estimating: Verify Recalculate selected Section ");
	 * test.log(LogStatus.INFO, "Estimating: Verify Recalculate selected Section ");
	 * Assert.assertTrue(estimatingPage.recalculateSelectedSection()); }
	 * 
	 * @Test(priority = 18) public void closeSectionDetailsPopup() { test =
	 * extent.startTest(" Estimating: Close Section Details popup ");
	 * test.log(LogStatus.INFO, "Estimating: Close Section Details popup ");
	 * Assert.assertTrue(estimatingPage.clickSectionDetailsCloseBotton()); }
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
