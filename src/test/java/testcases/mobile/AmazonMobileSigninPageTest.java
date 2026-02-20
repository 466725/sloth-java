package testcases.mobile;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testcases.TestCase;
import testcases.TestGroups;

import java.net.URL;
import java.time.Duration;
import java.util.List;

public class AmazonMobileSigninPageTest extends TestCase {
    private static final Logger logger = LogManager.getLogger(AmazonMobileSigninPageTest.class.getName());
    private static final String DEFAULT_APPIUM_URL = "http://127.0.0.1:4723";
    private static final String DEFAULT_AMAZON_SIGNIN_URL = "https://www.amazon.com/ap/signin";
    private AndroidDriver driver;

    // Initializes a mobile web driver session (Chrome on Android) through Appium.
    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        String appiumServerUrl = getEnvOrDefault("APPIUM_SERVER_URL", DEFAULT_APPIUM_URL);
        String deviceName = getEnvOrDefault("ANDROID_DEVICE_NAME", "Android");

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName(deviceName);
        options.setCapability("browserName", "Chrome");

        driver = new AndroidDriver(new URL(appiumServerUrl), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        logger.info("Android mobile web session started. appiumUrl=" + appiumServerUrl + ", deviceName=" + deviceName);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

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

    private static String getEnvOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value;
    }
}
