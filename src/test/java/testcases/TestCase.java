package testcases;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import config.Constants;
import config.ExtentReportHandler;

import java.lang.reflect.Method;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

/**
 * Base class of all test cases related objects
 *
 * @author Weipeng Zheng
 *
 */
public class TestCase {
    protected final static Logger logger = LogManager.getLogger(TestCase.class.getName());
    protected final static String LOG_CONFIG_FILE = Constants.CONFIG_FILE_FOLDER + "log4j-config.xml";
    protected final static String REPORT_FILE_NAME = "\\test-output\\ExtentReport\\ExtentReport.html";
    protected static ExtentTest test;
    protected static ExtentReports report;

    private void setupLog4j() {
        DOMConfigurator.configure(LOG_CONFIG_FILE);
        logger.info("Log4j configured from: " + LOG_CONFIG_FILE);
        logger.info(logger.getAllAppenders());
    }

    /**
     * Prepare per BeforeSuite annotation.
     */
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        setupLog4j();
        logger.info("-----------------------Beginning of suite----------------------");
    }

    /**
     * Prepare per BeforeTest annotation.
     */
    @BeforeTest(alwaysRun = true)
    public void beforeTest() {
        logger.info("-----------------------Beginning of test-----------------------");
        String reportPath = System.getProperty("user.dir") + REPORT_FILE_NAME;
        report = new ExtentReports(reportPath);
        ExtentReportHandler.loadConfig(report, logger);
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        String testName = method.getDeclaringClass().getSimpleName() + "." + method.getName();
        test = report.startTest(testName);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        if (report != null && test != null) {
            report.endTest(test);
        }
    }

    /**
     * Cleanup per AfterTest annotation.
     */
    @AfterTest(alwaysRun = true)
    public void afterTest() {
        logger.info("----------------------Ending of test--------------------------");
    }

    /**
     * Cleanup per AfterSuite annotation.
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
