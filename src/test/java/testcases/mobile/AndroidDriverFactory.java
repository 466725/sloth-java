package testcases.mobile;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestGroups;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AndroidDriverFactory {
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
        String dockerStatus = runCommand(
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
            String adbExecutable = resolveAdbExecutable();
            adbOutput = runCommand(new String[]{adbExecutable, "devices"}, 20);
        } catch (Exception hostAdbError) {
            adbOutput = runCommand(new String[]{"docker", "exec", "appium-container", "adb", "devices"}, 20);
        }
        Assert.assertTrue(
                adbOutput.toLowerCase().contains("list of devices attached"),
                "Unexpected adb output: " + adbOutput
        );
    }

    private static String runCommand(String[] command, int timeoutSeconds) throws Exception {
        Process process = new ProcessBuilder(command)
                .redirectErrorStream(true)
                .start();

        boolean completed = process.waitFor(timeoutSeconds, TimeUnit.SECONDS);
        if (!completed) {
            process.destroyForcibly();
            throw new IllegalStateException("Command timed out: " + String.join(" ", command));
        }

        String output = readStream(process.getInputStream());
        if (process.exitValue() != 0) {
            throw new IllegalStateException("Command failed: " + String.join(" ", command) + " | output: " + output);
        }
        return output;
    }

    private static String readStream(InputStream inputStream) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        return outputStream.toString(StandardCharsets.UTF_8);
    }

    private static String resolveAdbExecutable() {
        List<String> candidates = new ArrayList<>();

        String androidHome = System.getenv("ANDROID_HOME");
        String androidSdkRoot = System.getenv("ANDROID_SDK_ROOT");
        String localAppData = System.getenv("LOCALAPPDATA");

        if (androidHome != null && !androidHome.isBlank()) {
            candidates.add(Path.of(androidHome, "platform-tools", "adb.exe").toString());
            candidates.add(Path.of(androidHome, "platform-tools", "adb").toString());
        }

        if (androidSdkRoot != null && !androidSdkRoot.isBlank()) {
            candidates.add(Path.of(androidSdkRoot, "platform-tools", "adb.exe").toString());
            candidates.add(Path.of(androidSdkRoot, "platform-tools", "adb").toString());
        }

        if (localAppData != null && !localAppData.isBlank()) {
            candidates.add(Path.of(localAppData, "Android", "Sdk", "platform-tools", "adb.exe").toString());
            candidates.add(Path.of(localAppData, "Android", "Sdk", "platform-tools", "adb").toString());
        }

        for (String candidate : candidates) {
            if (Files.isRegularFile(Path.of(candidate))) {
                return candidate;
            }
        }

        return "adb";
    }
}
