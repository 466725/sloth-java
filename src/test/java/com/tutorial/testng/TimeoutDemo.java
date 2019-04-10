package com.tutorial.testng;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.relevantcodes.extentreports.LogStatus;

public class TimeoutDemo extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(TimeoutDemo.class.getName());

	@Test(enabled = true, timeOut = 500)
	public void testOne() throws InterruptedException {
		test = extent.startTest("Timeout test example 001");
		test.log(LogStatus.INFO, "Timeout test example 001");

        Thread.sleep(1000);
        logger.info("Time test method one");
	}
	
	@Test(enabled = true, timeOut = 500)
	public void testTwo() throws InterruptedException {
		test = extent.startTest("Timeout test example 002");
		test.log(LogStatus.INFO, "Timeout test example 002");
		
        Thread.sleep(400);
        logger.info("Time test method two");
	}
}
