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
