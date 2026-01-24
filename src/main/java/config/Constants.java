package config;

import java.time.Duration;

/**
 * Put all constant and ENUM here, please
 *
 * @author Weipeng Zheng
 *
 */
public final class Constants {
    public static final java.time.Duration IMPLICIT_WAIT_TIME = Duration.ofSeconds(5);
    public static final int EXPLICIT_WAIT_TIME = 10;
    public static final java.time.Duration PAGE_LOAD_TIME = Duration.ofSeconds(10);
    public static final int PAGE_RENDER_TIME = 1000; //sleep for one second

    public static final String CONFIG_FILE_FOLDER = "src/main/resources/";
    public static final String SCREENSHOT_FOLDER = "test-output/ExtentReport/ScreenShot/";

    public static final String THEATRE_SHOWTIME_CSV = "theatre_data.csv";
    public static final String CONFIG_FILE = CONFIG_FILE_FOLDER + "init-config.properties";

    public static enum CLICK_METHOD_ENUM {
        CLICK,
        SENDENTER,
        SENDRETURN,
        SUBMIT,
        RUNJS
    }
}