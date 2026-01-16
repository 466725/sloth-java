package com.utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class WebPageIframeHandler extends WebPageUtils {
    protected final static Logger logger = LogManager.getLogger(WebPageIframeHandler.class.getName());

    public static void printAllIframes(List<WebElement> allIframes) {
        for (WebElement e : allIframes)
            printWebElementInfo(e);
    }

    public static List<WebElement> locateAllIframes(WebDriver driver) {
        List<WebElement> allIframes = driver.findElements(By.tagName("iframe"));
        logger.info("The size() of allIframes is: " + allIframes.size());
        return allIframes;
    }

    public static boolean switchParentIframe(WebDriver driver) {
        try {
            driver.switchTo().parentFrame();
            return true;
        } catch (Exception e) {
            logger.error("Exception is: ", e);
            return false;
        }
    }

    public static boolean switchToIframe(WebDriver driver, WebElement iframeElement) {
        try {
            driver.switchTo().frame(iframeElement);
            return true;
        } catch (Exception e) {
            logger.error("Exception is: ", e);
            return false;
        }
    }
}
