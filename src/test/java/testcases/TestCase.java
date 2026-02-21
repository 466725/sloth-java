package testcases;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import config.Constants;
import config.ExtentReportHandler;

import java.lang.reflect.Method;
import java.net.URL;

import org.apache.log4j.BasicConfigurator;
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
 * Base class for shared test lifecycle and reporting setup.
 *
 * @author Weipeng Zheng
 */
public class TestCase {
    protected final static Logger logger = LogManager.getLogger(TestCase.class.getName());
    protected final static String LOG_CONFIG_FILE = Constants.CONFIG_FILE_FOLDER + Constants.LOG4J_CONFIG_RESOURCE;
    protected final static String REPORT_FILE_NAME = "\\test-output\\ExtentReport\\ExtentReport.html";
    protected static ExtentTest test;
    protected static ExtentReports report;

    private void setupLog4j() {
        // Avoid duplicate appenders: configure Log4j only once.
        boolean alreadyConfigured = LogManager.getRootLogger().getAllAppenders().hasMoreElements();
        if (alreadyConfigured) {
            logger.debug("Log4j already configured; skipping reconfiguration.");
            return;
        }

        // Load Log4j config from classpath (works in IntelliJ, Maven Surefire, CI, and packaged runs)
        URL configUrl = Thread.currentThread().getContextClassLoader().getResource(LOG_CONFIG_FILE);

        if (configUrl != null) {
            DOMConfigurator.configure(configUrl);
            logger.info("Log4j configured from classpath resource: " + LOG_CONFIG_FILE);
        } else {
            // Fallback: never run with "no appenders"
            BasicConfigurator.configure();
            logger.warn("Log4j config resource not found on classpath: " + LOG_CONFIG_FILE
                    + " (falling back to BasicConfigurator).");
        }
    }

    /**
     * Runs once before the suite starts.
     */
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        setupLog4j();
        logger.info("-----------------------Beginning of suite----------------------");
    }

    /**
     * Runs before each TestNG <test> block.
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
