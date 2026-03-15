package testcases.tangerine;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.PlaywrightGuiTestCase;
import testcases.TestGroups;
import webpages.PlaywrightBasePage;
import webpages.tangerine.SigningPagePlaywright;
import webpages.tangerine.SignupPagePlaywright;

import java.util.Objects;

public class SignupPagePlaywrightTest extends PlaywrightGuiTestCase {
    protected final static Logger logger = LogManager.getLogger(SignupPagePlaywrightTest.class.getName());
    SigningPagePlaywright tangerineSigningPage;
    SignupPagePlaywright tangerineSignupPage;

    // Verifies the Tangerine sign-up page title.
    @Test(groups = {TestGroups.REGRESSION, TestGroups.PLAYWRIGHT, TestGroups.UI_WEB, TestGroups.TANGERINE})
    public void shouldDisplayTangerineSignupPageTitle() {
        test.setDescription("Verify title on Amazon Register Page");
        logger.info("ui.web.tangerine.signup.verify_title.start");
        tangerineSigningPage = PlaywrightBasePage.gotoHomePage(page).gotoSigningPage();
        tangerineSignupPage = tangerineSigningPage.gotoSignupPage();
        logger.info("ui.web.tangerine.signup.verify_title.state | title=" + page.title());
        Assert.assertTrue(Objects.requireNonNull(page.title()).contains("Tangerine"), "Title verification failed");
    }
}
