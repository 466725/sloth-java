package com.framework.testcases;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import config.Constants;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
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
    protected static WebDriver driver;
    protected static ExtentTest test;
    protected static ExtentReports report;

    private static final String LOG_CONFIG_FILE = Constants.RESOURCE_FOLDER + "log4j-config.xml";
    private static final String REPORT_FILE_NAME = "\\test-output\\ExtentReport\\ExtentReport.html";
    private static final String TEST_PROJECT_NAME = "sloth-java test automation";

    /**
     * Prepare per BeforeSuite annotation.
     */
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        setupLog4j();
        logger.info("-----------------------Beginning of suite----------------------");
    }

    private void setupLog4j() {
        DOMConfigurator.configure(LOG_CONFIG_FILE);
        logger.info("Log4j configured from: " + LOG_CONFIG_FILE);
        logger.info(logger.getAllAppenders());
    }

    /**
     * Prepare per BeforeTest annotation.
     */
    @BeforeTest(alwaysRun = true)
    public void beforeTest() {
        logger.info("-----------------------Beginning of test-----------------------");
        String reportPath = System.getProperty("user.dir") + REPORT_FILE_NAME;
        report = new ExtentReports(reportPath);
        test = report.startTest(TEST_PROJECT_NAME);
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
            report.endTest(test);
            report.flush();
            logger.info("***** Extent report ready to use! *****");
        }
        logger.info("----------------------Ending of suite-------------------------");
    }
}