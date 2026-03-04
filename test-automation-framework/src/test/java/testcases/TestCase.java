package testcases;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import config.ExtentReportHandler;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

/**
 * Base class for shared test lifecycle and reporting setup.
 *
 * @author Weipeng Zheng
 */
public class TestCase {
    protected final static Logger logger = LogManager.getLogger(TestCase.class.getName());
    protected final static Path REPORT_FILE_PATH = Path.of("test-output", "ExtentReport", "ExtentReport.html");
    protected static ExtentTest test;
    protected static ExtentReports report;

    /**
     * Runs once before the suite starts.
     */
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        logger.info("-----------------------Beginning of suite----------------------");
    }

    /**
     * Runs before each TestNG <test> block.
     */
    @BeforeTest(alwaysRun = true)
    public void beforeTest() {
        logger.info("-----------------------Beginning of test-----------------------");
        Path absoluteReportPath = Path.of(System.getProperty("user.dir")).resolve(REPORT_FILE_PATH);
        try {
            Files.createDirectories(absoluteReportPath.getParent());
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to create Extent report directory: " + absoluteReportPath.getParent(), exception);
        }
        report = new ExtentReports(absoluteReportPath.toString());
        logger.info("extent.report.path | path=" + absoluteReportPath);
        ExtentReportHandler.loadConfig(report, logger);
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        String testName = method.getDeclaringClass().getSimpleName() + "." + method.getName();
        test = report.startTest(testName);
        logger.info("----------------------Beginning of method--------------------------");
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        if (report != null && test != null) {
            report.endTest(test);
        }
        test = null;
        logger.info("-----------------------Ending of method------------------------");
    }

    /**
     * Runs after each TestNG <test> block.
     */
    @AfterTest(alwaysRun = true)
    public void afterTest() {
        logger.info("----------------------Ending of test--------------------------");
    }

    /**
     * Runs once after the suite finishes.
     */
    @AfterSuite(alwaysRun = true)
    protected void afterSuite() {
        if (report != null) {
            report.flush();
            logger.info("***** Extent report ready to use! *****");
        }
        logger.info("----------------------Ending of suite-------------------------");
    }
}
