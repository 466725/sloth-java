package com.framework.helpers;

import config.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;

import java.io.File;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Screenshot provider class
 *
 * @author Weipeng Zheng
 *
 */
public class ScreenShotProvider {
    protected final static Logger logger = LogManager.getLogger(ScreenShotProvider.class.getName());

    /**
     * Capture screenshot
     *
     * @return String, screenshot file with folder
     */
    public static String captureScreenShot(WebDriver driver, String name) {
        try {
            return saveScreenShot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), name);
        } catch (WebDriverException e) {
            logger.error("Exception is: ", e);
            return null;
        }
    }

    /**
     * Capture screenshot
     *
     * @return String, screenshot file with folder
     */
    public static String captureScreenShot(WebDriver driver) {
        try {
            return saveScreenShot(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE));
        } catch (WebDriverException e) {
            logger.error("Exception is: ", e);
            return null;
        }
    }

    /**
     * Save screenshot
     *
     * @return String, screenshot name
     */
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

    /**
     * Save screenshot
     *
     */
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