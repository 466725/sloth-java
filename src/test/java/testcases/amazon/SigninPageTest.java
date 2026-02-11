package testcases.amazon;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.GuiTestCase;
import testcases.TestGroups;
import webpages.amazon.HomePage;
import webpages.amazon.SigninPage;

public class SigninPageTest extends GuiTestCase {
    final static Logger logger = LogManager.getLogger(SigninPageTest.class.getName());
    HomePage homepage;
    SigninPage signinPage;

    // Verify title
    @Test(groups = {TestGroups.REGRESSION, TestGroups.UI_WEB})
    public void verifyTitle() {
        homepage = basePage.gotoHomePage();
        signinPage = (SigninPage) homepage.gotoSigninPage();
        Assert.assertTrue(driver.getTitle().contains(""), "Title verification failed");
    }
}
