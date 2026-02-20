package testcases.mobile.app;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testcases.TestGroups;
import testcases.mobile.MobileTestCase;
import testutils.CommandUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;

public class TestAndroidDeviceConnectivity extends MobileTestCase {
    private final static Logger logger = LogManager.getLogger(TestAndroidDeviceConnectivity.class.getName());
    public static final String ANDROID_CONNECTIVITY_READY_PROPERTY = "mobile.android.connectivity.ready";

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        // Override MobileTestCase web/app bootstrap: this probe controls its own session creation.
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        // No shared class-level driver is created in this test class.
    }

    @Test(groups = {TestGroups.INTEGRATION, TestGroups.UI_MOBILE_APP})
    public void verifyAppiumAndAndroidDeviceConnectivity() throws Exception {
        System.setProperty(ANDROID_CONNECTIVITY_READY_PROPERTY, "false");
        String appiumServerUrl = getEnvOrDefault("APPIUM_SERVER_URL", DEFAULT_APPIUM_URL);
        assertAppiumStatusReachable(appiumServerUrl);

        if (tryVerifyViaDockerAdb()) {
            System.setProperty(ANDROID_CONNECTIVITY_READY_PROPERTY, "true");
            return;
        }

        verifyByStartingAndroidSession();
        System.setProperty(ANDROID_CONNECTIVITY_READY_PROPERTY, "true");
    }

    private void assertAppiumStatusReachable(String appiumServerUrl) throws Exception {
        long timeoutMillis = Duration.ofSeconds(getIntEnvOrDefault("APPIUM_STATUS_TIMEOUT_SECONDS", 120)).toMillis();
        long deadline = System.currentTimeMillis() + timeoutMillis;
        Exception lastError = null;
        List<String> statusUrls = buildStatusUrls(appiumServerUrl);

        while (System.currentTimeMillis() < deadline) {
            for (String statusUrl : statusUrls) {
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(statusUrl).openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    int statusCode = connection.getResponseCode();
                    if (statusCode == 200) {
                        return;
                    }
                    lastError = new IllegalStateException("Received status code " + statusCode + " from " + statusUrl);
                } catch (IOException exception) {
                    lastError = exception;
                }
            }
            Thread.sleep(2000);
        }

        throw new IllegalStateException("Appium status endpoint is not reachable. URLs tried: " + statusUrls, lastError);
    }

    private boolean tryVerifyViaDockerAdb() {
        String mobileContainerName = getEnvOrDefault("MOBILE_CONTAINER_NAME", "android-emulator");
        long timeoutMillis = Duration.ofSeconds(getIntEnvOrDefault("ANDROID_ADB_READY_TIMEOUT_SECONDS", 240)).toMillis();
        long deadline = System.currentTimeMillis() + timeoutMillis;
        Exception lastError = null;

        while (System.currentTimeMillis() < deadline) {
            try {
                String dockerStatus = CommandUtils.runCommand(
                        new String[]{"docker", "inspect", "-f", "{{.State.Running}}", mobileContainerName},
                        20
                ).trim().toLowerCase();
                Assert.assertEquals(
                        dockerStatus,
                        "true",
                        "Docker container '" + mobileContainerName + "' is not running. inspect output: " + dockerStatus
                );

                String adbOutput = CommandUtils.runCommand(
                        new String[]{"docker", "exec", mobileContainerName, "adb", "devices"},
                        20
                );
                if (hasConnectedAdbDevice(adbOutput)) {
                    return true;
                }
                lastError = new IllegalStateException(
                        "adb did not report any connected devices yet. output: " + adbOutput
                );
            } catch (Exception exception) {
                lastError = exception;
                if (isDockerCliUnavailable(exception)) {
                    logger.info("Docker CLI is unavailable in this test runtime; skipping docker/adb verification path.");
                    return false;
                }
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        if (lastError != null) {
            logger.info("Docker/adb verification path unavailable. Falling back to AndroidDriver connectivity probe. Reason: "
                    + lastError.getMessage());
        } else {
            logger.info("Docker/adb verification path unavailable. Falling back to AndroidDriver connectivity probe.");
        }
        return false;
    }

    private boolean isDockerCliUnavailable(Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            return false;
        }
        String normalized = message.toLowerCase();
        return normalized.contains("cannot run program \"docker\"")
                || normalized.contains("no such file or directory")
                || normalized.contains("createprocess error=2");
    }

    private boolean hasConnectedAdbDevice(String adbOutput) {
        String[] lines = adbOutput.split("\\R");
        for (String rawLine : lines) {
            String line = rawLine.trim();
            if (line.isEmpty()) {
                continue;
            }
            if (line.toLowerCase().startsWith("list of devices attached")) {
                continue;
            }
            if (line.contains("\tdevice")) {
                return true;
            }
        }
        return false;
    }

    private void verifyByStartingAndroidSession() throws Exception {
        String deviceName = getEnvOrDefault("ANDROID_DEVICE_NAME", "Android");
        int maxAttempts = getIntEnvOrDefault("ANDROID_CONNECTIVITY_SESSION_RETRY_COUNT", 4);
        int retryDelaySeconds = getIntEnvOrDefault("ANDROID_CONNECTIVITY_SESSION_RETRY_DELAY_SECONDS", 8);
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName(deviceName)
                // Use Android Settings app for connectivity probing to avoid ChromeDriver dependency mismatch.
                .setAppPackage("com.android.settings")
                .setAppActivity(".Settings");
        applyDefaultAndroidTimeoutCapabilities(options);

        AndroidDriver probeDriver = null;
        try {
            probeDriver = startAndroidSession(options, 2, maxAttempts, retryDelaySeconds);
            Assert.assertNotNull(probeDriver.getSessionId(), "Expected Appium to create an Android session.");
        } finally {
            if (probeDriver != null) {
                probeDriver.quit();
            }
        }
    }

    private List<String> buildStatusUrls(String appiumServerUrl) {
        String normalized = appiumServerUrl.endsWith("/") ? appiumServerUrl.substring(0, appiumServerUrl.length() - 1) : appiumServerUrl;
        List<String> statusUrls = new ArrayList<>();
        statusUrls.add(normalized + "/status");

        if (normalized.endsWith("/wd/hub")) {
            String root = normalized.substring(0, normalized.length() - "/wd/hub".length());
            statusUrls.add(root + "/status");
        } else {
            statusUrls.add(normalized + "/wd/hub/status");
        }
        return statusUrls;
    }
}
