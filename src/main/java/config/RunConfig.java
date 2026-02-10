package config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public final class RunConfig {
    private final static Logger logger = LogManager.getLogger(RunConfig.class.getName());

    private RunConfig() {
        logger.info("Initializing RunConfig...");
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
}