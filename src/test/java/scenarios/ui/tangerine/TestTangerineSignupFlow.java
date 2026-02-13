package scenarios.ui.tangerine;

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

public class TestTangerineSignupFlow extends GuiTestCase {
    protected final static Logger logger = LogManager.getLogger(TestTangerineSignupFlow.class.getName());
    TangerineSigninPage tangerineSigninPage;
    TangerineSignupPage tangerineSignupPage;

    // Verify title
    @Test(groups = {TestGroups.REGRESSION, TestGroups.UI_WEB, TestGroups.QUARANTINE})
    public void verifyTitle() {
        test.setDescription("Verify title on Amazon Register Page");
        // Navigate to Tangerine Home Page, and then navigate to Signin Page
        tangerineSigninPage = (TangerineSigninPage) BaseWebPage.gotoHomePage("Tangerine").gotoSigninPage();
        // Navigate to Signup Page
        tangerineSignupPage = tangerineSigninPage.gotoSignupPage();
        // Verify title contains "Tangerine"
        Assert.assertTrue(Objects.requireNonNull(driver.getTitle()).contains("Tangerine"), "Title verification failed");
        // Procee with filling out the form and verify error message
    }
}
