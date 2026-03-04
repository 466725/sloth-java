package utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Utility class for OS detection.
 *
 * @author Weipeng Zheng
 */
public final class OperationSystemDetector {
    private static final Logger logger = LogManager.getLogger(OperationSystemDetector.class.getName());
    private static final String OS_NAME = System.getProperty("os.name", "").toLowerCase();
    private static final Platform PLATFORM = detectPlatform(OS_NAME);

    private OperationSystemDetector() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static boolean isWindows() {
        return PLATFORM == Platform.WINDOWS;
    }

    public static boolean isMac() {
        return PLATFORM == Platform.MAC;
    }

    public static boolean isUnix() {
        return PLATFORM == Platform.UNIX;
    }

    public static boolean isSolaris() {
        return PLATFORM == Platform.SOLARIS;
    }

    // Returns a short OS code and logs the detected platform.
    public static String getOS() {
        switch (PLATFORM) {
            case WINDOWS:
                logger.info("Detected Windows");
                return "win";
            case MAC:
                logger.info("Detected Mac");
                return "osx";
            case UNIX:
                logger.warn("Detected Unix or Linux");
                return "uni";
            case SOLARIS:
                logger.error("Detected Solaris");
                return "sol";
            default:
                logger.fatal("Unsupported OS: " + OS_NAME);
                return "err";
        }
    }

    public static String getOsName() {
        return OS_NAME;
    }

    public static boolean isSupportedForUiTests() {
        return isWindows() || isMac();
    }

    private static Platform detectPlatform(String osName) {
        if (osName.contains("win")) {
            return Platform.WINDOWS;
        }
        if (osName.contains("mac")) {
            return Platform.MAC;
        }
        if (osName.contains("nix") || osName.contains("nux")) {
            return Platform.UNIX;
        }
        if (osName.contains("sunos")) {
            return Platform.SOLARIS;
        }
        return Platform.UNKNOWN;
    }

    public static void main(String[] args) {
        System.out.println(OS_NAME);
        getOS();
    }

    private enum Platform {
        WINDOWS,
        MAC,
        UNIX,
        SOLARIS,
        UNKNOWN
    }
}
