package utils;

import config.Constants;
import config.PropertiesFileReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public final class SeleniumWrapper {
    private static final Logger logger = LogManager.getLogger(SeleniumWrapper.class.getName());
    private static final By PAGE_LOAD_SPINNER = By.xpath("//div/svg");
    private static final String DOCUMENT_READY_SCRIPT = "return document.readyState";
    private static final String[] ELEMENT_ATTRIBUTES_TO_LOG = {
            "id", "src", "class", "name", "type", "style", "value",
            "onload", "onfocus", "onclick", "tabindex", "onmouseover", "onmouseout"
    };

    private SeleniumWrapper() {
        // Private constructor to prevent instantiation
    }

    public static int findAllElementsSize(WebDriver driver) {
        List<WebElement> elements = driver.findElements(By.xpath("//*"));
        return elements.size();
    }

    public static List<WebElement> findAllElements(WebDriver driver) {
        List<WebElement> elements = driver.findElements(By.xpath("//*"));
        return elements;
    }

    public static void printWebDriverInfo(WebDriver driver) {
        logger.debug("");
        logger.debug("driver.toString(): " + driver.toString());
        logger.debug("driver.getCurrentUrl(): " + driver.getCurrentUrl());
        logger.debug("driver.getTitle(): " + driver.getTitle());
        logger.debug("driver.getWindowHandles().size(): " + driver.getWindowHandles().size());
        logger.debug("driver.getWindowHandles().toString(): " + driver.getWindowHandles());
        logger.debug("driver.getPageSource(): ");
        logger.debug(driver.getPageSource());
        logger.debug("");
    }

    public static void printWebElementInfo(WebElement element) {
        logger.info("");
        logger.info("getText(): " + element.getText());
        logger.info("getTagName(): " + element.getTagName());
        logger.info("getLocation(): " + element.getLocation());
        for (String attribute : ELEMENT_ATTRIBUTES_TO_LOG) {
            logger.info("getAttribute(\"" + attribute + "\"): " + element.getAttribute(attribute));
        }
        logger.info("");
    }

    public static void implicitWait(WebDriver driver) {
        try {
            driver.manage().timeouts().implicitlyWait(PropertiesFileReader.getImplicitWaitTime());
        } catch (Exception e) {
            logger.warn("Exception is: ", e);
        }
    }

    public static void explicitWaitClickable(WebDriver driver, WebElement element, int waitTime) {
        try {
            (new WebDriverWait(driver, Duration.ofSeconds(waitTime)))
                    .until(ExpectedConditions.elementToBeClickable(element));
        } catch (RuntimeException e) {
            logger.warn("Element not clickable after " + waitTime + "s. Current URL: " + driver.getCurrentUrl(), e);
            throw e; // Stop here instead of clicking a non-clickable element.
        }
    }

    public static void waitForPageToRender(WebDriver driver) {
        try {
            logger.info(driver.getTitle());
            Duration timeout = Duration.ofSeconds(PropertiesFileReader.getPageRenderTimeout());
            new WebDriverWait(driver, timeout)
                    .until(d -> "complete".equals(((JavascriptExecutor) d).executeScript(DOCUMENT_READY_SCRIPT)));
        } catch (Exception e) {
            logger.info("Failed to wait for DOM to be rendered");
            logger.info("Exception is: " + e);
        }
    }

    public static void waitForPageLoadCompletion(WebDriver driver) {
        Duration timeout = PropertiesFileReader.getPageLoadTimeout();
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(PAGE_LOAD_SPINNER));
        } catch (Exception e) {
            logger.debug("Exception is: " + e);
            return;
        }
        WebDriverWait waitForInvisibility = new WebDriverWait(driver, timeout);
        waitForInvisibility.ignoring(org.openqa.selenium.NoSuchElementException.class);
        waitForInvisibility.until(ExpectedConditions.invisibilityOfElementLocated(PAGE_LOAD_SPINNER));
    }

    public static boolean hoverMouseOverElement(WebDriver driver, WebElement element) {
        try {
            new Actions(driver).moveToElement(element).perform();
            return true;
        } catch (Exception e) {
            logger.warn("Exception is: ", e);
            return false;
        }
    }

    public static boolean setInputFieldText(WebElement inputField, String textToSet, WebDriver driver) {
        SeleniumWrapper.explicitWaitClickable(driver, inputField, PropertiesFileReader.getExplicitWaitTimeInt());
        try {
            new Actions(driver).moveToElement(inputField).perform();
            inputField.clear();
            inputField.sendKeys(textToSet);
            return true;
        } catch (Exception e) {
            logger.error("Exception is: " + e);
            return false;
        }
    }

    public static boolean clickElement(WebDriver driver, WebElement element, Constants.CLICK_METHOD clickMethod) {
        if (!(element.isDisplayed() && element.isEnabled())) {
            waitForPageLoadCompletion(driver);
        }
        switch (clickMethod) {
            case CLICK:
                return executeClickAction(driver, () -> element.click(), "element.click(), called.");
            case SEND_ENTER:
                return executeClickAction(driver, () -> element.sendKeys(Keys.ENTER), "element.sendKeys(Keys.ENTER), called.");
            case SEND_RETURN:
                return executeClickAction(driver, () -> element.sendKeys(Keys.RETURN), "element.sendKeys(Keys.RETURN), called.");
            case SUBMIT:
                return executeClickAction(driver, () -> element.submit(), "element.submit(), called.");
            case RUN_JS:
                return executeClickAction(
                        driver,
                        () -> ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element),
                        "((JavascriptExecutor) driver).executeScript(\"arguments[0].click();\", element), called."
                );
        }
        return false;
    }

    public static void scrollToElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static List<WebElement> locateAllWebElements(WebDriver driver) {
        List<WebElement> allWebElements = null;
        printWebDriverInfo(driver);
        try {
            allWebElements = driver.findElements(By.cssSelector("*"));
            logger.info("All elements located, in total: " + allWebElements.size());
            // Placeholder for optional filtering of non-useful elements.
            logger.info("Useless elements removed, in total: " + allWebElements.size());
            for (WebElement element : allWebElements) {
                SeleniumWrapper.printWebElementInfo(element);
            }
        } catch (Exception e) {
            logger.error("Exception is: ", e);
        }
        return allWebElements;
    }

    private static boolean executeClickAction(WebDriver driver, Runnable action, String successLog) {
        action.run();
        SeleniumWrapper.waitForPageToRender(driver);
        logger.info(successLog);
        return true;
    }
}
