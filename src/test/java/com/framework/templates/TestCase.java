package com.framework.templates;

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
    public final static Logger logger = LogManager.getLogger(TestCase.class.getName());
    public static WebDriver driver;
    public static String API_TEST_BASE_URL = "";
    public static ExtentTest test;
    public static ExtentReports report;

    /**
     * Prepare per BeforeSuite annotation.
     */
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        DOMConfigurator.configure(Constants.RESOURCE_FOLDER + "log4j-config.xml");
        logger.info(Constants.RESOURCE_FOLDER + "log4j-config.xml");
        logger.info(logger.getAllAppenders());
        logger.info("-----------------------Beginning of suite----------------------");
    }

    /**
     * Prepare per BeforeTest annotation.
     *
     */
    @BeforeTest(alwaysRun = true)
    public void beforeTest() {
        logger.info("-----------------------Beginning of test-----------------------");
        report = new ExtentReports(System.getProperty("user.dir") + "ExtentReportResults.html");
        test = report.startTest("sloth-java test automation");
    }

    /**
     * Cleanup per AfterTest annotation.
     */
    @AfterTest(alwaysRun = true)
    public void afterTest() {
        logger.info("----------------------Ending of test--------------------------");
    }

    /**
     * Cleanup per AfterTest annotation.
     */
    @AfterSuite(alwaysRun = true)
    protected void afterSuite() {
        report.endTest(test);
        report.flush();
        logger.info("***** Extent report ready to use! *****");
        logger.info("----------------------Ending of suite-------------------------");
    }
}