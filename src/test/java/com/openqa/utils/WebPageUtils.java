package com.openqa.utils;

import java.util.Iterator;
import java.util.List;

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

public class WebPageUtils {
	protected final static Logger logger = LogManager.getLogger(WebPageUtils.class.getName());

	public static void gotoSleep() {
		try {
			Thread.sleep(1000 * Constants.PAGE_RENDER_TIME);
		} catch (InterruptedException e) {
			logger.info("Exception is: ", e);
		}
	}

	public static void gotoSleep(int seconds) {
		try {
			Thread.sleep(1000 * seconds);
		} catch (InterruptedException e) {
			logger.info("Exception is: ", e);
		}
	}

	private static void simpleExplicitlyWait(WebDriver driver, WebElement element) {
		(new WebDriverWait(driver, Constants.EXPLICIT_WAIT_TIME)).until(ExpectedConditions.elementToBeClickable(element));
	}

	public static void enhancedExplicitlyWait(WebDriver driver, WebElement element) {
		do
			try {
				simpleExplicitlyWait(driver, element);
			} catch (Exception e) {
				logger.error("Exception is: ", e);
				break;
			}
		while (!isElementDisplayed(element));
	}

	public static boolean isElementDisplayed(WebElement element) {
		try {
			if (element.isDisplayed())
				return true;
		} catch (Exception e) {
			logger.error("Exception is: ", e);
			return false;
		}
		return false;
	}

	public static String getElementText(WebElement element) {
		try {
			return (element.getText().isEmpty()) ? "-" : "\"" + element.getText() + "\"";
		} catch (Exception e) {
			return "-";
		}
	}

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

	public static boolean sendKeys(WebDriver driver, WebElement element, String key, int waitTime) {
		if (driver.equals(null)) {
			logger.fatal("WebDriver driver.equals(null), return false.");
			return false;
		}
		if (!(element.isDisplayed() && element.isEnabled())) {
			logger.error("WebElement element.isDisplayed() && element.isEnabled() is false!");
			enhancedExplicitlyWait(driver, element);
		}
		if (element.equals(null)) {
			logger.error("WebElement element.equals(null), return false.");
			return false;
		}
		WebPageUtils.printWebElementInfo(element);
		element.clear();
		logger.info("element.clear(), called.");
		element.sendKeys(key);
		logger.info("element.sendKeys(" + key + "), called.");
		WebPageUtils.gotoSleep(waitTime);
		return true;
	}

	public static boolean clickElement(WebDriver driver, WebElement element, Constants.CLICK_METHOD_ENUM how,
			int waitTime) {
		if (driver.equals(null)) {
			logger.fatal("WebDriver driver.equals(null), return false.");
			return false;
		}
		if (!(element.isDisplayed() && element.isEnabled())) {
			enhancedExplicitlyWait(driver, element);
		}
		if (element.equals(null)) {
			logger.error("WebElement element.equals(null), return false.");
			return false;
		}
		WebPageUtils.printWebElementInfo(element);
		switch (how) {
		case CLICK:
			element.click();
			logger.info("element.click(), called.");
			WebPageUtils.gotoSleep(waitTime);
			return true;
		case SENDENTER:
			element.sendKeys(Keys.ENTER);
			logger.info("element.sendKeys(Keys.ENTER), called.");
			WebPageUtils.gotoSleep(waitTime);
			return true;
		case SENDRETURN:
			element.sendKeys(Keys.RETURN);
			logger.info("element.sendKeys(Keys.RETURN), called.");
			WebPageUtils.gotoSleep(waitTime);
			return true;
		case SUBMIT:
			element.submit();
			logger.info("element.submit(), called.");
			WebPageUtils.gotoSleep(waitTime);
			return true;
		case RUNJS:
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			logger.info("((JavascriptExecutor) driver).executeScript(\"arguments[0].click();\", element), called.");
			WebPageUtils.gotoSleep(waitTime);
			return true;
		default:
			return false;
		}
	}

	public static boolean scrollToAndClickElement(WebDriver driver, WebElement element,
			Constants.CLICK_METHOD_ENUM how) {
		scrollToElement(driver, element);
		return clickElement(driver, element, how, 0);
	}

	public static List<WebElement> locateAllWebElements(WebDriver driver) {
		List<WebElement> allWebElements = null;
		printWebDriverInfo(driver);
		try {
			allWebElements = driver.findElements(By.cssSelector("*"));
			logger.info("All elements located, in total: " + allWebElements.size());
			// allWebElements = removeUselessElements(allWebElements);
			logger.info("Useless elements removed, in total: " + allWebElements.size());
			for (WebElement e : allWebElements)
				printWebElementInfo(e);
		} catch (Exception e) {
			logger.error("Exception is: ", e);
		}
		return allWebElements;
	}

	public static List<WebElement> locateAllWebElements(WebDriver driver, Boolean printDriverInfo,
			Boolean printWebElementInfo) {
		List<WebElement> allWebElements = null;
		if (printDriverInfo)
			printWebDriverInfo(driver);
		try {
			allWebElements = driver.findElements(By.cssSelector("*"));
			logger.info("All elements located, in total: " + allWebElements.size());
			// allWebElements = removeUselessElements(allWebElements);
			logger.info("Useless elements removed, in total: " + allWebElements.size());
			if (printWebElementInfo)
				for (WebElement e : allWebElements)
					printWebElementInfo(e);
		} catch (Exception e) {
			logger.error("Exception is: ", e);
		}
		return allWebElements;
	}

	public static List<WebElement> removeUselessElements(List<WebElement> allElements) {
		Iterator<WebElement> iterator = allElements.iterator();
		while (iterator.hasNext()) {
			WebElement element = iterator.next();
			int xLocation = element.getLocation().getX();
			String tagName = element.getTagName();
			if (xLocation == 0 || tagName.contains("script")) {
				iterator.remove();
			}
		}
		return allElements;
	}

	/**
	 * Print info of a specific WebDriver
	 * 
	 * @param driver the WebDriver to print info for
	 */
	public static void printWebDriverInfo(WebDriver driver) {
		logger.debug("");
		logger.debug("driver.toString(): " + driver.toString());
		logger.debug("driver.getCurrentUrl(): " + driver.getCurrentUrl());
		logger.debug("driver.getTitle(): " + driver.getTitle());
		logger.debug("driver.getWindowHandles().size(): " + driver.getWindowHandles().size());
		logger.debug("driver.getWindowHandles().toString(): " + driver.getWindowHandles().toString());
		logger.debug("driver.getPageSource(): ");
		logger.debug(driver.getPageSource());
		logger.debug("");
	}

	/**
	 * Print info of a specific WebElement
	 * 
	 * @param element the WebElement to print info for
	 */
	public static void printWebElementInfo(WebElement element) {
		logger.debug("");
		logger.debug("getText(): " + element.getText());
		logger.debug("getTagName(): " + element.getTagName());
		logger.debug("getLocation(): " + element.getLocation());
		logger.debug("getAttribute(\"id\"): " + element.getAttribute("id"));
		logger.debug("getAttribute(\"src\"): " + element.getAttribute("src"));
		logger.debug("getAttribute(\"class\"): " + element.getAttribute("class"));
		logger.debug("getAttribute(\"name\"): " + element.getAttribute("name"));
		logger.debug("getAttribute(\"type\"): " + element.getAttribute("type"));
		logger.debug("getAttribute(\"style\"): " + element.getAttribute("style"));
		logger.debug("getAttribute(\"value\"): " + element.getAttribute("value"));
		logger.debug("getAttribute(\"onload\"): " + element.getAttribute("onload"));
		logger.debug("getAttribute(\"onfocus\"): " + element.getAttribute("onfocus"));
		logger.debug("getAttribute(\"onclick\"): " + element.getAttribute("onclick"));
		logger.debug("getAttribute(\"tabindex\"): " + element.getAttribute("tabindex"));
		logger.debug("getAttribute(\"onmouseover\"): " + element.getAttribute("onmouseover"));
		logger.debug("getAttribute(\"onmouseout\"): " + element.getAttribute("onmouseout"));
		logger.debug("");
	}
}
