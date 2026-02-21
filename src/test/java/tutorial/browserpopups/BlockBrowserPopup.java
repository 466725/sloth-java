package tutorial.browserpopups;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import webpages.BaseWebPage;

import java.awt.*;
import java.awt.event.KeyEvent;

// Reference: https://www.browserstack.com/automate/handle-popups-alerts-prompts-in-automated-tests
public class BlockBrowserPopup {
    protected final static Logger logger = LogManager.getLogger(BlockBrowserPopup.class.getName());
    private static WebDriver  driver = BaseWebPage.getDriver("Chrome");
    private static String URL = "https://blog.csdn.net/cool_soup29/article/details/90412610";

    @BeforeTest
    public void setUp() {
        logger.info("Test started!");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null)
            driver.quit();
        logger.info("Test ended!");
    }

    // Opens the page where the popup appears.
    @Test(priority = 3)
    public void testPopupWindowChrome() throws Exception {
        driver.get(URL);
        Thread.sleep(5000);
    }

    // Opens the page and dismisses the popup with ESC.
    @Test(priority = 7)
    public void testDismissingPopupWindowChrome() throws Exception {
        driver.get(URL);
        Thread.sleep(5000);
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ESCAPE);
        Thread.sleep(5000);
        driver.quit();
    }
}
