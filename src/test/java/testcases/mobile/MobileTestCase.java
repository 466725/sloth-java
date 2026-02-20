package testcases.mobile;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import testcases.TestCase;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.Duration;
import java.util.Map;

/**
 * Shared mobile test base for Android/Appium session creation helpers.
 */
public abstract class MobileTestCase extends TestCase {
    protected static final Logger logger = LogManager.getLogger(MobileTestCase.class.getName());
    protected static final String DEFAULT_APPIUM_URL = "http://127.0.0.1:4723";

    protected WebDriver driver;

    protected AndroidDriver startAndroidSession(UiAutomator2Options options, int implicitWaitSeconds) throws Exception {
        String appiumServerUrl = getEnvOrDefault("APPIUM_SERVER_URL", DEFAULT_APPIUM_URL);
        int maxAttempts = getIntEnvOrDefault("ANDROID_SESSION_RETRY_COUNT", 8);
        int retryDelaySeconds = getIntEnvOrDefault("ANDROID_SESSION_RETRY_DELAY_SECONDS", 10);
        List<String> candidateUrls = buildCandidateAppiumUrls(appiumServerUrl);
        Exception lastError = null;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            for (String candidateUrl : candidateUrls) {
                try {
                    AndroidDriver androidDriver = new AndroidDriver(new URL(candidateUrl), options);
                    androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitSeconds));
                    logger.info("Android session started. appiumUrl=" + candidateUrl + ", deviceName=" + options.getDeviceName());
                    return androidDriver;
                } catch (Exception exception) {
                    lastError = exception;
                    logger.warn("Failed to start Android session. attempt=" + attempt + "/" + maxAttempts
                            + ", appiumUrl=" + candidateUrl + ", reason=" + exception.getMessage());
                }
            }
            if (attempt < maxAttempts) {
                Thread.sleep(Duration.ofSeconds(retryDelaySeconds).toMillis());
            }
        }

        throw new IllegalStateException(
                "Unable to start Android session after " + maxAttempts + " attempts. Appium URLs tried: " + candidateUrls,
                lastError
        );
    }

    protected void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    protected static String getEnvOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value;
    }

    protected static int getIntEnvOrDefault(String key, int defaultValue) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException exception) {
            logger.warn("Invalid integer for env '" + key + "': " + value + ". Using default: " + defaultValue);
            return defaultValue;
        }
    }

    protected static List<String> buildCandidateAppiumUrls(String appiumServerUrl) {
        String normalized = appiumServerUrl.endsWith("/")
                ? appiumServerUrl.substring(0, appiumServerUrl.length() - 1)
                : appiumServerUrl;

        List<String> urls = new ArrayList<>();
        urls.add(normalized);

        if (!normalized.endsWith("/wd/hub")) {
            urls.add(normalized + "/wd/hub");
        } else {
            urls.add(normalized.substring(0, normalized.length() - "/wd/hub".length()));
        }
        return urls;
    }


    protected static final String DEFAULT_AMAZON_SIGNIN_URL = "https://www.amazon.com/ap/signin";

    // Initializes a mobile web session (Chrome on Android) via Appium.
    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        String runMode = getEnvOrDefault("MOBILE_WEB_RUN_MODE", "appium").trim().toLowerCase();
        String deviceName = getEnvOrDefault("ANDROID_DEVICE_NAME", "Android");
        int implicitWaitSeconds = getIntEnvOrDefault("ANDROID_IMPLICIT_WAIT_SECONDS", 2);

        if ("selenium".equals(runMode)) {
            String seleniumRemoteUrl = getEnvOrDefault("SELENIUM_REMOTE_URL", "http://127.0.0.1:4444/wd/hub");
            String emulatedDeviceName = getEnvOrDefault("MOBILE_EMULATION_DEVICE", "Pixel 7");
            boolean headless = Boolean.parseBoolean(getEnvOrDefault("HEADLESS", "true"));

            ChromeOptions chromeOptions = new ChromeOptions();
            Map<String, Object> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", emulatedDeviceName);
            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
            if (headless) {
                chromeOptions.addArguments("--headless=new");
            }

            driver = new RemoteWebDriver(new URL(seleniumRemoteUrl), chromeOptions);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitSeconds));
            logger.info("Selenium mobile-emulated web session initialized. seleniumUrl=" + seleniumRemoteUrl
                    + ", emulatedDevice=" + emulatedDeviceName);
            return;
        }

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName(deviceName);
        options.setCapability("browserName", "Chrome");

        driver = startAndroidSession(options, implicitWaitSeconds);
        logger.info("Android mobile web session initialized.");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        quitDriver();
    }
}
