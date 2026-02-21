package testcases.tangerine;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.GuiTestCase;
import testcases.TestGroups;
import webpages.BaseWebPage;

public class TangerineHomePageTest extends GuiTestCase {
    final static Logger logger = LogManager.getLogger(TangerineHomePageTest.class.getName());
    BaseWebPage homepage;

    // Verifies the Tangerine home page title.
    @Test(groups = {TestGroups.REGRESSION, TestGroups.SMOKE, TestGroups.UI_WEB, TestGroups.TANGERINE})
    public void verifyTitle() {
        logger.info("ui.web.tangerine.home.verify_title.start");
        homepage = BaseWebPage.gotoHomePage("Tangerine");
        logger.info("ui.web.tangerine.home.verify_title.state | title=" + driver.getTitle());
        Assert.assertTrue(driver.getTitle().contains("Tangerine"), "Title verification failed");
    }
}
