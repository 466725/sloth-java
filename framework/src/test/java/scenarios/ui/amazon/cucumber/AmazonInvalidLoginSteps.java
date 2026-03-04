package scenarios.ui.amazon.cucumber;

import config.PropertiesFileReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import webpages.BaseWebPage;
import webpages.amazon.AmazonSigninPage;

import java.time.Duration;

public class AmazonInvalidLoginSteps {
    private static final Logger logger = LogManager.getLogger(AmazonInvalidLoginSteps.class.getName());
    private AmazonSigninPage amazonSigninPage;
    private WebDriver driver;

    @Given("I am on the Amazon sign-in page")
    public void iAmOnTheAmazonSignInPage() {
        driver = BaseWebPage.getDriver(PropertiesFileReader.getBrowser());
        driver.get(PropertiesFileReader.getAmazonURL() + "ap/signin");
        logger.info("cucumber.step.open_signin | url=" + driver.getCurrentUrl());
        amazonSigninPage = new AmazonSigninPage(driver);
    }

    @When("I attempt to sign in with email {string} and password {string}")
    public void iAttemptToSignInWithEmailAndPassword(String email, String password) {
        logger.info("cucumber.step.signin_attempt | email=" + email);
        amazonSigninPage.attemptSignin(email, password);
    }

    @When("I navigate back to Amazon home page")
    public void iNavigateBackToAmazonHomePage() {
        logger.info("cucumber.step.navigate_back_to_home.start");
        driver.navigate().back();
        String baseUrl = PropertiesFileReader.getAmazonURL();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(d -> {
                        String url = d.getCurrentUrl();
                        return url != null && url.contains("amazon.com") && !url.contains("/ap/signin");
                    });
        } catch (Exception ignored) {
            driver.get(baseUrl);
        }

        String currentUrl = driver.getCurrentUrl();
        if (currentUrl == null || !currentUrl.contains("amazon.com") || currentUrl.contains("/ap/signin")) {
            driver.get(baseUrl);
        }
        logger.info("cucumber.step.navigate_back_to_home.end | url=" + driver.getCurrentUrl());
    }

    @Then("I should see a sign-in error on Amazon")
    public void iShouldSeeASignInErrorOnAmazon() {
        logger.info("cucumber.step.verify_signin_error");
        Assert.assertTrue(amazonSigninPage.hasSigninError(),
                "Expected Amazon to show an error for invalid credentials.");
    }

    @Then("I should be on the Amazon home page")
    public void iShouldBeOnTheAmazonHomePage() {
        String currentUrl = driver.getCurrentUrl();
        logger.info("cucumber.step.verify_home_page | url=" + currentUrl);
        Assert.assertTrue(currentUrl.contains("amazon.com") && !currentUrl.contains("/ap/signin"),
                "Expected to be on Amazon home page, but current url is: " + currentUrl);
    }
}
