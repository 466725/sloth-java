package utilities;

import config.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;

/**
 * Screenshot provider class
 *
 * @author Weipeng Zheng
 */
public class ScreenShotHandler {
    private static final Logger logger = LogManager.getLogger(ScreenShotHandler.class);

    /**
     * Capture screenshot and save it to the default screenshot folder.
     *
     * @param driver         WebDriver instance
     * @param screenshotName Name of the screenshot file (without extension)
     * @return Path to the saved screenshot, or null if capture fails
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
        String destinationPath = Constants.SCREENSHOT_FOLDER + screenshotName + ".png";
        File destinationFile = new File(destinationPath);

        try {
            FileUtils.copyFile(screenShot, destinationFile);
            return destinationPath;
        } catch (IOException e) {
            logger.error("Failed to save screenshot to " + destinationPath, e);
            return null;
        }
    }
}