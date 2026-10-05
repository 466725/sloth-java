package ui.tangerine.playwright;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import core.PlaywrightGuiTestCase;
import core.TestGroups;
import webpages.playwright.PlaywrightBasePage;
import webpages.playwright.tangerine.HomePage;

public class HomePagePlaywrightTest extends PlaywrightGuiTestCase {
    final static Logger logger = LogManager.getLogger(HomePagePlaywrightTest.class.getName());
    HomePage homepage;

    // Verifies the Tangerine home page title.
    @Test(groups = {TestGroups.PLAYWRIGHT, TestGroups.SMOKE, TestGroups.REGRESSION, TestGroups.INTEGRATION})
    public void shouldDisplayTangerineHomePageTitle() {
        logger.info("ui.web.tangerine.home.verify_title.start");
        homepage = PlaywrightBasePage.gotoHomePage(page);
        logger.info("ui.web.tangerine.home.verify_title.state | title=" + page.title());
        Assert.assertTrue(page.title().contains("Tangerine"), "Title verification failed");
    }
}
