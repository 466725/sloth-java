package com.avanti.ui.workflows;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.avanti.ui.webpages.ShopFloorPage;
import com.framework.templates.GuiTestCase;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Test objective: Verify that Employee, Operation and Time could be correctly logged on End of Shift PDF report accordingly (Pause, Unfinished and Completed)
 * Test steps:
 * Step 01: Navigate to http://184.106.20.39:8093/servoy-webclient/ss/s/avanti/a/25CA7817-FA36-447A-BD34-E63A3BBAD6C7
 * Step 02: Input "Andy Zheng" to the Username text field
 * Step 03: Input "avanti" to the Password text field
 * Step 04: Click the Login button
 * Step 05: Wait for Slingshot home page to be launched
 * Step 06: On the modules tree view, click Sales Orders
 * Step 07: Wait for Order - Table View to be launched
 * Step 08: Click the Copy Estimate button
 * Step 09: Wait for the Copy Estimate to Sales Order to be loaded
 * Step 10: Input "ST140329" to Estimate text field
 * Step 11: Wait for Estimate Lines to be loaded
 * Step 12: Click the Copy button
 * Step 13: Wait for the Update Cost Pricing popup to be launched
 * Step 14: Click the No button on the popup
 * Step 15: Wait for the Sales Order to be displayed
 * Step 16: Change it to Edit mode, by clicking the Edit Icon
 * Step 17: Release the Sales Order with drop down Actions
 * Step 18: Wait for the Workflow Actions popup window to be launched
 * Step 19: Click the OK button on the Workflow Actions popup window
 * Step 20: Wait for the New Jobs Created popup window to be launched
 * Step 21: Click the Close button on the popup window
 * Step 22: Navigate to Production > Shop Floor
 * Step 23: Input "Andy" to Employee Code text field
 * Step 24: Don抰 input anything to Password text field
 * Step 25: Click Login button
 * Step 26: On the Start Shift Confirmation popup window, click Yes button
 * Step 27: Wait for Shop Floor to be loaded
 * Step 28: Specify "***-***" to Job Number text field
 * Step 29: Specify "***-***" to Section text field
 * Step 30: Add Other Operations with the drop down, which is different with "***-***" �
 * Step 31: Click the Start Operation button
 * Step 32: Wait several minutes, or with any other parameterized time
 * Step 33: Click the Pause button
 * Step 34: Specify "***-***" to Job Number text field
 * Step 35: Specify "***-***" to Section text field
 * Step 36: Click Shortcut button, which is different with "***-***"
 * Step 37: Click the Start Operation button
 * Step 38: Wait several minutes, or with any other parameterized time
 * Step 39: Click the Unfinished button
 * Step 40: Wait for the Message popup window to be displayed
 * Step 41: Check the Dismiss checkbox and then click the OK button on the Message popup window
 * Step 42: Specify "***-***" to Job Number text field
 * Step 43: Specify "***-***" to Section text field
 * Step 44: Specify "***-***" to Operation text field � Disable the QA check in ANY operations SL-14537
 * Step 45: Click the Start Operation button
 * Step 46: On the Predecessor Operation not Completed popup window, click Yes button
 * Step 47: Wait several minutes, or with any other parameterized time
 * Step 48: Click the Complete Operation: button
 * Step 49: Click the End Shift
 * Step 50: Wait for the End Shift popup window to be launched
 * Step 51: Click the Yes button
 * Step 52: Wait for the Run Report popup window to be launched
 * Step 53: Select PDF as the Output Format
 * Step 54: Specify a unique name as the Report Title
 * Step 55: Click the Run Report button
 * Step 56: Wait for the Open End Shift Report popup window to be launched
 * Step 57: Enable Save File radio button
 * Step 58: Click the OK button
 * Step 59: Wait for the PDF report to be created
 * Step 60: On the PDF report, Verify Employee Code, Full Name, Date
 * Step 61: On the PDF report, Verify Job Number, Section, Operation,
 * Step 62: On the PDF report, Verify Start Time End Time
 * Step 63: On the PDF report, Verify Operation Duration
 * 
 * @author Weipeng Zheng
 *         Jira#:
 */
public class ShopFloor extends GuiTestCase
{
	
	protected final static Logger logger = LogManager.getLogger(ShopFloor.class.getName());
	protected ShopFloorPage shopFloorPage;
	
	@Test(priority = 1)
	public void loginWithEmbedLoginPage()
	{
		test = extent.startTest(" Shop Floor: Login to Shop Floor on embeded login page ");
		test.log(LogStatus.INFO, "Shop Floor: Login to Shop Floor on embeded login page ");
		Assert.assertNotNull(shopFloorPage);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
	}
}
