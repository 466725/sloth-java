package core;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public final class TestGroups {
    // Utility class; not meant to be instantiated.
    private TestGroups() {
        throw new UnsupportedOperationException("Utility class");
    }
    final static Logger logger = LogManager.getLogger(TestGroups.class.getName());
    public static final String UNIT = "unit";
    public static final String API = "api";
    public static final String PLAYWRIGHT = "playwright";
    public static final String SELENIUM = "selenium";
    public static final String UI_MOBILE_APP = "ui-mobile-app";
    public static final String UI_MOBILE_WEB = "ui-mobile-web";
    public static final String SMOKE = "smoke";
    public static final String REGRESSION = "regression";
    public static final String INTEGRATION = "integration";
    public static final String QUARANTINE = "quarantine"; // Flaky test cases
}
