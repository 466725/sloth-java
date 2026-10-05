package ui.tangerine.selenium;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import core.GuiTestCase;
import core.TestGroups;
import webpages.selenium.SeleniumBasePage;
import webpages.selenium.tangerine.SigninPage;
import webpages.selenium.tangerine.SignupPage;

import java.util.Objects;

public class SignupPageSeleniumTest extends GuiTestCase {
    protected final static Logger logger = LogManager.getLogger(SignupPageSeleniumTest.class.getName());
    SigninPage signinPage;
    SignupPage signupPage;

    // Verifies the Tangerine sign-up page title.
    @Test(groups = {TestGroups.REGRESSION, TestGroups.UI_WEB, TestGroups.TANGERINE})
    public void shouldDisplayTangerineSignupPageTitle() {
        test.setDescription("Verify title on Tangerine Register Page");
        logger.info("ui.web.tangerine.signup.verify_title.start");
        signinPage = (SigninPage) SeleniumBasePage.gotoHomePage("Tangerine").gotoSigninPage();
        signupPage = signinPage.gotoSignupPage();
        logger.info("ui.web.tangerine.signup.verify_title.state | title=" + driver.getTitle());
        Assert.assertTrue(Objects.requireNonNull(driver.getTitle()).contains("Tangerine"), "Title verification failed");
    }
}
