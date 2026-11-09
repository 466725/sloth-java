package ui.tangerine.selenium;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import core.GuiTestCase;
import core.TestGroups;
import webpages.selenium.SeleniumBasePage;
import webpages.selenium.tangerine.SigninPage;

import java.util.Objects;

public class SigninPageSeleniumTest extends GuiTestCase {
    protected final static Logger logger = LogManager.getLogger(SigninPageSeleniumTest.class.getName());
    SigninPage signinPage;

    // Verifies the Tangerine sign-in page title.
    @Test(groups = {TestGroups.SELENIUM})
    public void shouldDisplayTangerineSigninPageTitle() {
        test.setDescription("Verify title on Tangerine Signin Page");
        logger.info("ui.web.tangerine.signin.verify_title.start");
        signinPage = (SigninPage) SeleniumBasePage.gotoHomePage("Tangerine").gotoSigninPage();
        logger.info("ui.web.tangerine.signin.verify_title.state | title=" + driver.getTitle());
        Assert.assertTrue(Objects.requireNonNull(driver.getTitle()).contains("Tangerine"), "Title verification failed");
    }
}
