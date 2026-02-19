package testcases.mobile;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestGroups;
import testutils.CommandUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;

public class AndroidDriverFactory {
    private final static Logger logger = LogManager.getLogger(AndroidDriverFactory.class.getName());

    // Builds Android drivers and verifies local Appium/ADB prerequisites.
    public static AndroidDriver newDriver() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setDeviceName("Android Emulator")
                .setAutomationName("UiAutomator2")
                .setAppPackage("com.example.android")
                .setAppActivity(".MainActivity");

        AndroidDriver driver = new AndroidDriver(
                new URL("http://127.0.0.1:4723"),
                options
        );
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    @Test(groups = {TestGroups.INTEGRATION, TestGroups.UI_MOBILE})
    public void verifyLocalAppiumContainerAndAdbConnectivity() throws Exception {
        // docker inspect -f "{{.State.Running}}" appium-container
        String dockerStatus = CommandUtils.runCommand(
                new String[]{"docker", "inspect", "-f", "{{.State.Running}}", "appium-container"},
                20
        ).trim().toLowerCase();
        Assert.assertEquals(
                dockerStatus,
                "true",
                "Docker container 'appium-container' is not running. inspect output: " + dockerStatus
        );

        HttpURLConnection connection = (HttpURLConnection) new URL("http://127.0.0.1:4723/status").openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        int statusCode = connection.getResponseCode();
        Assert.assertEquals(statusCode, 200, "Appium status endpoint is not reachable at http://127.0.0.1:4723/status");

        String adbOutput;
        try {
            String adbExecutable = CommandUtils.resolveAdbExecutable();
            // adb devices
            adbOutput = CommandUtils.runCommand(new String[]{adbExecutable, "devices"}, 20);
        } catch (Exception hostAdbError) {
            // docker exec appium-container adb devices
            adbOutput = CommandUtils.runCommand(new String[]{"docker", "exec", "appium-container", "adb", "devices"}, 20);
        }
        Assert.assertTrue(
                adbOutput.toLowerCase().contains("list of devices attached"),
                "Unexpected adb output: " + adbOutput
        );
    }
}
