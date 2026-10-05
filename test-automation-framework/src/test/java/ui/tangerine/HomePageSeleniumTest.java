package ui.tangerine;

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
    @Test(groups = {TestGroups.REGRESSION, TestGroups.SMOKE, TestGroups.UI_WEB, TestGroups.TANGERINE})
    public void shouldDisplayTangerineHomePageTitle() {
        logger.info("ui.web.tangerine.home.verify_title.start");
        homepage = SeleniumBasePage.gotoHomePage("Tangerine");
        logger.info("ui.web.tangerine.home.verify_title.state | title=" + driver.getTitle());
        Assert.assertTrue(driver.getTitle().contains("Tangerine"), "Title verification failed");
    }
}
