package testcases.amazon;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.GuiTestCase;
import testcases.TestGroups;
import webpages.BaseWebPage;

public class HomePageTest extends GuiTestCase {
    final static Logger logger = LogManager.getLogger(HomePageTest.class.getName());
    BaseWebPage homepage;

    // Verifies the Amazon home page title.
    @Test(groups = {TestGroups.UI_WEB, TestGroups.AMAZON})
    public void shouldDisplayAmazonHomePageTitle() {
        logger.info("ui.web.amazon.home.verify_title.start");
        homepage = BaseWebPage.gotoHomePage("Amazon");
        logger.info("ui.web.amazon.home.verify_title.state | title=" + driver.getTitle());
        Assert.assertTrue(driver.getTitle().contains("Amazon.com."), "Title verification failed");
    }
}
