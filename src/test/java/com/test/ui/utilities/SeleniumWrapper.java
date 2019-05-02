package com.test.ui.utilities;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import config.Constants;

/**
 * WebActionPerformer to host all web action related methods
 * 
 * @author Weipeng Zheng
 *
 */
public class SeleniumWrapper {
	protected final static Logger logger = LogManager.getLogger(SeleniumWrapper.class.getName());

	/**
	 * Check if a web element is displayed or not
	 * 
	 * @param element web element to check
	 */
	public static boolean isElementDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (Exception e) {
			logger.error("Exception is: ", e);
			return false;
		}
	}

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

	/**
	 * Waits for the progress bar to disappear ensuring the page has loaded
	 * 
	 * @param driver WebDriver
	 */
	public static void waitForPageToLoad(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Constants.WAIT_TIME_SECOND);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/svg")));
		} catch (Exception e) {
			logger.debug("Exception is: " + e);
			return;
		}
		WebDriverWait waitForInvisibility = new WebDriverWait(driver, Constants.WAIT_TIME_SECOND);
		waitForInvisibility.ignoring(org.openqa.selenium.NoSuchElementException.class);
		waitForInvisibility.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div/svg")));
	}

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
	 * Hover mouse over element
	 * 
	 * @param driver  web browser driver
	 * @param element web element to hover over
	 */
	public static boolean hoverMouseOverElement(WebDriver driver, WebElement element) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(element).perform();
			return true;
		} catch (Exception e) {
			logger.warn("Exception is: ", e);
			return false;
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
	public static boolean clickElement(WebDriver driver, WebElement element, Constants.CLICK_METHOD_ENUM clickMethod,
			int waitTime) {
		if (!(element.isDisplayed() && element.isEnabled())) {
			explicitWait(driver, element, waitTime);
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

	/**
	 * Set text of an input field
	 * 
	 * @param inputField The field to set the text for
	 * @param textToSet  The text to set
	 * @param driver     The WebDriver instance
	 * @return true, if everything successful; otherwise false
	 */
	public static boolean setInputFieldText(WebElement inputField, String textToSet, WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Constants.WAIT_TIME_SECOND);
			wait.until(ExpectedConditions.elementToBeClickable(inputField));
		} catch (Exception e1) {
			logger.info("Exception during wait.until(ExpectedConditions.elementToBeClickable())");
		}
		try {
			new Actions(driver).moveToElement(inputField).perform();
			inputField.clear();
			inputField.sendKeys(textToSet);
			return true;
		} catch (Exception e) {
			logger.warn("Exception is: " + e);
			return false;
		}
	}

	/**
	 * Set text of an input field without using built-in clear() method since
	 * sometimes it does not work
	 * 
	 * @param inputField The field to set the text for
	 * @param textToSet  The text to set
	 * @param driver     The WebDriver instance
	 * @return true, if everything successful; otherwise false
	 */
	public static boolean setInputFieldTextNoClear(WebElement inputField, String textToSet, WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Constants.WAIT_TIME_SECOND);
			wait.until(ExpectedConditions.elementToBeClickable(inputField));
		} catch (Exception e1) {
			logger.info("Exception during wait.until(ExpectedConditions.elementToBeClickable())");
		}
		try {
			new Actions(driver).moveToElement(inputField).perform();
			inputField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
			inputField.sendKeys(textToSet);
			return true;
		} catch (Exception e) {
			logger.warn("Exception is: " + e);
			return false;
		}
	}

	/**
	 * Simulates clicking on the browser back button
	 * 
	 * @param driver The WebDriver
	 */
	public static void goBackToPage(WebDriver driver) {
		driver.navigate().back();
		SeleniumWrapper.waitForPageToLoad(driver);
	}

	/**
	 * Print info of a specific WebDriver
	 * 
	 * @param driver the WebDriver to print info for
	 */
	public static void printWebDriverInfo(WebDriver driver) {
		logger.info("");
		logger.info("driver.toString(): " + driver.toString());
		logger.info("driver.getCurrentUrl(): " + driver.getCurrentUrl());
		logger.info("driver.getTitle(): " + driver.getTitle());
		logger.info("driver.getWindowHandles().size(): " + driver.getWindowHandles().size());
		logger.info("driver.getWindowHandles().toString(): " + driver.getWindowHandles().toString());
		logger.info("driver.getPageSource(): ");
		logger.info(driver.getPageSource());
		logger.info("");
	}

	/**
	 * Print info of a specific WebElement
	 * 
	 * @param element the WebElement to print info for
	 */
	public static void printWebElementInfo(WebElement element) {
		logger.info("");
		logger.info("getText(): " + element.getText());
		logger.info("getTagName(): " + element.getTagName());
		logger.info("getLocation(): " + element.getLocation());
		logger.info("getAttribute(\"id\"): " + element.getAttribute("id"));
		logger.info("getAttribute(\"src\"): " + element.getAttribute("src"));
		logger.info("getAttribute(\"class\"): " + element.getAttribute("class"));
		logger.info("getAttribute(\"name\"): " + element.getAttribute("name"));
		logger.info("getAttribute(\"type\"): " + element.getAttribute("type"));
		logger.info("getAttribute(\"style\"): " + element.getAttribute("style"));
		logger.info("getAttribute(\"value\"): " + element.getAttribute("value"));
		logger.info("getAttribute(\"onload\"): " + element.getAttribute("onload"));
		logger.info("getAttribute(\"onfocus\"): " + element.getAttribute("onfocus"));
		logger.info("getAttribute(\"onclick\"): " + element.getAttribute("onclick"));
		logger.info("getAttribute(\"tabindex\"): " + element.getAttribute("tabindex"));
		logger.info("getAttribute(\"onmouseover\"): " + element.getAttribute("onmouseover"));
		logger.info("getAttribute(\"onmouseout\"): " + element.getAttribute("onmouseout"));
		logger.info("");
	}
}