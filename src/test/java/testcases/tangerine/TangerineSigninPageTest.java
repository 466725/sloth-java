package testcases.tangerine;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.GuiTestCase;
import testcases.TestGroups;
import webpages.BaseWebPage;
import webpages.tangerine.TangerineSigninPage;

import java.util.Objects;

public class TangerineSigninPageTest extends GuiTestCase {
    protected final static Logger logger = LogManager.getLogger(TangerineSigninPageTest.class.getName());
    TangerineSigninPage tangerineSigninPage;

    // Verify title
    @Test(groups = {TestGroups.REGRESSION, TestGroups.UI_WEB})
    public void verifyTitle() {
        test.setDescription("Verify title on Tangerine Signup Page");
        tangerineSigninPage = (TangerineSigninPage) BaseWebPage.gotoHomePage("Tangerine").gotoSigninPage();
        Assert.assertTrue(Objects.requireNonNull(driver.getTitle()).contains("Tangerine"), "Title verification failed");
    }
}
