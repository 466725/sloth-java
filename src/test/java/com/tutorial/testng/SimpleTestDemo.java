package com.tutorial.testng;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.relevantcodes.extentreports.LogStatus;

public class SimpleTestDemo extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(SimpleTestDemo.class.getName());

	private int param = 50;

	public SimpleTestDemo(int param) {
		this.param = param;
	}

	@Test(enabled = true, timeOut = 500)
	public void testOne() {
		test = extent.startTest("Factory test example 001");
		test.log(LogStatus.INFO, "Factory test example 001");

		int opValue = param + 1;
		logger.info("Test method one output: " + opValue);
	}

	@Test(enabled = true, timeOut = 500)
	public void testTwo() {
		test = extent.startTest("Factory test example 002");
		test.log(LogStatus.INFO, "Factory test example 002");

		int opValue = param + 2;
		logger.info("Test method two output: " + opValue);
	}
}
