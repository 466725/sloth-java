package com.tutorial.testng;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.relevantcodes.extentreports.LogStatus;

public class DisableTestDemo extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(DisableTestDemo.class.getName());

	@Test(expectedExceptions = { IOException.class }, expectedExceptionsMessageRegExp = "Pass Message test")
	public void testFive() throws Exception {
		throw new IOException("Fail Message test");
	}

	@Test(enabled = true, expectedExceptions = { IOException.class, NullPointerException.class })
	public void testTwo() throws Exception {
		throw new Exception();
	}

	@Test(enabled = false, expectedExceptions = { IOException.class })
	public void testOne() throws Exception {
		throw new IOException();
	}

	@Test(expectedExceptions = { IOException.class }, expectedExceptionsMessageRegExp = "Pass Message test")
	public void testThree() throws Exception {
		throw new IOException("Pass Message test");
	}

	@Test(expectedExceptions = { IOException.class }, expectedExceptionsMessageRegExp = ".* Message .*")
	public void testFour() throws Exception {
		throw new IOException("Pass Message test");
	}
}
