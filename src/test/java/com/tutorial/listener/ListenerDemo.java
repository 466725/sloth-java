package com.tutorial.listener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerDemo implements ITestListener {
	protected final static Logger logger = LogManager.getLogger(ListenerDemo.class.getName());

	@Override
	public void onFinish(ITestContext Result) {
		logger.info("***********************ITestListener onFinish***********************");
	}

	@Override
	public void onStart(ITestContext Result) {
		logger.info("***********************ITestListener onStart***********************");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult Result) {
		logger.info("***********************ITestListener onTestFailedBut***********************");
	}

	@Override
	public void onTestFailure(ITestResult Result) {
		logger.info("***********************ITestListener onTestFailure***********************");
	}

	@Override
	public void onTestSkipped(ITestResult Result) {
		logger.info("***********************ITestListener onTestSkipped***********************");
	}

	@Override
	public void onTestStart(ITestResult Result) {
		logger.info("***********************ITestListener onTestStart***********************");
	}

	@Override
	public void onTestSuccess(ITestResult Result) {
		logger.info("***********************ITestListener onTestSuccess***********************");
	}

}