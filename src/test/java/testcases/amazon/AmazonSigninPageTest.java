package testcases.amazon;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.GuiTestCase;
import testcases.TestGroups;
import webpages.BaseWebPage;
import webpages.amazon.AmazonSigninPage;

public class AmazonSigninPageTest extends GuiTestCase {
    final static Logger logger = LogManager.getLogger(AmazonSigninPageTest.class.getName());
    AmazonSigninPage amazonSigninPage;

    // Verify title
    @Test(groups = {TestGroups.REGRESSION, TestGroups.UI_WEB})
    public void verifyTitle() {
        amazonSigninPage = (AmazonSigninPage) basePage.gotoHomePage("Amazon").gotoSigninPage();
        Assert.assertTrue(driver.getTitle().contains(""), "Title verification failed");
    }
}
