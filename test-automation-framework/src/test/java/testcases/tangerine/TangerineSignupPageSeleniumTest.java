package testcases.tangerine;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.GuiTestCase;
import testcases.TestGroups;
import webpages.BaseWebPage;
import webpages.tangerine.TangerineSigninPage;
import webpages.tangerine.TangerineSignupPage;

import java.util.Objects;

public class TangerineSignupPageSeleniumTest extends GuiTestCase {
    protected final static Logger logger = LogManager.getLogger(TangerineSignupPageSeleniumTest.class.getName());
    TangerineSigninPage tangerineSigninPage;
    TangerineSignupPage tangerineSignupPage;

    // Verifies the Tangerine sign-up page title.
    @Test(groups = {TestGroups.REGRESSION, TestGroups.UI_WEB, TestGroups.TANGERINE})
    public void shouldDisplayTangerineSignupPageTitle() {
        test.setDescription("Verify title on Amazon Register Page");
        logger.info("ui.web.tangerine.signup.verify_title.start");
        tangerineSigninPage = (TangerineSigninPage) BaseWebPage.gotoHomePage("Tangerine").gotoSigninPage();
        tangerineSignupPage = tangerineSigninPage.gotoSignupPage();
        logger.info("ui.web.tangerine.signup.verify_title.state | title=" + driver.getTitle());
        Assert.assertTrue(Objects.requireNonNull(driver.getTitle()).contains("Tangerine"), "Title verification failed");
    }
}
