package scenarios.ui.tangerine;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.GuiTestCase;
import testcases.TestGroups;
import webpages.BaseWebPage;
import webpages.tangerine.SigninPage;
import webpages.tangerine.SignupPage;

import java.util.Objects;

public class TestSignupFlow extends GuiTestCase {
    protected final static Logger logger = LogManager.getLogger(TestSignupFlow.class.getName());
    SigninPage signinPage;
    SignupPage signupPage;

    // Verifies navigation to the Tangerine sign-up flow.
    @Test(groups = {TestGroups.REGRESSION, TestGroups.SMOKE, TestGroups.UI_WEB, TestGroups.QUARANTINE})
    public void shouldNavigateToTangerineSignupFlow() {
        test.setDescription("Verify title on Amazon Register Page");
        // Navigate to Tangerine home page, then to the sign-in page.
        signinPage = (SigninPage) BaseWebPage.gotoHomePage("Tangerine").gotoSigninPage();
        // Navigate to the sign-up page.
        signupPage = signinPage.gotoSignupPage();
        // Verify the title contains "Tangerine".
        Assert.assertTrue(Objects.requireNonNull(driver.getTitle()).contains("Tangerine"), "Title verification failed");
        // TODO: Fill out the form and verify validation messages.
    }
}
