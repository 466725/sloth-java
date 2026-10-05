package ui.tangerine.playwright;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import core.PlaywrightGuiTestCase;
import core.TestGroups;
import webpages.playwright.PlaywrightBasePage;
import webpages.playwright.tangerine.SigningPage;

import java.util.Objects;

public class SigningPagePlaywrightTest extends PlaywrightGuiTestCase {
    protected final static Logger logger = LogManager.getLogger(SigningPagePlaywrightTest.class.getName());
    SigningPage tangerineSigningPage;

    // Verifies the Tangerine sign-in page title.
    @Test(groups = {TestGroups.REGRESSION, TestGroups.PLAYWRIGHT, TestGroups.UI_WEB, TestGroups.TANGERINE})
    public void shouldDisplayTangerineSigningPageTitle() {
        test.setDescription("Verify title on Tangerine Signup Page");
        logger.info("ui.web.tangerine.signin.verify_title.start");
        tangerineSigningPage = PlaywrightBasePage.gotoHomePage(page).gotoSigningPage();
        logger.info("ui.web.tangerine.signin.verify_title.state | title=" + page.title());
        Assert.assertTrue(Objects.requireNonNull(page.title()).contains("Tangerine"), "Title verification failed");
    }
}
