package ui.tangerine.selenium;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import core.GuiTestCase;
import core.TestGroups;
import webpages.selenium.google.GoogleSearchHomePage;

public class GoogleSearchForAmazonTest extends GuiTestCase {
    final static Logger logger = LogManager.getLogger(GoogleSearchForAmazonTest.class.getName());
    GoogleSearchHomePage homepage;

    // Verifies google search functionality by searching for "Amazon" and clicking enter
    @Test(groups = {TestGroups.SELENIUM})
    public void googleSearchAmazonAndClickEnter() {
        homepage = (GoogleSearchHomePage) GoogleSearchHomePage.gotoGoogleSearchHomePage();
        homepage.getSearchBox().sendKeys("Amazon");
        logger.info("Searching for Amazon on Google");
        homepage.getSearchBox().submit();
        logger.info("Submitted search for Amazon on Google");
    }
}
