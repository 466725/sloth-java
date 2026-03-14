package testcases.tangerine;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.GuiTestCase;
import testcases.TestGroups;
import webpages.BaseWebPage;
import webpages.tangerine.SigninPage;
import webpages.tangerine.SignupPage;

import java.util.Objects;

public class SignupPageSeleniumTest extends GuiTestCase {
    protected final static Logger logger = LogManager.getLogger(SignupPageSeleniumTest.class.getName());
    SigninPage signinPage;
    SignupPage signupPage;

    // Verifies the Tangerine sign-up page title.
    @Test(groups = {TestGroups.REGRESSION, TestGroups.UI_WEB, TestGroups.TANGERINE})
    public void shouldDisplayTangerineSignupPageTitle() {
        test.setDescription("Verify title on Amazon Register Page");
        logger.info("ui.web.tangerine.signup.verify_title.start");
        signinPage = (SigninPage) BaseWebPage.gotoHomePage("Tangerine").gotoSigninPage();
        signupPage = signinPage.gotoSignupPage();
        logger.info("ui.web.tangerine.signup.verify_title.state | title=" + driver.getTitle());
        Assert.assertTrue(Objects.requireNonNull(driver.getTitle()).contains("Tangerine"), "Title verification failed");
    }
}
