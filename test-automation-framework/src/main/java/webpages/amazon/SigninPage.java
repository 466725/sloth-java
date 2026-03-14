package webpages.amazon;

import config.PropertiesFileReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.SeleniumWrapper;
import webpages.BaseWebPage;

import java.time.Duration;

public class SigninPage extends BaseWebPage {
    protected final static Logger logger = LogManager.getLogger(SigninPage.class.getName());
    private static final Duration SIGNIN_WAIT_TIMEOUT = Duration.ofSeconds(25);
    private static final Duration SHORT_WAIT_TIMEOUT = Duration.ofSeconds(4);
    private static final By EMAIL_INPUT = By.cssSelector("#ap_email, input[name='email'], input[type='email']");
    private static final By CONTINUE_BUTTON = By.cssSelector("#continue, input[name='continue']");
    private static final By PASSWORD_INPUT = By.cssSelector("#ap_password, input[name='password']");
    private static final By SIGNIN_SUBMIT_BUTTON = By.cssSelector("#signInSubmit, input#signInSubmit");
    private static final By AUTH_ERROR = By.cssSelector("#auth-error-message-box");
    private static final By EMAIL_INVALID_ERROR = By.cssSelector("#auth-email-invalid-email-alert");
    private static final By PASSWORD_MISSING_ERROR = By.cssSelector("#auth-password-missing-alert");
    private static final By CAPTCHA_INPUT = By.cssSelector("input[name='cvf_captcha_input'], input#captchacharacters");
    private static final By CONSENT_ACCEPT = By.cssSelector("#sp-cc-accept, input[name='accept']");
    private static final By NEXT_SIGNIN_STATE = By.cssSelector(
            "#ap_password, input[name='password'], #auth-error-message-box, " +
                    "input[name='cvf_captcha_input'], input#captchacharacters, " +
                    "#auth-email-invalid-email-alert, #auth-password-missing-alert"
    );
    private boolean signinBlocked = false;

    @FindBy(id = "ab-registration-ingress-link")
    public WebElement createAccount;
    
    public SigninPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public BaseWebPage gotoRegisterPage() {
        SeleniumWrapper.explicitWaitClickable(driver, createAccount, PropertiesFileReader.getTimeout());
        createAccount.click();
        return new RegisterPage(driver);
    }

    public SigninPage attemptSignin(String email, String password) {
        WebDriverWait wait = new WebDriverWait(driver, SIGNIN_WAIT_TIMEOUT);
        acceptConsentIfPresent();
        waitForAnySigninState(wait);

        if (hasElement(EMAIL_INPUT)) {
            WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_INPUT));
            emailInput.clear();
            emailInput.sendKeys(email);
            wait.until(ExpectedConditions.elementToBeClickable(CONTINUE_BUTTON)).click();
            wait.until(d -> hasElement(NEXT_SIGNIN_STATE) || isChallengeUrl());
        }

        if (hasElement(PASSWORD_INPUT)) {
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_INPUT));
            passwordInput.clear();
            passwordInput.sendKeys(password);
            wait.until(ExpectedConditions.elementToBeClickable(SIGNIN_SUBMIT_BUTTON)).click();
        }

        return this;
    }

    public boolean hasSigninError() {
        return signinBlocked
                || isVisible(AUTH_ERROR, 5)
                || isVisible(EMAIL_INVALID_ERROR, 2)
                || isVisible(PASSWORD_MISSING_ERROR, 2)
                || isVisible(CAPTCHA_INPUT, 2)
                || isRobotCheckPage()
                || isChallengeUrl();
    }

    private void acceptConsentIfPresent() {
        try {
            new WebDriverWait(driver, SHORT_WAIT_TIMEOUT).until(
                    ExpectedConditions.elementToBeClickable(CONSENT_ACCEPT)
            ).click();
            logger.info("amazon.signin.consent.accepted");
        } catch (Exception ignored) {
            logger.info("amazon.signin.consent.not_present");
        }
    }

    private void waitForAnySigninState(WebDriverWait wait) {
        try {
            wait.until(d -> hasElement(EMAIL_INPUT)
                    || hasElement(PASSWORD_INPUT)
                    || hasElement(AUTH_ERROR)
                    || hasElement(CAPTCHA_INPUT)
                    || isRobotCheckPage()
                    || isChallengeUrl());
        } catch (TimeoutException e) {
            signinBlocked = true;
            logger.warn("amazon.signin.state_timeout | url=" + driver.getCurrentUrl() + " | title=" + driver.getTitle());
        }
    }

    private boolean hasElement(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    private boolean isChallengeUrl() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl != null
                && (currentUrl.contains("/ap/cvf/")
                || currentUrl.contains("challenge")
                || currentUrl.contains("captcha")
                || currentUrl.contains("/errors/validateCaptcha")
                || currentUrl.contains("/sorry/"));
    }

    private boolean isRobotCheckPage() {
        String currentUrl = String.valueOf(driver.getCurrentUrl()).toLowerCase();
        String title = String.valueOf(driver.getTitle()).toLowerCase();
        String pageSource = String.valueOf(driver.getPageSource()).toLowerCase();
        return title.contains("robot check")
                || title.contains("sorry")
                || currentUrl.contains("validatecaptcha")
                || currentUrl.contains("/sorry/")
                || pageSource.contains("enter the characters you see below")
                || pageSource.contains("to discuss automated access to amazon data");
    }

    private boolean isVisible(By locator, int timeoutSeconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}
