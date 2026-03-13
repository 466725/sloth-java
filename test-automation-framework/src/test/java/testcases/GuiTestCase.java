package testcases;

import com.relevantcodes.extentreports.LogStatus;
import config.PropertiesFileReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
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
    private static final DateTimeFormatter SCREENSHOT_TS = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");
    public WebDriver driver = null;
    protected BaseWebPage basePage;

    /**
     * Runs before each UI test class.
     */
    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        logger.info("-----------------------Beginning of class----------------------");
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
        super.beforeMethod(method);

        // Avoid reusing an old RemoteWebDriver session across tests. In CI, the Selenium node can reap
        // sessions that go idle (e.g., while other tests run), leaving us with a stale session id.
        try {
            BaseWebPage.quitDriver();
        } catch (Throwable t) {
            logger.warn("Ignoring exception while quitting pre-existing driver in @BeforeMethod.", t);
        }

        driver = BaseWebPage.getDriver(PropertiesFileReader.getBrowser());
        if (driver == null) {
            logger.fatal("WebDriver initialization failed (driver is null).");
            return;
        }
        basePage = new BaseWebPage(driver);
    }

    /**
     * Runs after each UI test method.
     */
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        try {
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
        } finally {
            try {
                BaseWebPage.quitDriver();
            } catch (Throwable t) {
                logger.warn("Ignoring exception during driver.quit() in @AfterMethod (session may already be gone).", t);
            } finally {
                driver = null;
            }
            super.afterMethod(result);
        }
    }

    /**
     * Runs after each TestNG <test> block.
     */
    @AfterTest(alwaysRun = true)
    @Override
    public void afterTest() {
        try {
            if (driver == null || driver instanceof RemoteWebDriver remote && remote.getSessionId() == null) {
                return;
            }
            SeleniumWrapper.implicitWait(driver);
            BaseWebPage.quitDriver();
        } catch (Throwable t) {
            logger.warn("Ignoring exception during driver.quit() in teardown (session may already be gone).", t);
        } finally {
            driver = null;
            super.afterTest();
        }
    }

    private void logResultToExtent(ITestResult result, String screenShotPath) {
        if (test == null) {
            logger.warn("ExtentTest is null in GuiTestCase.logResultToExtent; skipping report logging.");
            return;
        }

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
