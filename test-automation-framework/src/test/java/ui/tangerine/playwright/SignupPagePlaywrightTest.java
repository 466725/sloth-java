package ui.tangerine.playwright;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import core.PlaywrightGuiTestCase;
import core.TestGroups;
import webpages.playwright.PlaywrightBasePage;
import webpages.playwright.tangerine.SigningPage;
import webpages.playwright.tangerine.SignupPage;

import java.util.Objects;

public class SignupPagePlaywrightTest extends PlaywrightGuiTestCase {
    protected final static Logger logger = LogManager.getLogger(SignupPagePlaywrightTest.class.getName());
    SigningPage tangerineSigningPage;
    SignupPage tangerineSignupPage;

    // Verifies the Tangerine sign-up page title.
    @Test(groups = {TestGroups.PLAYWRIGHT, TestGroups.SMOKE, TestGroups.REGRESSION, TestGroups.INTEGRATION})
    public void shouldDisplayTangerineSignupPageTitle() {
        test.setDescription("Verify title on Tangerine signup Page");
        logger.info("ui.web.tangerine.signup.verify_title.start");
        tangerineSigningPage = PlaywrightBasePage.gotoHomePage(page).gotoSigningPage();
        tangerineSignupPage = tangerineSigningPage.gotoSignupPage();
        logger.info("ui.web.tangerine.signup.verify_title.state | title=" + page.title());
        Assert.assertTrue(Objects.requireNonNull(page.title()).contains("Tangerine"), "Title verification failed");
    }
}
