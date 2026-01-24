package testcases;

import com.relevantcodes.extentreports.LogStatus;
import config.PropertiesFileReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import utilities.ScreenShotHandler;
import utilities.SeleniumWrapper;
import webpages.BaseWebPage;

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
    protected static BaseWebPage basePage;
    public static WebDriver driver = null;

    /**
     * Prepare per BeforeClass annotation.
     *
     */
    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        logger.info("-----------------------Beginning of class----------------------");
        driver = BaseWebPage.getDriver(PropertiesFileReader.getBrowser());
        if (driver == null) {
            logger.fatal("WebDriver initialization failed (driver is null). Check browser parameter and driver setup.");
            return;
        }
        basePage = new BaseWebPage(driver);
    }

    /**
     * Cleanup per AfterMethod annotation.
     */
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        logger.info("***** Class: " + result.getTestClass().getName() + " *****");
        logger.info("***** Method: " + result.getName() + "(...) *****");

        String screenShotPath = null;
        if (driver != null) {
            screenShotPath = ScreenShotHandler.captureScreenShot(driver, result.getName());
        } else {
            logger.warn("Driver is null in @AfterMethod; skipping screenshot and implicit wait.");
        }

        logResultToExtent(result, screenShotPath);

        if (driver != null) {
            SeleniumWrapper.implicitWait(driver);
        }
        driver.quit();
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

        if (screenShotPath != null) {
            test.log(status, test.addScreenCapture(screenShotPath));
        } else {
            logger.warn("No screenshot path available to attach to report.");
        }
    }

    private String getStackTraceAsString(Throwable exception) {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}