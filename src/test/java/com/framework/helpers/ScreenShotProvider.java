package com.framework.helpers;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import config.Constants;

public class ScreenShotProvider {
	protected final static Logger logger = LogManager.getLogger(ScreenShotProvider.class.getName());

	public static String captureScreenShot(WebDriver driver, String name) {
		try {
			return saveScreenShot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), name);
		} catch (WebDriverException e) {
			logger.error("Exception is: ", e);
			return null;
		}
	}

	public static String captureScreenShot(WebDriver driver) {
		try {
			return saveScreenShot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE));
		} catch (WebDriverException e) {
			logger.error("Exception is: ", e);
			return null;
		}
	}

	private static String saveScreenShot(File screenShot, String name) {
		String screenshotFolder = Constants.SCREENSHOT_FOLDER.toString();
		String screenshotName = name + ".png";
		String screenshotFile = screenshotFolder + screenshotName;
		try {
			FileUtils.copyFile(screenShot, new File(screenshotFile));
		} catch (Exception e) {
			logger.error("Exception is: ", e);
		}
		return ".//ScreenShot/" + screenshotName;
	}

	private static String saveScreenShot(File screenShot) {
		String screenshotFolder = Constants.SCREENSHOT_FOLDER.toString();
		String screenshotName = System.currentTimeMillis() + ".png";
		String screenshotFile = screenshotFolder + screenshotName;
		try {
			FileUtils.copyFile(screenShot, new File(screenshotFile));
		} catch (Exception e) {
			logger.error("Exception is: ", e);
		}
		return ".//ScreenShot/" + screenshotName;
	}
}