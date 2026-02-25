package config;

/**
 * @author Weipeng Zheng
 *
 */
public final class Constants {
    public static final String CONFIG_FILE_FOLDER = "src/main/resources/";
    public static final String SCREENSHOT_FOLDER = "test-output/ExtentReport/ScreenShot/";
    public static final String THEATRE_SHOWTIME_CSV = "theatre-data.csv";
    public static final String CONFIG_FILE = CONFIG_FILE_FOLDER + "init-config.properties";
    // Use classpath resource names for runtime loading (not source-directory paths)
    public static final String LOG4J_CONFIG_RESOURCE = "log4j-config.xml";

    private Constants() {
        throw new UnsupportedOperationException("Utility class");
    }

    public enum CLICK_METHOD {
        CLICK,
        SEND_ENTER,
        SEND_RETURN,
        SUBMIT,
        RUN_JS
    }
}