package com.test.ui.utilities;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import config.Constants;

/**
 * WaitHandler to wait for a web element to be ready
 * 
 * @author Weipeng Zheng
 *
 */
public class WaitHandler {
	protected final static Logger logger = LogManager.getLogger(WaitHandler.class.getName());

	/**
	 * Implicitly wait
	 * 
	 * @param driver web browser driver
	 */
	public static void implicitWait(WebDriver driver) {
		try {
			driver.manage().timeouts().implicitlyWait(Constants.WAIT_TIME_SECOND, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.warn("Exception is: ", e);
		}
	}

	/**
	 * Explicitly wait for a web element to be ready
	 * 
	 * @param driver   web browser driver
	 * @param element  web element to scroll to
	 * @param waitTime time to wait
	 */
	public static void explicitWait(WebDriver driver, WebElement element, int waitTime) {
		try {
			(new WebDriverWait(driver, waitTime)).until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			logger.warn("Exception is: ", e);
		}
	}
}