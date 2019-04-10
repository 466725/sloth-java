package com.tutorial.testng;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import com.framework.templates.ApiTestCase;
import com.relevantcodes.extentreports.LogStatus;

public class DataProviderDemo extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(DataProviderDemo.class.getName());

	@DataProvider(name = "data-provider")
	public Object[][] dataProviderMethod() {
		return new Object[][] { { "data one" }, { "data two" }, { "data three" }, { "data four" }, { "data five" } };
	}

	@Test(enabled = true, timeOut = 500, dataProvider = "data-provider")
	public void testOne(String data) throws InterruptedException {
		test = extent.startTest("DataProvider test example 001");
		test.log(LogStatus.INFO, "DataProvider test example 001");

		Thread.sleep(300);
		logger.info("Data is: " + data);
	}

	@Test(enabled = true, timeOut = 500)
	public void testTwo() throws InterruptedException {
		test = extent.startTest("DataProvider test example 002");
		test.log(LogStatus.INFO, "DataProvider test example 002");

		Thread.sleep(400);
		logger.info("Time test method two");
	}
}
