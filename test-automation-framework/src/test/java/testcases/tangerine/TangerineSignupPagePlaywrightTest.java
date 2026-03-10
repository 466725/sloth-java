package testcases.tangerine;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.PlaywrightGuiTestCase;
import testcases.TestGroups;
import webpages.PlaywrightBaseWebPage;
import webpages.tangerine.TangerineSigninPagePlaywright;
import webpages.tangerine.TangerineSignupPagePlaywright;

import java.util.Objects;

public class TangerineSignupPagePlaywrightTest extends PlaywrightGuiTestCase {
    protected final static Logger logger = LogManager.getLogger(TangerineSignupPagePlaywrightTest.class.getName());
    TangerineSigninPagePlaywright tangerineSigninPage;
    TangerineSignupPagePlaywright tangerineSignupPage;

    // Verifies the Tangerine sign-up page title.
    @Test(groups = {TestGroups.REGRESSION, TestGroups.PLAYWRIGHT, TestGroups.UI_WEB, TestGroups.TANGERINE})
    public void shouldDisplayTangerineSignupPageTitle() {
        test.setDescription("Verify title on Amazon Register Page");
        logger.info("ui.web.tangerine.signup.verify_title.start");
        tangerineSigninPage = PlaywrightBaseWebPage.gotoHomePage(page, "Tangerine").gotoSigninPage();
        tangerineSignupPage = tangerineSigninPage.gotoSignupPage();
        logger.info("ui.web.tangerine.signup.verify_title.state | title=" + page.title());
        Assert.assertTrue(Objects.requireNonNull(page.title()).contains("Tangerine"), "Title verification failed");
    }
}
