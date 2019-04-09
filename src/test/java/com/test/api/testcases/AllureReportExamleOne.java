package com.test.api.testcases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.relevantcodes.extentreports.LogStatus;

import io.qameta.allure.Attachment;
//import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;

public class AllureReportExamleOne extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(AllureReportExamleOne.class.getName());

	@Test(priority = 1)
	@Issue("ISSUE-0000")
	@Feature("Some feature")
	@Severity(SeverityLevel.CRITICAL)
	public void simpleTest() {
		test = extent.startTest("Allure Example: Simple test example");
		test.log(LogStatus.INFO, "Allure Example: Simple test example");
		firstStep();
		Assert.assertTrue(true);
	}

	@Step
	@Attachment
	public static String firstStep() {
		logger.info("First step with Allure Test Report");
		return "Yeah, 2 is 2!";
	}
}
