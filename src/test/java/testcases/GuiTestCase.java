package testcases;

import com.relevantcodes.extentreports.LogStatus;
import config.PropertiesFileReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.ScreenShotHandler;
import utilities.SeleniumWrapper;
import webpages.BaseWebPage;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Base class for UI test lifecycle and screenshot handling.
 *
 * @author Weipeng Zheng
 */
public class GuiTestCase extends TestCase {
    protected final static Logger logger = LogManager.getLogger(GuiTestCase.class.getName());
    protected static BaseWebPage basePage;
    public static WebDriver driver = null;

    private static final DateTimeFormatter SCREENSHOT_TS = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    /**
     * Runs before each UI test class.
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
     * Runs after each UI test class.
     */
    @AfterClass(alwaysRun = true)
    public void afterClass() {
        logger.info("----------------------Ending of class--------------------------");
    }

    /**
     * Runs before each UI test method.
     */
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        driver = BaseWebPage.getDriver(PropertiesFileReader.getBrowser());
        if (driver == null) {
            logger.fatal("WebDriver initialization failed (driver is null).");
            return;
        }
        basePage = new BaseWebPage(driver);
        super.beforeMethod(method);
    }

    /**
     * Runs after each UI test method.
     */
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        logger.info("***** Class: " + result.getTestClass().getName() + " *****");
        logger.info("***** Method: " + result.getName() + "(...) *****");

        String screenShotPath = null;
        if (driver != null) {
            String screenshotName = LocalDateTime.now().format(SCREENSHOT_TS) + "_" + result.getName();
            screenShotPath = ScreenShotHandler.captureScreenShot(driver, screenshotName);
        } else {
            logger.warn("Driver is null in @AfterMethod; skipping screenshot.");
        }
        logResultToExtent(result, screenShotPath);
        super.afterMethod(result);
    }

    /**
     * Runs after each TestNG <test> block.
     */
    @AfterTest(alwaysRun = true)
    public void afterTest() {
        if (driver != null) {
            SeleniumWrapper.implicitWait(driver);
            driver.quit();
            driver = null;
        }
        logger.info("----------------------Ending of test--------------------------");
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
