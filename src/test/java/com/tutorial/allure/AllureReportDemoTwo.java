package com.tutorial.allure;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.relevantcodes.extentreports.LogStatus;

import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class AllureReportDemoTwo extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(AllureReportDemoTwo.class.getName());

	@Test(priority = 1)
    @Feature("Some feature")
    @Severity(SeverityLevel.CRITICAL)
	@Link(name = "VOL-0000", url = "http://volantedocs.com/testlink/linkto.php?tprojectPrefix=VOL&item=testcase&id=VOL-0000")
	public void simpleTest() {
		test = extent.startTest("Allure Demo: Simple test example");
		test.log(LogStatus.INFO, "Allure Demo: Simple test example");
		
		AllureReportDemoOne.firstStep();
		AllureReportDemoOne.secondStep();
		AllureReportDemoOne.thirdStep();
		AllureReportDemoOne.fourthStep();
		AllureReportDemoOne.fifthStep();
		
		Assert.assertTrue(true);
	}
}
