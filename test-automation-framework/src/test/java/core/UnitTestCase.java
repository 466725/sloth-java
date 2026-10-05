package core;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import static com.google.common.base.Throwables.getStackTraceAsString;

/**
 * Base class for unit tests that verify framework utilities without browsers, devices, or remote services.
 *
 * @author Weipeng Zheng
 */
public class UnitTestCase extends TestCase {
    protected final static Logger logger = LogManager.getLogger(UnitTestCase.class.getName());

    /**
     * Runs before each test class.
     */
    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        logger.info("-----------------------Beginning of class----------------------");
    }

    /**
     * Runs after each test class.
     */
    @AfterClass(alwaysRun = true)
    public void afterClass() {
        logger.info("----------------------Ending of class--------------------------");
    }

    /**
     * Runs after each test method.
     */
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        try {
            logger.info("***** Class: " + result.getTestClass().getName() + " *****");
            logger.info("***** Method: " + result.getName() + "(...) *****");
            String testName = result.getTestClass().getName() + "." + result.getName();
            if (result.getStatus() == ITestResult.FAILURE) {
                logger.error("Unit test failed: " + testName, result.getThrowable());
            } else if (result.getStatus() == ITestResult.SKIP && result.getThrowable() != null) {
                logger.warn("Unit test skipped or scheduled for retry: " + testName, result.getThrowable());
            }
            logResultToExtent(result);
        } finally {
            super.afterMethod(result);
        }
    }

    private void logResultToExtent(ITestResult result) {
        if (test == null) {
            logger.warn("ExtentTest is null in UnitTestCase.logResultToExtent; skipping report logging.");
            return;
        }

        String logDetails = String.format("%s:  %s", result.getTestClass().getName(), result.getMethod().getMethodName());
        LogStatus status = switch (result.getStatus()) {
            case ITestResult.SUCCESS -> LogStatus.PASS;
            case ITestResult.FAILURE -> LogStatus.FAIL;
            case ITestResult.SKIP -> LogStatus.SKIP;
            default -> LogStatus.FATAL;
        };

        test.log(status, logDetails);
        Throwable exception = result.getThrowable();
        if (status != LogStatus.PASS && exception != null) {
            test.log(status, getStackTraceAsString(exception));
        }
    }
}
