package testcases.mobile;

import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import testcases.TestCase;
import utilities.ScreenShotHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.time.Duration;

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
        logger.info("mobile.session.create.start | appiumServerUrl=" + appiumServerUrl
                + " | candidateUrls=" + candidateUrls
                + " | maxAttempts=" + maxAttempts
                + " | retryDelaySeconds=" + retryDelaySeconds);

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            for (Iterator<String> iterator = candidateUrls.iterator(); iterator.hasNext(); ) {
                String candidateUrl = iterator.next();
                try {
                    AndroidDriver androidDriver = new AndroidDriver(new URL(candidateUrl), options);
                    androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitSeconds));
                    logger.info("mobile.session.create.success | appiumUrl=" + candidateUrl + " | deviceName=" + options.getDeviceName());
                    return androidDriver;
                } catch (Exception exception) {
                    lastError = exception;
                    String reason = exception.getMessage();
                    if (reason != null
                            && reason.contains("Response code 404")
                            && candidateUrl.endsWith(WD_HUB_SUFFIX)
                            && candidateUrls.size() > 1) {
                        iterator.remove();
                        logger.info("mobile.session.create.fallback | reason=legacy_404 | droppedUrl=" + candidateUrl);
                        continue;
                    }
                    logger.warn("mobile.session.create.retry | attempt=" + attempt + "/" + maxAttempts
                            + " | appiumUrl=" + candidateUrl + " | reason=" + reason);
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
            logger.info("mobile.driver.quit | driverClass=" + driver.getClass().getName());
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
            logger.warn("mobile.env.invalid_int | key=" + key + " | value=" + value + " | usingDefault=" + defaultValue);
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

    @BeforeClass(alwaysRun = true)
    public void beforeClass() throws Exception {
        logger.info("-----------------------Beginning of class----------------------");
        if (!shouldInitializeDriverSession()) {
            logger.info("mobile.driver.init.skip | class=" + getClass().getSimpleName());
            return;
        }

        driver = createDriverSession();
    }

    protected boolean shouldInitializeDriverSession() {
        return true;
    }

    // Subclasses provide their own session bootstrap strategy (mobile web vs mobile app).
    protected WebDriver createDriverSession() throws Exception {
        throw new UnsupportedOperationException("Subclasses must implement createDriverSession()");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        quitDriver();
        logger.info("----------------------Ending of class--------------------------");
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        try {
            logger.info("***** Class: " + result.getTestClass().getName() + " *****");
            logger.info("***** Method: " + result.getName() + "(...) *****");

            String screenShotPath = null;
            if (driver != null) {
                String screenshotName = LocalDateTime.now().format(SCREENSHOT_TS) + "_" + result.getName();
                screenShotPath = ScreenShotHandler.captureScreenShot(driver, screenshotName);
            } else {
                logger.warn("mobile.screenshot.skip | reason=driver_null");
            }

            logResultToExtent(result, screenShotPath);
        } finally {
            super.afterMethod(result);
        }
    }

    private void logResultToExtent(ITestResult result, String screenShotPath) {
        if (test == null) {
            logger.warn("ExtentTest is null in MobileTestCase.logResultToExtent; skipping report logging.");
            return;
        }

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
            logger.warn("mobile.report.screenshot.missing | reason=path_unavailable");
        }
    }

    private String getStackTraceAsString(Throwable exception) {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
