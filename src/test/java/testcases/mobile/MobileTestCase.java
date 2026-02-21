package testcases.mobile;

import com.relevantcodes.extentreports.LogStatus;
import config.PropertiesFileReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import testcases.TestCase;
import utilities.ScreenShotHandler;
import webpages.BaseWebPage;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
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
    private static final String WD_HUB_SUFFIX = "/wd/hub";
    private static final DateTimeFormatter SCREENSHOT_TS = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    protected WebDriver driver;

    protected AndroidDriver startAndroidSession(UiAutomator2Options options, int implicitWaitSeconds) throws Exception {
        int maxAttempts = getIntEnvOrDefault("ANDROID_SESSION_RETRY_COUNT", 16);
        int retryDelaySeconds = getIntEnvOrDefault("ANDROID_SESSION_RETRY_DELAY_SECONDS", 12);
        return startAndroidSession(options, implicitWaitSeconds, maxAttempts, retryDelaySeconds);
    }

    protected AndroidDriver startAndroidSession(UiAutomator2Options options,
                                                int implicitWaitSeconds,
                                                int maxAttempts,
                                                int retryDelaySeconds) throws Exception {
        String appiumServerUrl = getEnvOrDefault("APPIUM_SERVER_URL", DEFAULT_APPIUM_URL);
        List<String> candidateUrls = buildCandidateAppiumUrls(appiumServerUrl);
        Exception lastError = null;
        applyDefaultAndroidTimeoutCapabilities(options);

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            for (Iterator<String> iterator = candidateUrls.iterator(); iterator.hasNext(); ) {
                String candidateUrl = iterator.next();
                try {
                    AndroidDriver androidDriver = new AndroidDriver(new URL(candidateUrl), options);
                    androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitSeconds));
                    logger.info("Android session started. appiumUrl=" + candidateUrl + ", deviceName=" + options.getDeviceName());
                    return androidDriver;
                } catch (Exception exception) {
                    lastError = exception;
                    String reason = exception.getMessage();
                    if (reason != null
                            && reason.contains("Response code 404")
                            && candidateUrl.endsWith(WD_HUB_SUFFIX)
                            && candidateUrls.size() > 1) {
                        iterator.remove();
                        logger.info("Dropping legacy Appium 1 URL after 404: " + candidateUrl);
                        continue;
                    }
                    logger.warn("Failed to start Android session. attempt=" + attempt + "/" + maxAttempts
                            + ", appiumUrl=" + candidateUrl + ", reason=" + reason);
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

    protected static void applyDefaultAndroidTimeoutCapabilities(UiAutomator2Options options) {
        int adbExecTimeoutMs = getIntEnvOrDefault("ANDROID_ADB_EXEC_TIMEOUT_MS", 120000);
        int deviceReadyTimeoutSeconds = getIntEnvOrDefault("ANDROID_DEVICE_READY_TIMEOUT_SECONDS", 180);
        int uia2ServerLaunchTimeoutMs = getIntEnvOrDefault("ANDROID_UIA2_SERVER_LAUNCH_TIMEOUT_MS", 120000);
        options.setCapability("appium:adbExecTimeout", adbExecTimeoutMs);
        options.setCapability("appium:androidDeviceReadyTimeout", deviceReadyTimeoutSeconds);
        options.setCapability("appium:uiautomator2ServerLaunchTimeout", uia2ServerLaunchTimeoutMs);
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

        if (!normalized.endsWith(WD_HUB_SUFFIX)) {
            urls.add(normalized + WD_HUB_SUFFIX);
        } else {
            urls.add(normalized.substring(0, normalized.length() - WD_HUB_SUFFIX.length()));
        }
        return urls;
    }


    protected static final String DEFAULT_AMAZON_SIGNIN_URL = "https://www.amazon.com/ap/signin";

    // Initializes a mobile web session (Chrome on Android) via Appium.
    @BeforeClass(alwaysRun = true)
    public void beforeClass() throws Exception {
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
        applyDefaultAndroidTimeoutCapabilities(options);
        options.setCapability("browserName", "Chrome");

        driver = startAndroidSession(options, implicitWaitSeconds);
        logger.info("Android mobile web session initialized.");
        logger.info("-----------------------Beginning of class----------------------");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        quitDriver();
        logger.info("----------------------Ending of class--------------------------");
    }

    /**
     * Prepare per BeforeMethod annotation.
     */
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        logger.info("----------------------Beginning of method--------------------------");
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        logger.info("***** Class: " + result.getTestClass().getName() + " *****");
        logger.info("***** Method: " + result.getName() + "(...) *****");

        String screenShotPath = null;
        if (driver != null) {
            String screenshotName = LocalDateTime.now().format(SCREENSHOT_TS) + "_" + result.getName();
            screenShotPath = ScreenShotHandler.captureScreenShot(driver, screenshotName);
        } else {
            logger.warn("Driver is null in @AfterMethod; skipping screenshot.");
        }

        logResultToExtent(result, screenShotPath);
        super.afterMethod(result);
        logger.info("-----------------------Ending of method------------------------");
    }

    private void logResultToExtent(ITestResult result, String screenShotPath) {
        String className = result.getTestClass().getName();
        String methodName = result.getMethod().getMethodName();
        Throwable exception = result.getThrowable();
        String logDetails = String.format("%s:  %s", className, methodName);

        LogStatus status = switch (result.getStatus()) {
            case ITestResult.SUCCESS -> LogStatus.PASS;
            case ITestResult.FAILURE -> LogStatus.FAIL;
            case ITestResult.SKIP -> LogStatus.SKIP;
            default -> LogStatus.FATAL;
        };

        test.log(status, logDetails);
        if (status != LogStatus.PASS && exception != null) {
            test.log(status, getStackTraceAsString(exception));
        }

        if (screenShotPath != null) {
            test.log(status, test.addScreenCapture(screenShotPath));
        } else {
            logger.warn("No screenshot path available to attach to report.");
        }
    }

    private String getStackTraceAsString(Throwable exception) {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
