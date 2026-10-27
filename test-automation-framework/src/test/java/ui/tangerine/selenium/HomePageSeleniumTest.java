package ui.tangerine.selenium;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import core.GuiTestCase;
import core.TestGroups;
import webpages.selenium.SeleniumBasePage;

public class HomePageSeleniumTest extends GuiTestCase {
    final static Logger logger = LogManager.getLogger(HomePageSeleniumTest.class.getName());
    SeleniumBasePage homepage;

    // Verifies the Tangerine home page title.
    @Test(groups = {TestGroups.SELENIUM})
    public void shouldDisplayTangerineHomePageTitle() {
        logger.info("ui.web.tangerine.home.verify_title.start");
        homepage = SeleniumBasePage.gotoHomePage("Tangerine");
        String title = driver.getTitle();
        logger.info("ui.web.tangerine.home.verify_title.state | title=" + title);
        Assert.assertNotNull(title, "Title verification failed: title is null");
        Assert.assertTrue(title.contains("Tangerine"), "Title verification failed");
    }
}
