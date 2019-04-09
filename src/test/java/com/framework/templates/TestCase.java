package com.framework.templates;

import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.framework.helpers.DatabaseConnectionManager;
import com.framework.helpers.DatabaseStatementManager;
import com.framework.helpers.ExtentReportHelper;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import config.Constants;
import config.Constants.DB_CONN_ENUM;
import io.restassured.RestAssured;

public class TestCase {
	protected final static Logger logger = LogManager.getLogger(TestCase.class.getName());

	protected static WebDriver driver;
	protected static ExtentReports extent;
	protected static ExtentTest test;
	protected static DB_CONN_ENUM dbConn = DB_CONN_ENUM.LOCALHOST_POSTGRE;

	@BeforeSuite(alwaysRun = true)
	public static void beforeSuite() {
		DOMConfigurator.configure(Constants.RESOURCE_FOLDER + "log4j-config.xml");
		logger.info(Constants.RESOURCE_FOLDER + "log4j-config.xml");
		logger.info(logger.getAllAppenders());
		logger.info("-----------------------Beginning of suite----------------------");
	}

	@Parameters({ "browser", "dbConnection" })
	@BeforeTest(alwaysRun = true)
	public void beforeTest(String browser, String dbConnection) {
		logger.info("-----------------------Beginning of test-----------------------");
		logger.info("dbConn.toString() before: " + dbConn.toString());
		if (dbConnection.compareToIgnoreCase("Localhost_Postgre") == 0)
			TestCase.dbConn = DB_CONN_ENUM.LOCALHOST_POSTGRE;
		if (dbConnection.compareToIgnoreCase("Localhost_Sybase") == 0)
			TestCase.dbConn = DB_CONN_ENUM.LOCALHOST_SYBASE;
		logger.info("dbConn.toString() after: " + dbConn.toString());
		try {
			DatabaseConnectionManager.getConnection(dbConn);
			DatabaseStatementManager.createStatement(dbConn);
		} catch (Exception e) {
			logger.error("Exception is: ", e);
		}
		RestAssured.baseURI = Constants.API_TEST_BASE_URL;
		extent = ExtentReportHelper.getExtentReporter(browser);
	}

	@AfterTest(alwaysRun = true)
	public void afterTest() {
		try {
			DatabaseStatementManager.closeStatement();
			DatabaseConnectionManager.closeConnection();
		} catch (SQLException e) {
			logger.error("Exception is: ", e);
		}
		logger.info("----------------------Ending of test--------------------------");
	}

	@AfterSuite(alwaysRun = true)
	protected void afterSuite() {
		extent.flush();
		extent.close();
		logger.info("***** Extent report ready to use! *****");
		logger.info("----------------------Ending of suite-------------------------");
	}
}