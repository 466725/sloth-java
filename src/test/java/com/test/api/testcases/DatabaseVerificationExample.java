package com.test.api.testcases;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.helpers.DatabaseResultsetManager;
import com.framework.templates.ApiTestCase;
import com.framework.templates.TestCase;
import com.relevantcodes.extentreports.LogStatus;

import config.Constants;

public class DatabaseVerificationExample extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(DatabaseVerificationExample.class.getName());

	@Test(priority = 2)
	public void checkDatabaseLoginInfo() {
		test = extent.startTest("Database Test: Verify database URL + Username + Password");
		test.log(LogStatus.INFO, "Database Test: Verify database URL + Username + Password");
		Assert.assertFalse(Constants.LOCALHOST_POSTGRE_JDBC_URL.isEmpty());
		logger.info(Constants.LOCALHOST_POSTGRE_JDBC_URL);
		Assert.assertFalse(Constants.LOCALHOST_POSTGRE_JDBC_USERNAME.isEmpty());
		logger.info(Constants.LOCALHOST_POSTGRE_JDBC_USERNAME);
		Assert.assertFalse(Constants.LOCALHOST_POSTGRE_JDBC_PASSWORD.isEmpty());
		logger.info(Constants.LOCALHOST_POSTGRE_JDBC_PASSWORD);
	}

	@Test(priority = 6)
	public void printResultset() throws Exception {
		test = extent.startTest("Database Test: Print everything in resultset");
		test.log(LogStatus.INFO, "Database Test: Print everything in resultset");
		ResultSet resultSet = DatabaseResultsetManager.createResultset(Constants.SQL_EXAMPLE_003, TestCase.dbConn);
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while (resultSet.next())
			for (int i = 1; i <= columnsNumber; i++)
				logger.info(rsmd.getColumnName(i) + ": " + resultSet.getString(i));
		DatabaseResultsetManager.closeResultset(resultSet);
	}
}
