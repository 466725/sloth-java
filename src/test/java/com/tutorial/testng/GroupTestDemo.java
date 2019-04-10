package com.tutorial.testng;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.relevantcodes.extentreports.LogStatus;

public class GroupTestDemo extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(GroupTestDemo.class.getName());

	@Test(enabled = true, timeOut = 500, groups = { "test-group" })
	public void testOne() throws InterruptedException {
		test = extent.startTest("Group test example 001");
		test.log(LogStatus.INFO, "Group test example 001");

		Thread.sleep(300);
		logger.info("Group test method one");
	}

	@Test(enabled = true, timeOut = 500)
	public void testTwo() throws InterruptedException {
		test = extent.startTest("Group test example 002");
		test.log(LogStatus.INFO, "Group test example 002");

		Thread.sleep(300);
		logger.info("Group test method two");
	}
	
	@Test(enabled = true, timeOut = 500, groups = { "test-group" })
	public void testThree() throws InterruptedException {
		test = extent.startTest("Group test example 003");
		test.log(LogStatus.INFO, "Group test example 003");

		Thread.sleep(300);
		logger.info("Group test method three");
	}
}
