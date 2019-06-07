package com.utilities;

import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import config.Constants;

public class WebPagePopupHandler extends WebPageUtils {
	protected final static Logger logger = LogManager.getLogger(WebPagePopupHandler.class.getName());

	public static void switchToWindow(WebDriver driver, String parentWindow) {
		ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
		logger.info("driver.getWindowHandle(): " + parentWindow);
		logger.info("driver.getWindowHandles().size(): " + windows.size());
		logger.info("driver.getWindowHandles().toString(): " + windows.toString());
		if (windows.size() > 1) {
			if (windows.contains(parentWindow))
				windows.remove(parentWindow);
			driver.switchTo().window(windows.get(0));
			WebPageUtils.gotoSleep(1);
		}
	}

	public static void switchToAlert(WebDriver driver, String parentWindow, Constants.ALLERT_METHOD_ENUM alertMethods) {
		ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
		logger.info("driver.getWindowHandle(): " + parentWindow);
		logger.info("driver.getWindowHandles().size(): " + windows.size());
		logger.info("driver.getWindowHandles().toString(): " + windows.toString());
		switch (alertMethods) {
		case SWITCHTO:
			logger.info("driver.switchTo().alert().getText(): called. ");
			driver.switchTo().alert();
			return;
		case NO:
			logger.info("driver.switchTo().alert().dismiss(): called. ");
			driver.switchTo().alert().dismiss();
			return;
		case YES:
			logger.info("driver.switchTo().alert().accept(): called. ");
			driver.switchTo().alert().accept();
			return;
		default:
			logger.info("driver.switchTo().alert().getText(): " + driver.switchTo().alert().getText());
			return;
		}
	}
}
