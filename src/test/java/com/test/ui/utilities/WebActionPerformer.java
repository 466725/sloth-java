package com.test.ui.utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import config.Constants;

/**
 * WebActionPerformer to host all web action related methods
 * 
 * @author Weipeng Zheng
 *
 */
public class WebActionPerformer {
	protected final static Logger logger = LogManager.getLogger(WebActionPerformer.class.getName());

	/**
	 * Scroll to the location of a specific web element
	 * 
	 * @param driver  web browser driver
	 * @param element web element to scroll to
	 */
	public static boolean scrollToElement(WebDriver driver, WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		int yScrollPosition = element.getLocation().getY();
		try {
			executor.executeScript("window.scroll(0, " + yScrollPosition + ");");
			return true;
		} catch (Exception e) {
			try {
				new Actions(driver).moveToElement(element).perform();
				return true;
			} catch (Exception e1) {
				logger.error("Exception is: ", e1);
				return false;
			}
		}
	}

	/**
	 * Perform click action to a specific web element
	 * 
	 * @param driver      web browser driver
	 * @param element     web element to scroll to
	 * @param clickMethod action to perform
	 * @param waitTime    time to wait
	 */
	public static boolean clickElement(WebDriver driver, WebElement element, Constants.CLICK_METHOD_ENUM clickMethod, int waitTime) {
		if (!(element.isDisplayed() && element.isEnabled())) {
			WaitHandler.explicitWait(driver, element, waitTime);
		}
		switch (clickMethod) {
		case CLICK:
			element.click();
			logger.info("element.click(), called.");
			return true;
		case SENDENTER:
			element.sendKeys(Keys.ENTER);
			logger.info("element.sendKeys(Keys.ENTER), called.");
			return true;
		case SENDRETURN:
			element.sendKeys(Keys.RETURN);
			logger.info("element.sendKeys(Keys.RETURN), called.");
			return true;
		case SUBMIT:
			element.submit();
			logger.info("element.submit(), called.");
			return true;
		case RUNJS:
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			logger.info("((JavascriptExecutor) driver).executeScript(\"arguments[0].click();\", element), called.");
			return true;
		default:
			return false;
		}
	}
}