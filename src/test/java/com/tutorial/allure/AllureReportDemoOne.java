package com.tutorial.allure;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.relevantcodes.extentreports.LogStatus;

// import io.qameta.allure.Attachment;
// import io.qameta.allure.Description;
// import io.qameta.allure.Feature;
// import io.qameta.allure.Issue;
// import io.qameta.allure.Severity;
// import io.qameta.allure.SeverityLevel;
// import io.qameta.allure.Step;

public class AllureReportDemoOne extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(AllureReportDemoOne.class.getName());

	@Test(priority = 1)
	// @Issue("ISSUE-0000")
	// @Feature("Some feature")
	// @Severity(SeverityLevel.CRITICAL)
	public void simpleTest() {
		test = extent.startTest("Allure Demo: Simple test example");
		test.log(LogStatus.INFO, "Allure Demo: Simple test example");

		logger.info(firstStep());
		logger.info(secondStep());
		logger.info(thirdStep());
		logger.info(fourthStep());
		logger.info(fifthStep());

		Assert.assertTrue(true);
	}

	// @Step("01 step with Allure Test Report")
	// @Attachment
	public static String firstStep() {
		logger.info("01 step with Allure Test Report");
		return "Yeah, 1 is 1!";
	}

	// @Step("02 step with Allure Test Report")
	// @Attachment
	public static String secondStep() {
		logger.info("02 step with Allure Test Report");
		return "Yeah, 2 is 2!";
	}

	// @Step("03 step with Allure Test Report")
	// @Attachment
	public static String thirdStep() {
		logger.info("03 step with Allure Test Report");
		return "Yeah, 3 is 3!";
	}

	// @Step("04 step with Allure Test Report")
	// @Attachment
	public static String fourthStep() {
		logger.info("04 step with Allure Test Report");
		return "Yeah, 4 is 4!";
	}

	// @Step("05 step with Allure Test Report")
	// @Attachment
	public static String fifthStep() {
		logger.info("05 step with Allure Test Report");
		return "Yeah, 5 is 5!";
	}
}
