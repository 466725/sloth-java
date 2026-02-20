package testcases.mobile.web;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestGroups;
import testcases.mobile.WebTestCase;

import java.time.Duration;
import java.util.List;

public class TestAmazonMobileSigninPage extends WebTestCase {
    private static final Logger logger = LogManager.getLogger(TestAmazonMobileSigninPage.class.getName());

    @Test(groups = {TestGroups.SMOKE, TestGroups.UI_MOBILE, TestGroups.AMAZON, TestGroups.INTEGRATION})
    public void verifyAmazonSigninFlowOnAndroidChrome() {
        String signinUrl = getEnvOrDefault("AMAZON_SIGNIN_URL", DEFAULT_AMAZON_SIGNIN_URL);
        driver.get(signinUrl);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/ap/signin"),
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("#ap_email, input[name='email'], input[type='email']"))
        ));

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#ap_email, input[name='email'], input[type='email']")
        ));
        Assert.assertTrue(emailInput.isDisplayed(), "Email field should be visible on Amazon sign-in page.");

        emailInput.clear();
        emailInput.sendKeys("mobile-web-smoke@example.com");

        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#continue, input[name='continue']")
        ));
        continueButton.click();

        wait.until(driver -> {
            List<WebElement> candidates = driver.findElements(By.cssSelector(
                    "#ap_password, input[name='password'], #auth-error-message-box"
            ));
            return !candidates.isEmpty();
        });

        boolean sawNextStep = !driver.findElements(By.cssSelector("#ap_password, input[name='password']")).isEmpty();
        boolean sawError = !driver.findElements(By.cssSelector("#auth-error-message-box")).isEmpty();

        Assert.assertTrue(
                sawNextStep || sawError,
                "Expected password step or auth error after clicking Continue on sign-in page."
        );
    }
}
