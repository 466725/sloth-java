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

    // Verifies navigation to the Tangerine sign-up flow.
    @Test(groups = {TestGroups.REGRESSION, TestGroups.SMOKE, TestGroups.UI_WEB, TestGroups.QUARANTINE})
    public void verifyTangerineSignupFlow() {
        test.setDescription("Verify title on Amazon Register Page");
        // Navigate to Tangerine home page, then to the sign-in page.
        tangerineSigninPage = (TangerineSigninPage) BaseWebPage.gotoHomePage("Tangerine").gotoSigninPage();
        // Navigate to the sign-up page.
        tangerineSignupPage = tangerineSigninPage.gotoSignupPage();
        // Verify the title contains "Tangerine".
        Assert.assertTrue(Objects.requireNonNull(driver.getTitle()).contains("Tangerine"), "Title verification failed");
        // TODO: Fill out the form and verify validation messages.
    }
}
