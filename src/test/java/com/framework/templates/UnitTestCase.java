package com.framework.templates;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import com.relevantcodes.extentreports.LogStatus;

public class UnitTestCase extends TestCase {
	protected final static Logger logger = LogManager.getLogger(UnitTestCase.class.getName());

	@Parameters("browser")
	@BeforeClass(alwaysRun = true)
	public void beforeClass(String browser) {
		logger.info("-----------------------Beginning of class----------------------");
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {
		logger.info("-----------------------Beginning of method---------------------");
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) {
		logger.info("***** Method: " + result.getName() + "(...) *****");
		logger.info("***** Class: " + result.getTestClass().getName() + " *****");
		int resultStatus = result.getStatus();
		StringWriter sw = new StringWriter();
		Throwable exception = result.getThrowable();
		String className = result.getTestClass().getName();
		String methodName = result.getMethod().getMethodName();
		switch (resultStatus) {
		case ITestResult.SUCCESS:
			test.log(LogStatus.PASS, String.format("%s:  %s", className, methodName));
			logger.info("***** Result: PASS *****");
			break;
		case ITestResult.FAILURE:
			test.log(LogStatus.FAIL, String.format("%s:  %s", className, methodName));
			exception.printStackTrace(new PrintWriter(sw));
			if (exception != null)
				test.log(LogStatus.FAIL, sw.getBuffer().toString());
			logger.info("***** Result: FAIL *****");
			break;
		case ITestResult.SKIP:
			test.log(LogStatus.SKIP, String.format("%s:  %s", className, methodName));
			exception.printStackTrace(new PrintWriter(sw));
			if (exception != null)
				test.log(LogStatus.SKIP, sw.getBuffer().toString());
			logger.info("***** Result: SKIP *****");
			break;
		default:
			test.log(LogStatus.FATAL, String.format("%s:  %s", className, methodName));
			exception.printStackTrace(new PrintWriter(sw));
			if (exception != null)
				test.log(LogStatus.FATAL, sw.getBuffer().toString());
			logger.info("***** Result: FATAL *****");
			break;
		}
		extent.endTest(test);
		logger.info("-----------------------Ending of method------------------------");
	}

	@AfterClass(alwaysRun = true)
	protected void afterClass() {
		logger.info("----------------------Ending of class-------------------------");
	}
}
