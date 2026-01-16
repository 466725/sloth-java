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
        Thread.sleep(1000);
        logger.info("Time test method one");
	}
	
	@Test(enabled = true, timeOut = 500)
	public void testTwo() throws InterruptedException {
        Thread.sleep(400);
        logger.info("Time test method two");
	}
}
