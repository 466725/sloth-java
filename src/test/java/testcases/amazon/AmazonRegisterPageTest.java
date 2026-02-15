package testcases.amazon;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.GuiTestCase;
import testcases.TestGroups;
import webpages.BaseWebPage;
import webpages.amazon.AmazonHomePage;
import webpages.amazon.AmazonRegisterPage;
import webpages.amazon.AmazonSigninPage;

import java.util.Objects;

public class AmazonRegisterPageTest extends GuiTestCase {
    protected final static Logger logger = LogManager.getLogger(AmazonRegisterPageTest.class.getName());
    AmazonSigninPage amazonSigninPage;
    AmazonRegisterPage amazonRegisterPage;

    // Verify title
    @Test(groups = {TestGroups.REGRESSION, TestGroups.UI_WEB, TestGroups.QUARANTINE, TestGroups.AMAZON})
    public void verifyTitle() {
        test.setDescription("Verify title on Amazon Register Page");
        amazonSigninPage = (AmazonSigninPage) BaseWebPage.gotoHomePage("Amazon").gotoSigninPage();
        amazonRegisterPage = (AmazonRegisterPage) amazonSigninPage.gotoRegisterPage();
        Assert.assertTrue(Objects.requireNonNull(driver.getTitle()).contains("Amazon Business"), "Title verification failed");
    }
}
