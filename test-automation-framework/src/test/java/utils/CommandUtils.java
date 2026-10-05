package utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class CommandUtils {
    private final static Logger logger = LogManager.getLogger(CommandUtils.class.getName());

    // Utility methods for invoking external commands used by integration tests.
    private CommandUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    // Runs a shell command and returns combined stdout/stderr output.
    public static String runCommand(String[] command, int timeoutSeconds) throws Exception {
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

    // Resolves the adb executable path from common SDK locations, then falls back to PATH.
    public static String resolveAdbExecutable() {
        // Prefer SDK-resolved adb paths before falling back to PATH lookup.
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

    private static String readStream(InputStream inputStream) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        return outputStream.toString(StandardCharsets.UTF_8);
    }
}
