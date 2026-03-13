package webpages.selfhealing;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Runtime configuration for locator self-healing.
 *
 * <p>This feature is intentionally opt-in at the callsite (via {@link SelfHealingLocator}),
 * and can be disabled globally with system property/environment variable.
 */
public final class SelfHealingConfig {
    private static final Logger logger = LogManager.getLogger(SelfHealingConfig.class.getName());

    private SelfHealingConfig() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Enable/disable self-healing.
     *
     * <p>System property: {@code self.healing.enabled}
     * <p>Environment variable: {@code SELF_HEALING_ENABLED}
     */
    public static boolean isEnabled() {
        String prop = System.getProperty("self.healing.enabled");
        if (prop != null) {
            return Boolean.parseBoolean(prop);
        }
        String env = System.getenv("SELF_HEALING_ENABLED");
        if (env != null) {
            return env.equalsIgnoreCase("true");
        }
        return true;
    }

    /**
     * Minimum acceptable score for a healed match.
     *
     * <p>System property: {@code self.healing.minScore}
     */
    public static double minScore() {
        String prop = System.getProperty("self.healing.minScore");
        if (prop == null || prop.isBlank()) {
            return 0.35; // low default: similarity may be weak when UI text changes
        }
        try {
            return Double.parseDouble(prop.trim());
        } catch (NumberFormatException e) {
            logger.warn("Invalid self.healing.minScore=" + prop + "; using default.", e);
            return 0.35;
        }
    }

    /**
     * Maximum number of candidates to score from the DOM before giving up.
     *
     * <p>System property: {@code self.healing.maxCandidates}
     */
    public static int maxCandidates() {
        String prop = System.getProperty("self.healing.maxCandidates");
        if (prop == null || prop.isBlank()) {
            return 800;
        }
        try {
            return Integer.parseInt(prop.trim());
        } catch (NumberFormatException e) {
            logger.warn("Invalid self.healing.maxCandidates=" + prop + "; using default.", e);
            return 800;
        }
    }
}

