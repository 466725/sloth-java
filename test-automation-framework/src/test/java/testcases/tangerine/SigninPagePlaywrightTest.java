package testcases.tangerine;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.PlaywrightGuiTestCase;
import testcases.TestGroups;
import webpages.PlaywrightBasePage;
import webpages.tangerine.SigninPagePlaywright;

import java.util.Objects;

public class SigninPagePlaywrightTest extends PlaywrightGuiTestCase {
    protected final static Logger logger = LogManager.getLogger(SigninPagePlaywrightTest.class.getName());
    SigninPagePlaywright tangerineSigninPage;

    // Verifies the Tangerine sign-in page title.
    @Test(groups = {TestGroups.REGRESSION, TestGroups.PLAYWRIGHT, TestGroups.UI_WEB, TestGroups.TANGERINE})
    public void shouldDisplayTangerineSigninPageTitle() {
        test.setDescription("Verify title on Tangerine Signup Page");
        logger.info("ui.web.tangerine.signin.verify_title.start");
        tangerineSigninPage = PlaywrightBasePage.gotoHomePage(page).gotoSigninPage();
        logger.info("ui.web.tangerine.signin.verify_title.state | title=" + page.title());
        Assert.assertTrue(Objects.requireNonNull(page.title()).contains("Tangerine"), "Title verification failed");
    }
}
