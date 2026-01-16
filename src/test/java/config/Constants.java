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

    public static final String RESOURCE_FOLDER = "src/test/resources/";
    public static final String SCREENSHOT_FOLDER = "test-output/ExtentReport/ScreenShot/";
    public static final String SITE_REQUEST_BODY = "ApiRequestBody/SiteMgmt/";
    public static final String WIN64_DRIVER_FIREFOX = RESOURCE_FOLDER + "FirefoxDrivers/win/geckodriver-64.exe";

    public static final String PROPERTY_FILE = RESOURCE_FOLDER + "init-properties-config";
    public static final String THEATRE_SHOWTIME_CSV = "theatre_data.csv";

    public static enum CLICK_METHOD_ENUM {
        CLICK,
        SENDENTER,
        SENDRETURN,
        SUBMIT,
        RUNJS
    }

    public static enum ALLERT_METHOD_ENUM {
        SWITCHTO,
        NO,
        YES,
        OK,
        CANCEL
    }
}