package com.tutorial.listener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.relevantcodes.extentreports.LogStatus;

public class ListenerTestCaseDemo2 extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(ListenerTestCaseDemo2.class.getName());

	@Test(enabled = true, timeOut = 500)
	public void testOne() {
		test = extent.startTest("ListenerTestCaseDemo test example 001");
		test.log(LogStatus.INFO, "ListenerTestCaseDemo test example 001");

		Assert.assertTrue(true);
	}

	@Test(enabled = true, timeOut = 500)
	public void testTwo() {
		test = extent.startTest("ListenerTestCaseDemo test example 002");
		test.log(LogStatus.INFO, "ListenerTestCaseDemo test example 002");

		Assert.assertTrue(true);
	}

	@Test(enabled = true, timeOut = 500)
	public void testThree() throws Exception {
		test = extent.startTest("ListenerTestCaseDemo test example 003");
		test.log(LogStatus.INFO, "ListenerTestCaseDemo test example 003");

		throw new Exception("For testing purpose, no worries! ");
	}
}