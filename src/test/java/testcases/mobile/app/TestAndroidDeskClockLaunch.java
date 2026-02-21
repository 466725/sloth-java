package testcases.mobile.app;

import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import testcases.TestGroups;
import testcases.mobile.MobileAppTestCase;

import java.util.ArrayList;
import java.util.List;

public class TestAndroidDeskClockLaunch extends MobileAppTestCase {
    private static final Logger logger = LogManager.getLogger(TestAndroidDeskClockLaunch.class.getName());

    @Test(groups = {TestGroups.INTEGRATION, TestGroups.UI_MOBILE_APP})
    public void verifyAdbDevicesCommandWorks() {
        String mobileContainerName = System.getenv("MOBILE_CONTAINER_NAME");
        if (mobileContainerName == null || mobileContainerName.isBlank()) {
            throw new SkipException(
                    "Skipping docker adb verification because MOBILE_CONTAINER_NAME is not set. "
                            + "Set MOBILE_CONTAINER_NAME (for example: android-emulator-google) when running docker emulator profile."
            );
        }

        logger.info("mobile.app.test.adb_verify.start | container=" + mobileContainerName);
        boolean adbDeviceDetected = tryVerifyViaDockerAdb();
        String connectivityStatus = System.getProperty(ANDROID_CONNECTIVITY_STATUS_PROPERTY, CONNECTIVITY_STATUS_NOT_READY);
        if (CONNECTIVITY_STATUS_DOCKER_UNAVAILABLE.equals(connectivityStatus)) {
            throw new SkipException(
                    "Skipping docker adb verification because Docker CLI is unavailable in this test runtime."
            );
        }

        logger.info("mobile.app.test.adb_verify.result | detected=" + adbDeviceDetected + " | status=" + connectivityStatus);
        Assert.assertTrue(
                adbDeviceDetected,
                "Expected docker adb verification to detect at least one connected Android device. "
                        + "Ensure the emulator container is running and reachable."
        );
    }

    @Test(groups = {TestGroups.INTEGRATION, TestGroups.UI_MOBILE_APP})
    public void verifyDeskClockCanBeLaunched() {
        Assert.assertTrue(driver instanceof AndroidDriver, "Expected AndroidDriver session.");
        AndroidDriver androidDriver = (AndroidDriver) driver;
        List<String> deskClockPackages = getDeskClockPackages();

        List<String> launchErrors = new ArrayList<>();
        boolean launched = false;

        for (String appPackage : deskClockPackages) {
            logger.info("mobile.app.test.deskclock.launch.attempt | package=" + appPackage);
            try {
                androidDriver.activateApp(appPackage);
                String currentPackage = androidDriver.getCurrentPackage();
                if (appPackage.equals(currentPackage)) {
                    launched = true;
                    logger.info("mobile.app.test.deskclock.launch.success | package=" + appPackage);
                    break;
                }
                launchErrors.add("Activated " + appPackage + " but current package is " + currentPackage);
                logger.info("mobile.app.test.deskclock.launch.mismatch | expected=" + appPackage + " | actual=" + currentPackage);
            } catch (Exception exception) {
                launchErrors.add(appPackage + ": " + exception.getMessage());
                logger.info("mobile.app.test.deskclock.launch.error | package=" + appPackage + " | reason=" + exception.getMessage());
            }
        }

        Assert.assertTrue(launched, "Failed to launch Android Desk Clock. Tried packages: " + deskClockPackages + ". Details: " + launchErrors);
    }
}
