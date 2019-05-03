package com.framework.templates;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.framework.helpers.BrowserDriverProvider;
import com.framework.helpers.ScreenShotProvider;
import com.relevantcodes.extentreports.LogStatus;
import com.test.ui.utilities.SeleniumWrapper;

/**
 * Base class of all GUI test cases related objects
 * 
 * @author Weipeng Zheng
 *
 */
public class GuiTestCase extends TestCase {
	protected final static Logger logger = LogManager.getLogger(GuiTestCase.class.getName());

	protected static WebDriver driver;
	protected static String URL = "";
	protected static String userName = "";
	protected static String password = "";

	/**
	 * Prepare per BeforeClass annotation.
	 * 
	 * @param Browser  type used by GUI test
	 * @param URL      used to launch a website
	 * @param userName login user name
	 * @param password login password
	 */
	@Parameters({ "browser", "URL", "userName", "password" })
	@BeforeClass(alwaysRun = true)
	public void beforeClass(String browser, String URL, String userName, String password) {
		logger.info("-----------------------Beginning of class----------------------");
		logger.info("Browser parameterized as: " + browser);
		logger.info("URL parameterized as: " + URL);
		logger.info("userName parameterized as: " + userName);
		logger.info("password parameterized as: " + password);
		driver = BrowserDriverProvider.createDriver(browser);
		GuiTestCase.URL = URL;
		GuiTestCase.userName = userName;
		GuiTestCase.password = password;
	}

	/**
	 * Prepare per BeforeMethod annotation.
	 */
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {
		logger.info("-----------------------Beginning of method---------------------");
	}

	/**
	 * Cleanup per AfterMethod annotation.
	 */
	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) {
		logger.info("***** Class: " + result.getTestClass().getName() + " *****");
		logger.info("***** Method: " + result.getName() + "(...) *****");
		int resultStatus = result.getStatus();
		StringWriter sw = new StringWriter();
		Throwable exception = result.getThrowable();
		String className = result.getTestClass().getName();
		String methodName = result.getMethod().getMethodName();
		String screenShotPath = ScreenShotProvider.captureScreenShot(driver, result.getName());
		switch (resultStatus) {
		case ITestResult.SUCCESS:
			test.log(LogStatus.PASS, String.format("%s:  %s", className, methodName));
			test.log(LogStatus.PASS, test.addScreenCapture(screenShotPath));
			break;
		case ITestResult.FAILURE:
			test.log(LogStatus.FAIL, String.format("%s:  %s", className, methodName));
			exception.printStackTrace(new PrintWriter(sw));
			test.log(LogStatus.FAIL, sw.getBuffer().toString());
			test.log(LogStatus.FAIL, test.addScreenCapture(screenShotPath));
			logger.error("Exception is: ", exception);
			break;
		case ITestResult.SKIP:
			test.log(LogStatus.SKIP, String.format("%s:  %s", className, methodName));
			exception.printStackTrace(new PrintWriter(sw));
			test.log(LogStatus.SKIP, sw.getBuffer().toString());
			test.log(LogStatus.SKIP, test.addScreenCapture(screenShotPath));
			logger.error("Exception is: ", exception);
			break;
		default:
			test.log(LogStatus.FATAL, String.format("%s:  %s", className, methodName));
			exception.printStackTrace(new PrintWriter(sw));
			test.log(LogStatus.FATAL, sw.getBuffer().toString());
			test.log(LogStatus.FATAL, test.addScreenCapture(screenShotPath));
			logger.error("Exception is: ", exception);
			break;
		}
		extent.endTest(test);
		SeleniumWrapper.implicitWait(driver);
		logger.info("-----------------------Ending of method------------------------");
	}

	/**
	 * Cleanup per AfterClass annotation.
	 */
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		logger.info("***** " + driver.toString() + " quit()! *****");
		driver.quit();
		logger.info("----------------------Ending of class-------------------------");
	}
}