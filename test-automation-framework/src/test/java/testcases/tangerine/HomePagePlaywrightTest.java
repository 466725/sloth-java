package testcases.tangerine;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.PlaywrightGuiTestCase;
import testcases.TestGroups;
import webpages.PlaywrightBasePage;
import webpages.tangerine.HomePagePlaywright;

public class HomePagePlaywrightTest extends PlaywrightGuiTestCase {
    final static Logger logger = LogManager.getLogger(HomePagePlaywrightTest.class.getName());
    HomePagePlaywright homepage;

    // Verifies the Tangerine home page title.
    @Test(groups = {TestGroups.REGRESSION, TestGroups.SMOKE, TestGroups.PLAYWRIGHT, TestGroups.UI_WEB, TestGroups.TANGERINE})
    public void shouldDisplayTangerineHomePageTitle() {
        logger.info("ui.web.tangerine.home.verify_title.start");
        homepage = PlaywrightBasePage.gotoHomePage(page);
        logger.info("ui.web.tangerine.home.verify_title.state | title=" + page.title());
        Assert.assertTrue(page.title().contains("Tangerine"), "Title verification failed");
    }
}
