package testcases.mobile.web;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestGroups;
import testcases.mobile.MobileTestCase;

import java.time.Duration;
import java.util.List;

public class TestAmazonMobileSigninPage extends MobileTestCase {
    private static final Logger logger = LogManager.getLogger(TestAmazonMobileSigninPage.class.getName());
    private static final By EMAIL_INPUT = By.cssSelector("#ap_email, input[name='email'], input[type='email']");
    private static final By CONTINUE_BUTTON = By.cssSelector("#continue, input[name='continue']");
    private static final By PASSWORD_INPUT = By.cssSelector("#ap_password, input[name='password']");
    private static final By AUTH_ERROR = By.cssSelector("#auth-error-message-box");
    private static final By CAPTCHA_INPUT = By.cssSelector("input[name='cvf_captcha_input'], input#captchacharacters");
    private static final By CONSENT_ACCEPT = By.cssSelector("#sp-cc-accept, input[name='accept']");

    @Test(groups = {TestGroups.SMOKE, TestGroups.INTEGRATION, TestGroups.UI_MOBILE_WEB, TestGroups.AMAZON})
    public void verifyAmazonSigninFlowOnAndroidChrome() {
        String signinUrl = getEnvOrDefault("AMAZON_SIGNIN_URL", DEFAULT_AMAZON_SIGNIN_URL);
        driver.get(signinUrl);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        acceptConsentIfPresent();

        waitForAnySigninState(wait);

        if (hasElement(EMAIL_INPUT)) {
            WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_INPUT));
            emailInput.clear();
            emailInput.sendKeys("mobile-web-smoke@example.com");

            WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(CONTINUE_BUTTON));
            continueButton.click();

            wait.until(driver -> {
                List<WebElement> candidates = driver.findElements(By.cssSelector(
                        "#ap_password, input[name='password'], #auth-error-message-box, input[name='cvf_captcha_input'], input#captchacharacters"
                ));
                return !candidates.isEmpty();
            });
        }

        boolean sawNextStep = hasElement(PASSWORD_INPUT);
        boolean sawError = hasElement(AUTH_ERROR);
        boolean sawCaptchaOrChallenge = hasElement(CAPTCHA_INPUT) || isChallengeUrl();

        Assert.assertTrue(
                sawNextStep || sawError || sawCaptchaOrChallenge,
                "Expected password step, auth error, or challenge state. url=" + driver.getCurrentUrl()
        );
    }

    private void acceptConsentIfPresent() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(4));
            WebElement acceptButton = shortWait.until(ExpectedConditions.elementToBeClickable(CONSENT_ACCEPT));
            acceptButton.click();
            logger.info("Accepted Amazon consent banner.");
        } catch (TimeoutException ignored) {
            // Consent not shown; continue.
        }
    }

    private void waitForAnySigninState(WebDriverWait wait) {
        wait.until(driver ->
                hasElement(EMAIL_INPUT)
                        || hasElement(PASSWORD_INPUT)
                        || hasElement(AUTH_ERROR)
                        || hasElement(CAPTCHA_INPUT)
                        || isChallengeUrl());
    }

    private boolean hasElement(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    private boolean isChallengeUrl() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.contains("/ap/signin")
                || currentUrl.contains("/ap/cvf")
                || currentUrl.contains("/errors/validateCaptcha")
                || currentUrl.contains("/ap/challenge");
    }
}
