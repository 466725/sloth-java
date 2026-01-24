package testcases.amazon;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.GuiTestCase;
import webpages.amazon.HomePage;

public class HomePageTest extends GuiTestCase {
    protected final static Logger logger = LogManager.getLogger(HomePageTest.class.getName());
    HomePage homepage;

    // Verify title
    @Test
    public void verifyTitle() {
        homepage = basePage.gotoHomePage();
        logger.info("Title: " + driver.getTitle());
        Assert.assertTrue(driver.getTitle().contains("Amazon.com."), "Title verification failed");
    }
}
