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
import testcases.mobile.MobileWebTestCase;

import java.time.Duration;
import java.util.List;

public class TestAmazonMobileSigninPage extends MobileWebTestCase {
    private static final Logger logger = LogManager.getLogger(TestAmazonMobileSigninPage.class.getName());
    private static final Duration SIGNIN_WAIT_TIMEOUT = Duration.ofSeconds(25);
    private static final Duration CONSENT_WAIT_TIMEOUT = Duration.ofSeconds(4);
    private static final String TEST_EMAIL = "mobile-web-smoke@example.com";
    private static final By EMAIL_INPUT = By.cssSelector("#ap_email, input[name='email'], input[type='email']");
    private static final By CONTINUE_BUTTON = By.cssSelector("#continue, input[name='continue']");
    private static final By PASSWORD_INPUT = By.cssSelector("#ap_password, input[name='password']");
    private static final By AUTH_ERROR = By.cssSelector("#auth-error-message-box");
    private static final By CAPTCHA_INPUT = By.cssSelector("input[name='cvf_captcha_input'], input#captchacharacters");
    private static final By NEXT_SIGNIN_STATE = By.cssSelector("#ap_password, input[name='password'], #auth-error-message-box, input[name='cvf_captcha_input'], input#captchacharacters");
    private static final By CONSENT_ACCEPT = By.cssSelector("#sp-cc-accept, input[name='accept']");

    protected void acceptConsentIfPresent() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, CONSENT_WAIT_TIMEOUT);
            WebElement acceptButton = shortWait.until(ExpectedConditions.elementToBeClickable(CONSENT_ACCEPT));
            acceptButton.click();
            logger.info("mobile.web.amazon.consent.accepted");
        } catch (TimeoutException ignored) {
            logger.info("mobile.web.amazon.consent.not_present");
        }
    }

    protected void waitForAnySigninState(WebDriverWait wait) {
        wait.until(driver -> hasElement(EMAIL_INPUT) || hasElement(PASSWORD_INPUT) || hasElement(AUTH_ERROR) || hasElement(CAPTCHA_INPUT) || isChallengeUrl());
    }

    @Test(priority = 3, groups = {TestGroups.INTEGRATION, TestGroups.UI_MOBILE_WEB, TestGroups.AMAZON})
    public void shouldProgressAmazonSigninFlowOnAndroidChrome() {
        String signinUrl = getEnvOrDefault("AMAZON_SIGNIN_URL", DEFAULT_AMAZON_SIGNIN_URL);
        logger.info("mobile.web.amazon.signin.open | url=" + signinUrl);
        driver.get(signinUrl);

        WebDriverWait wait = new WebDriverWait(driver, SIGNIN_WAIT_TIMEOUT);
        acceptConsentIfPresent();

        waitForAnySigninState(wait);

        if (hasElement(EMAIL_INPUT)) {
            WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_INPUT));
            emailInput.clear();
            emailInput.sendKeys(TEST_EMAIL);
            logger.info("mobile.web.amazon.signin.email_submitted | value=" + TEST_EMAIL);

            WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(CONTINUE_BUTTON));
            continueButton.click();

            wait.until(driver -> !driver.findElements(NEXT_SIGNIN_STATE).isEmpty());
        }

        boolean sawNextStep = hasElement(PASSWORD_INPUT);
        boolean sawError = hasElement(AUTH_ERROR);
        boolean sawCaptchaOrChallenge = hasElement(CAPTCHA_INPUT) || isChallengeUrl();
        logger.info("mobile.web.amazon.signin.outcome | passwordStep=" + sawNextStep + " | authError=" + sawError + " | captchaOrChallenge=" + sawCaptchaOrChallenge + " | url=" + driver.getCurrentUrl());

        // Smoke test accepts any expected auth progression outcome.
        Assert.assertTrue(sawNextStep || sawError || sawCaptchaOrChallenge, "Expected password step, auth error, or challenge state. url=" + driver.getCurrentUrl());
    }
}
