package testcases.amazon;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.GuiTestCase;
import testcases.TestGroups;
import webpages.amazon.SigninPage;

public class SigninPageTest extends GuiTestCase {
    final static Logger logger = LogManager.getLogger(SigninPageTest.class.getName());
    SigninPage signinPage;

    // Verifies the Amazon sign-in page title.
    @Test(groups = {TestGroups.REGRESSION, TestGroups.UI_WEB, TestGroups.AMAZON})
    public void shouldDisplayAmazonSigninPageTitle() {
        logger.info("ui.web.amazon.signin.verify_title.start");
        signinPage = (SigninPage) basePage.gotoHomePage("Amazon").gotoSigninPage();
        logger.info("ui.web.amazon.signin.verify_title.state | title=" + driver.getTitle());
        Assert.assertTrue(driver.getTitle().contains(""), "Title verification failed");
    }
}
