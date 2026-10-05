package ui.tangerine;

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

public class TestSignupFlow extends GuiTestCase {
    protected final static Logger logger = LogManager.getLogger(TestSignupFlow.class.getName());
    SigninPage signinPage;
    SignupPage signupPage;

    @Test(groups = {TestGroups.REGRESSION, TestGroups.SMOKE, TestGroups.UI_WEB, TestGroups.QUARANTINE})
    public void shouldNavigateToTangerineSignupFlow() {
        test.setDescription("Verify title on Tangerine signup page");
        signinPage = (SigninPage) SeleniumBasePage.gotoHomePage("Tangerine").gotoSigninPage();
        signupPage = signinPage.gotoSignupPage();
        Assert.assertTrue(Objects.requireNonNull(driver.getTitle()).contains("Tangerine"), "Title verification failed");
    }
}
