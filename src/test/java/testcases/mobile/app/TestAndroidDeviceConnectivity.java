package testcases.mobile.app;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestGroups;
import testcases.mobile.MobileTestCase;
import testutils.CommandUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;

public class TestAndroidDeviceConnectivity extends MobileTestCase {
    private final static Logger logger = LogManager.getLogger(TestAndroidDeviceConnectivity.class.getName());

    @Test(groups = {TestGroups.INTEGRATION, TestGroups.UI_MOBILE})
    public void verifyAppiumAndAndroidDeviceConnectivity() throws Exception {
        String appiumServerUrl = getEnvOrDefault("APPIUM_SERVER_URL", DEFAULT_APPIUM_URL);
        assertAppiumStatusReachable(appiumServerUrl);

        if (tryVerifyViaDockerAdb()) {
            return;
        }

        verifyByStartingAndroidChromeSession();
    }

    private void assertAppiumStatusReachable(String appiumServerUrl) throws Exception {
        String normalized = appiumServerUrl.endsWith("/") ? appiumServerUrl.substring(0, appiumServerUrl.length() - 1) : appiumServerUrl;
        String statusUrl = normalized + "/status";
        long timeoutMillis = Duration.ofSeconds(getIntEnvOrDefault("APPIUM_STATUS_TIMEOUT_SECONDS", 120)).toMillis();
        long deadline = System.currentTimeMillis() + timeoutMillis;
        Exception lastError = null;

        while (System.currentTimeMillis() < deadline) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(statusUrl).openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                int statusCode = connection.getResponseCode();
                if (statusCode == 200) {
                    return;
                }
                lastError = new IllegalStateException("Received status code " + statusCode);
            } catch (IOException exception) {
                lastError = exception;
            }
            Thread.sleep(2000);
        }

        throw new IllegalStateException("Appium status endpoint is not reachable at " + statusUrl, lastError);
    }

    private boolean tryVerifyViaDockerAdb() {
        String mobileContainerName = getEnvOrDefault("MOBILE_CONTAINER_NAME", "android-emulator");
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
            Assert.assertTrue(
                    adbOutput.toLowerCase().contains("list of devices attached"),
                    "Unexpected adb output from docker container '" + mobileContainerName + "': " + adbOutput
            );
            return true;
        } catch (Exception exception) {
            logger.info("Docker/adb verification path unavailable. Falling back to AndroidDriver connectivity probe. Reason: "
                    + exception.getMessage());
            return false;
        }
    }

    private void verifyByStartingAndroidChromeSession() throws Exception {
        String deviceName = getEnvOrDefault("ANDROID_DEVICE_NAME", "Android");
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName(deviceName);
        options.setCapability("browserName", "Chrome");

        AndroidDriver probeDriver = null;
        try {
            probeDriver = startAndroidSession(options, 2);
            Assert.assertNotNull(probeDriver.getSessionId(), "Expected Appium to create an Android session.");
        } finally {
            if (probeDriver != null) {
                probeDriver.quit();
            }
        }
    }
}
