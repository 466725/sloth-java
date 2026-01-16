package com.utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WebDriverUtils {

    /**
     * Retrieves all http cookies associated with current login session
     *
     * @param driver web driver
     * @return List of http cookies
     */
    public static List<HttpCookie> getCookies(WebDriver driver) {
        List<HttpCookie> cookies = new ArrayList<HttpCookie>();
        if (driver != null) {
            Iterator<Cookie> driverCookies = driver.manage().getCookies().iterator();
            while (driverCookies.hasNext()) {
                Cookie c = driverCookies.next();
                HttpCookie hc = new HttpCookie(c.getName(), c.getValue());
                cookies.add(hc);
            }
        }
        return cookies;
    }

    public static File getScreenShot(WebDriver driver) {
        WebDriver d;
        try {
            if (driver.getClass().getName().equals("org.openqa.selenium.remote.RemoteWebDriver")) {
                d = new Augmenter().augment(driver);
            } else {
                d = driver;
            }
            return ((TakesScreenshot) d).getScreenshotAs(OutputType.FILE);
        } catch (Exception e) {
            System.out.println("DEBUG: Screenshot failure:" + e + " with driver: " + driver);
        }
        return null;
    }

    public static boolean elementExists(WebDriver driver, By by) {
        return driver.findElements(by).size() != 0;
    }

    public static boolean elementExists(WebElement webElement, By by) {
        return webElement.findElements(by).size() != 0;
    }

    /**
     * Checks if the element both exists and if it exists, checks if it is displayed
     *
     * @param driver
     * @param by
     * @return whether the element is both existent and displayed
     */
    public static boolean elementExistsAndDisplayed(WebDriver driver, By by) {
        boolean exists = elementExists(driver, by);
        if (!exists) {
            return false;
        }
        boolean displayed = driver.findElement(by).isDisplayed();
        if (!displayed) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the element both exists and if it exists, checks if it is displayed
     *
     * @param driver
     * @param by
     * @return whether the element is both existent and displayed
     */
    public static boolean elementExistsAndDisplayed(WebElement element, By by) {
        boolean exists = elementExists(element, by);
        if (!exists) {
            return false;
        }
        boolean displayed = element.findElement(by).isDisplayed();
        if (!displayed) {
            return false;
        }
        return true;
    }

    /**
     * For remote web driver testing, a local html file can be injected to test
     * similarly to a local file. This function takes a local file reference and
     * sends it to a local or remote webdriver instance through javascript
     * injection.
     *
     * @param html   File holding HTML for the page
     * @param driver The driver you want to inject to
     * @throws IOException if html file does not exist
     */
    public static void injectHtml(File html, WebDriver driver) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(html));
        byte[] datainBytes = new byte[dis.available()];
        dis.readFully(datainBytes);
        dis.close();
        String content = new String(datainBytes, 0, datainBytes.length);
        content = content.replaceAll("\\r\\n|\\r|\\n", " ").replace("\"", "\\\"");
        String script = "var h1 = document.createElement('div'); " + "h1.innerHTML=\"" + content
                + "\"; document.body.appendChild(h1);";
        ((JavascriptExecutor) driver).executeScript(script);
    }
}
