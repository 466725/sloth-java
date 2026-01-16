package com.tutorial.listener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.relevantcodes.extentreports.LogStatus;

@Listeners(ListenerDemo.class)		

public class ListenerTestCaseDemo extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(ListenerTestCaseDemo.class.getName());

	@Test(enabled = true, timeOut = 500)
	public void testOne() {
		Assert.assertTrue(true);
	}

	@Test(enabled = true, timeOut = 500)
	public void testTwo() {
		Assert.assertTrue(true);
	}

	@Test(enabled = true, timeOut = 500)
	public void testThree() throws Exception {
		throw new Exception("For testing purpose, no worries! ");
	}
}