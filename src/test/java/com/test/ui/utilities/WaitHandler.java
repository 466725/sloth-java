package com.test.ui.utilities;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.framework.templates.WebPage;

import config.Constants;

public class WaitHandler extends WebPage {
	protected final static Logger logger = LogManager.getLogger(WaitHandler.class.getName());

	public static void implicitWait(WebDriver driver) {
		try {
			driver.manage().timeouts().implicitlyWait(Constants.WAIT_TIME_SECOND, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
	}

	public static void explicitWait(WebDriver driver, WebElement element) {
		try {
			(new WebDriverWait(driver, Constants.WAIT_TIME_SECOND)).until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
	}
}