package testcases.amazon;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.GuiTestCase;
import webpages.BaseWebPage;
import webpages.amazon.HomePage;
import webpages.amazon.RegisterPage;
import webpages.amazon.SigninPage;

import java.util.Objects;

public class RegisterPageTest extends GuiTestCase {
    protected final static Logger logger = LogManager.getLogger(RegisterPageTest.class.getName());
    HomePage homepage;
    SigninPage signinPage;
    RegisterPage registerPage;

    // Verify title
    @Test()
    public void verifyTitle() {
        homepage = BaseWebPage.gotoHomePage();
        signinPage = (SigninPage) homepage.gotoSigninPage();
        registerPage = (RegisterPage) signinPage.gotoRegisterPage();
        Assert.assertTrue(Objects.requireNonNull(driver.getTitle()).contains("Amadzon Business"), "Title verification failed");
    }
}
