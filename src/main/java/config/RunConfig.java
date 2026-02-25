package config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public final class RunConfig {
    private final static Logger logger = LogManager.getLogger(RunConfig.class.getName());

    private RunConfig() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static boolean isHeadless() {
        String prop = System.getProperty("headless");
        if (prop != null) {
            logger.debug("Found 'headless' system property: " + prop);
            return Boolean.parseBoolean(prop);
        }

        String env = System.getenv("HEADLESS");
        if (env != null) {
            logger.debug("Found 'HEADLESS' environment variable: " + env);
            return env.equalsIgnoreCase("true");
        }

        logger.debug("No 'headless' configuration found, defaulting to false");
        return false;
    }

    // Test set property
    public static void main(String[] args) {
        System.setProperty("headless", "true");
        System.out.println("Set 'headless' property to: " + true);
        System.out.println("Headless value actually is: " + isHeadless());
        System.setProperty("headless", "false");
        System.out.println("Set 'headless' property to: " + false);
        System.out.println("Headless value actually is: " + isHeadless());
    }
}