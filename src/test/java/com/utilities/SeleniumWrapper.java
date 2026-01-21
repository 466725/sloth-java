package com.utilities;

import config.Constants;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SeleniumWrapper {
    protected final static Logger logger = LogManager.getLogger(SeleniumWrapper.class.getName());

    private SeleniumWrapper() {
        // Private constructor to prevent instantiation
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

    public static void implicitWait(WebDriver driver) {
        try {
            driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT_TIME);
        } catch (Exception e) {
            logger.warn("Exception is: ", e);
        }
    }

    public static void explicitWaitClickable(WebDriver driver, WebElement element, int waitTime) {
        try {
            (new WebDriverWait(driver, Duration.ofSeconds(waitTime))).until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            logger.warn("Exception is: ", e);
        }
    }

    public static void waitForPageToRender(WebDriver driver) {
        // To be polished, we need a better solution
        try {
            logger.info(driver.getTitle());
            Thread.sleep(Constants.PAGE_RENDER_TIME);
        } catch (InterruptedException e) {
            logger.info("Failed to wait for DOM to be rendered");
            logger.info("Exception is: " + e);
        }
    }

    public static void waitForPageLoadCompletion(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Constants.PAGE_LOAD_TIME);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/svg")));
        } catch (Exception e) {
            logger.debug("Exception is: " + e);
            return;
        }
        WebDriverWait waitForInvisibility = new WebDriverWait(driver, Constants.PAGE_LOAD_TIME);
        waitForInvisibility.ignoring(org.openqa.selenium.NoSuchElementException.class);
        waitForInvisibility.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div/svg")));
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
        SeleniumWrapper.explicitWaitClickable(driver, inputField, Constants.EXPLICIT_WAIT_TIME);
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

    public static boolean clickElement(WebDriver driver, WebElement element, Constants.CLICK_METHOD_ENUM clickMethod) {
        if (!(element.isDisplayed() && element.isEnabled())) {
            waitForPageLoadCompletion(driver);
        }
        switch (clickMethod) {
            case CLICK:
                element.click();
                SeleniumWrapper.waitForPageToRender(driver);
                logger.info("element.click(), called.");
                return true;
            case SENDENTER:
                element.sendKeys(Keys.ENTER);
                SeleniumWrapper.waitForPageToRender(driver);
                logger.info("element.sendKeys(Keys.ENTER), called.");
                return true;
            case SENDRETURN:
                element.sendKeys(Keys.RETURN);
                SeleniumWrapper.waitForPageToRender(driver);
                logger.info("element.sendKeys(Keys.RETURN), called.");
                return true;
            case SUBMIT:
                element.submit();
                SeleniumWrapper.waitForPageToRender(driver);
                logger.info("element.submit(), called.");
                return true;
            case RUNJS:
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                SeleniumWrapper.waitForPageToRender(driver);
                logger.info("((JavascriptExecutor) driver).executeScript(\"arguments[0].click();\", element), called.");
                return true;
            default:
                return false;
        }
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
            // allWebElements = removeUselessElements(allWebElements);
            logger.info("Useless elements removed, in total: " + allWebElements.size());
            for (WebElement e : allWebElements)
                SeleniumWrapper.printWebElementInfo(e);
        } catch (Exception e) {
            logger.error("Exception is: ", e);
        }
        return allWebElements;
    }

    public static WebDriver createDriver(String browser) {
        return switch (browser.toLowerCase()) {
            case "firefox" -> createFirefoxDriver();
            case "ie" -> createIEDriver();
            default -> createChromeDriver();
        };
    }

    private static WebDriver createChromeDriver() {
        if (!OperationSystemDetector.isWindows() && !OperationSystemDetector.isMac()) {
            logger.fatal("Unsupported platform: " + OperationSystemDetector.getOS());
            return null;
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {return null;}

    private static WebDriver createIEDriver() {
        return null;
    }
}