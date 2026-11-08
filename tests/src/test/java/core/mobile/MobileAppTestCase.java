package core.mobile;

import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utils.CommandUtils;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class MobileAppTestCase extends MobileTestCase {
    public static final String ANDROID_CONNECTIVITY_READY_PROPERTY = "mobile.android.connectivity.ready";
    public static final String ANDROID_CONNECTIVITY_STATUS_PROPERTY = "mobile.android.connectivity.status";
    public static final String CONNECTIVITY_STATUS_READY = "ready";
    public static final String CONNECTIVITY_STATUS_DOCKER_UNAVAILABLE = "docker_unavailable";
    public static final String CONNECTIVITY_STATUS_NOT_READY = "not_ready";
    private static final int DOCKER_COMMAND_TIMEOUT_SECONDS = 20;
    private static final long ADB_POLL_INTERVAL_MILLIS = 5000;
    protected static final List<String> DEFAULT_DESKCLOCK_PACKAGES = Arrays.asList("com.google.android.deskclock", "com.android.deskclock");
    private static final Logger logger = LogManager.getLogger(MobileAppTestCase.class.getName());

    protected List<String> getDeskClockPackages() {
        String configuredPackages = getEnvOrDefault("ANDROID_DESKCLOCK_PACKAGES", "");
        if (configuredPackages.isBlank()) {
            return DEFAULT_DESKCLOCK_PACKAGES;
        }

        List<String> parsed = Arrays.stream(configuredPackages.split(",")).map(value -> value == null ? "" : value.trim()).filter(value -> !value.isBlank()).collect(Collectors.toList());

        if (parsed.isEmpty()) {
            return DEFAULT_DESKCLOCK_PACKAGES;
        }
        return parsed;
    }

    @Override
    protected WebDriver createDriverSession() throws Exception {
        String deviceName = getEnvOrDefault("ANDROID_DEVICE_NAME", "Android");
        int implicitWaitSeconds = getIntEnvOrDefault("ANDROID_IMPLICIT_WAIT_SECONDS", 5);

        UiAutomator2Options options = new UiAutomator2Options().setPlatformName("Android").setAutomationName("UiAutomator2").setDeviceName(deviceName);

        WebDriver appiumDriver = startAndroidSession(options, implicitWaitSeconds);
        logger.info("mobile.app.session.init | deviceName=" + deviceName);
        return appiumDriver;
    }

    protected boolean tryVerifyViaDockerAdb() {
        String mobileContainerName = getEnvOrDefault("MOBILE_CONTAINER_NAME", "android-emulator");
        long timeoutMillis = Duration.ofSeconds(getIntEnvOrDefault("ANDROID_ADB_READY_TIMEOUT_SECONDS", 240)).toMillis();
        long deadline = System.currentTimeMillis() + timeoutMillis;
        Exception lastError = null;
        setConnectivityStatus(CONNECTIVITY_STATUS_NOT_READY);
        logger.info("mobile.app.adb.verify.start | container=" + mobileContainerName + " | timeoutSeconds=" + (timeoutMillis / 1000));

        while (System.currentTimeMillis() < deadline) {
            try {
                String dockerStatus = CommandUtils.runCommand(
                        new String[]{"docker", "inspect", "-f", "{{.State.Running}}", mobileContainerName},
                        DOCKER_COMMAND_TIMEOUT_SECONDS
                ).trim().toLowerCase();
                Assert.assertEquals(dockerStatus, "true", "Docker container '" + mobileContainerName + "' is not running. inspect output: " + dockerStatus);

                String adbOutput = CommandUtils.runCommand(
                        new String[]{"docker", "exec", mobileContainerName, "adb", "devices"},
                        DOCKER_COMMAND_TIMEOUT_SECONDS
                );
                if (hasConnectedAdbDevice(adbOutput)) {
                    setConnectivityStatus(CONNECTIVITY_STATUS_READY);
                    logger.info("mobile.app.adb.verify.success | container=" + mobileContainerName);
                    return true;
                }
                lastError = new IllegalStateException("adb did not report any connected devices yet. output: " + adbOutput);
            } catch (Exception exception) {
                lastError = exception;
                if (isDockerCliUnavailable(exception)) {
                    setConnectivityStatus(CONNECTIVITY_STATUS_DOCKER_UNAVAILABLE);
                    logger.info("mobile.app.adb.verify.skip | reason=docker_cli_unavailable");
                    return false;
                }
            }

            try {
                Thread.sleep(ADB_POLL_INTERVAL_MILLIS);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        if (lastError != null) {
            logger.info("mobile.app.adb.verify.fallback | reason=" + lastError.getMessage());
        } else {
            logger.info("mobile.app.adb.verify.fallback | reason=unknown");
        }

        setConnectivityStatus(CONNECTIVITY_STATUS_NOT_READY);
        return false;
    }

    protected boolean isDockerCliUnavailable(Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            return false;
        }
        String normalized = message.toLowerCase();
        return normalized.contains("cannot run program \"docker\"") || normalized.contains("no such file or directory") || normalized.contains("createprocess error=2");
    }

    protected boolean hasConnectedAdbDevice(String adbOutput) {
        String[] lines = adbOutput.split("\\R");
        for (String rawLine : lines) {
            String line = rawLine.trim();
            if (line.isEmpty()) {
                continue;
            }
            if (line.toLowerCase().startsWith("list of devices attached")) {
                continue;
            }
            // Accept both tab and multi-space delimiters from adb output.
            String[] tokens = line.split("\\s+");
            if (tokens.length >= 2 && "device".equalsIgnoreCase(tokens[tokens.length - 1])) {
                return true;
            }
        }
        return false;
    }

    private void setConnectivityStatus(String status) {
        System.setProperty(ANDROID_CONNECTIVITY_STATUS_PROPERTY, status);
        System.setProperty(ANDROID_CONNECTIVITY_READY_PROPERTY, String.valueOf(CONNECTIVITY_STATUS_READY.equals(status)));
    }
}
