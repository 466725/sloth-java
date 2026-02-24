package testcases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public final class TestGroups {
    final static Logger logger = LogManager.getLogger(TestGroups.class.getName());
    public static final String API = "api";
    public static final String UI_WEB = "ui-web";
    public static final String UI_MOBILE_APP = "ui-mobile-app";
    public static final String UI_MOBILE_WEB = "ui-mobile-web";
    public static final String SMOKE = "smoke";
    public static final String REGRESSION = "regression";
    public static final String INTEGRATION = "integration";
    public static final String QUARANTINE = "quarantine"; // Flaky test cases
    public static final String CANADA_ONLY = "canada-only"; // API can be called only within Canada
    public static final String AMAZON = "amazon";

    // Utility class; not meant to be instantiated.
    private TestGroups() {
        logger.info("TestGroups class created");
    }
}
