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

public class AllureReportExamleTwo extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(AllureReportExamleTwo.class.getName());

	@Test(priority = 1)
    @Feature("Some feature")
    @Severity(SeverityLevel.CRITICAL)
	@Link(name = "VOL-0000", url = "http://volantedocs.com/testlink/linkto.php?tprojectPrefix=VOL&item=testcase&id=VOL-0000")
	public void simpleTest() {
		test = extent.startTest("Allure Example: Simple test example");
		test.log(LogStatus.INFO, "Allure Example: Simple test example");
		AllureReportExamleOne.firstStep();
		Assert.assertTrue(true);
	}
}
