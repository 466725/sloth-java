package testcases.amazon;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.GuiTestCase;
import webpages.amazon.HomePage;
import webpages.amazon.RegisterPage;
import webpages.amazon.SigninPage;

public class RegisterPageTest extends GuiTestCase {
    protected final static Logger logger = LogManager.getLogger(RegisterPageTest.class.getName());
    HomePage homepage;
    SigninPage signinPage;
    RegisterPage registerPage;

    // Verify title
    @Test
    public void verifyTitle() {
        homepage = basePage.gotoHomePage();
        signinPage = (SigninPage) homepage.gotoSigninPage();
        registerPage = (RegisterPage) signinPage.gotoRegisterPage();
        logger.info("Title: " + driver.getTitle());
        Assert.assertTrue(driver.getTitle().contains("Amazon Business"), "Title verification failed");
    }
}
