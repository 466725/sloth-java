package utils;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;

import static config.Constants.SCREENSHOT_FOLDER;

/**
 * Screenshot provider class
 *
 * @author Weipeng Zheng
 */
public class ScreenShotHandler {
    private static final Logger logger = LogManager.getLogger(ScreenShotHandler.class.getName());

    /**
     * Capture screenshot and save it to the default screenshot folder.
     *
     * @param driver         WebDriver instance
     * @param screenshotName Name of the screenshot file (without extension)
     * @return Report-relative path to the saved screenshot, or null if capture fails
     */
    public static String captureScreenShot(WebDriver driver, String screenshotName) {
        try {
            File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            return saveScreenShot(sourceFile, screenshotName);
        } catch (WebDriverException e) {
            logger.error("Failed to capture screenshot: ", e);
            return null;
        }
    }

    private static String saveScreenShot(File screenShot, String screenshotName) {
        String destinationPath = SCREENSHOT_FOLDER + screenshotName + ".png";
        File destinationFile = new File(destinationPath);
        File parentDir = destinationFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        String reportRelativePath = "ScreenShot/" + screenshotName + ".png";

        try {
            FileUtils.copyFile(screenShot, destinationFile);
            return reportRelativePath;
        } catch (IOException e) {
            logger.error("Failed to save screenshot to " + destinationPath, e);
            return null;
        }
    }
}

