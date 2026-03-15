package testcases.amazon;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.GuiTestCase;
import testcases.TestGroups;
import webpages.BaseWebPage;
import webpages.amazon.RegisterPage;
import webpages.amazon.SigninPage;

import java.util.Objects;

public class RegisterPageTest extends GuiTestCase {
    protected final static Logger logger = LogManager.getLogger(RegisterPageTest.class.getName());
    SigninPage signinPage;
    RegisterPage registerPage;

    // Verifies the Amazon register page title.
    @Test(groups = {TestGroups.UI_WEB, TestGroups.QUARANTINE, TestGroups.AMAZON})
    public void shouldDisplayAmazonRegisterPageTitle() {
        test.setDescription("Verify title on Amazon Register Page");
        logger.info("ui.web.amazon.register.verify_title.start");
        signinPage = (SigninPage) BaseWebPage.gotoHomePage("Amazon").gotoSigninPage();
        registerPage = (RegisterPage) signinPage.gotoRegisterPage();
        logger.info("ui.web.amazon.register.verify_title.state | title=" + driver.getTitle());
        Assert.assertTrue(Objects.requireNonNull(driver.getTitle()).contains("Amazon Business"), "Title verification failed");
    }
}
