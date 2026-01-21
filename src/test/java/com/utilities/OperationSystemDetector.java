package com.utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Utility class to detect platform/OS smartly
 *
 * @author Weipeng Zheng
 *
 */
public class OperationSystemDetector {
    private final static Logger logger = LogManager.getLogger(OperationSystemDetector.class.getName());
    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return (OS.contains("win"));
    }

    public static boolean isMac() {
        return (OS.contains("mac"));
    }

    public static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux"));
    }

    public static boolean isSolaris() {
        return (OS.contains("sunos"));
    }

    // Fetch OS and log it accordingly, to generate WebDriver smartly
    public static String getOS() {
        if (isWindows()) {
            return logAndReturn("info", "This is Windows", "win");
        } else if (isMac()) {
            return logAndReturn("info", "This is Mac", "osx");
        } else if (isUnix()) {
            return logAndReturn("warn", "This is Unix or Linux", "uni");
        } else if (isSolaris()) {
            return logAndReturn("error", "This is Solaris", "sol");
        } else {
            return logAndReturn("fatal", "Your OS is not support!!", "err");
        }
    }

    private static String logAndReturn(String level, String message, String returnVal) {
        switch (level) {
            case "info" -> logger.info(message);
            case "warn" -> logger.warn(message);
            case "error" -> logger.error(message);
            case "fatal" -> logger.fatal(message);
        }
        return returnVal;
    }

    static void main() {
        System.out.println(OS);
        getOS();
    }
}