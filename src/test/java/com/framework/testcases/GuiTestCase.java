package com.framework.testcases;

import com.utilities.BrowserDriverProvider;
import com.utilities.ScreenShotProvider;
import com.relevantcodes.extentreports.LogStatus;
import com.utilities.SeleniumWrapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Base class of all GUI test cases related objects
 *
 * @author Weipeng Zheng
 *
 */
public class GuiTestCase extends TestCase {
    protected final static Logger logger = LogManager.getLogger(GuiTestCase.class.getName());

    protected static WebDriver driver;
    public static String URL = "";
    public static String userName = "";
    public static String password = "";

    /**
     * Prepare per BeforeClass annotation.
     *
     */
    @Parameters({"browser", "URL", "userName", "password"})
    @BeforeClass(alwaysRun = true)
    public void beforeClass(String browser, String url, String userName, String password) {
        logger.info("-----------------------Beginning of class----------------------");
        logger.info("Browser parameterized as: " + browser);
        logger.info("URL parameterized as: " + url);
        logger.info("userName parameterized as: " + userName);
        logger.info("password parameterized as: " + password);
        driver = BrowserDriverProvider.createDriver(browser);
        GuiTestCase.URL = url;
        GuiTestCase.userName = userName;
        GuiTestCase.password = password;
    }

    /**
     * Cleanup per AfterMethod annotation.
     */
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        logger.info("***** Class: " + result.getTestClass().getName() + " *****");
        logger.info("***** Method: " + result.getName() + "(...) *****");

        String screenShotPath = ScreenShotProvider.captureScreenShot(driver, result.getName());
        logResultToExtent(result, screenShotPath);

        SeleniumWrapper.implicitWait(driver);
        logger.info("-----------------------Ending of method------------------------");
    }

    private void logResultToExtent(ITestResult result, String screenShotPath) {
        String className = result.getTestClass().getName();
        String methodName = result.getMethod().getMethodName();
        Throwable exception = result.getThrowable();
        String logDetails = String.format("%s:  %s", className, methodName);

        LogStatus status = switch (result.getStatus()) {
            case ITestResult.SUCCESS -> LogStatus.PASS;
            case ITestResult.FAILURE -> LogStatus.FAIL;
            case ITestResult.SKIP -> LogStatus.SKIP;
            default -> LogStatus.FATAL;
        };

        test.log(status, logDetails);
        if (status != LogStatus.PASS && exception != null) {
            test.log(status, getStackTraceAsString(exception));
            logger.error("Exception is: ", exception);
        }
        test.log(status, test.addScreenCapture(screenShotPath));
    }

    private String getStackTraceAsString(Throwable exception) {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}