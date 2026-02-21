package utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.UUID;

/**
 * Utility methods for string checks used by automation tests.
 * <p>
 * <b>Do not change these methods without reviewing impact on existing tests.</b>
 * </p>
 */
public class StringUtils {
    protected final static Logger logger = LogManager.getLogger(StringUtils.class.getName());

    /**
     * Returns true when the input is null or empty (after trim).
     *
     * @return true if the string is null or empty
     */
    public static boolean isEmpty(String stringToCheck) {
        return (stringToCheck == null || stringToCheck.trim().isEmpty());
    }

    /**
     * Returns true when the input is not null and not empty (after trim).
     *
     * @return true if the string is not null and not empty
     */
    public static boolean isNotEmpty(String stringToCheck) {
        return !isEmpty(stringToCheck);
    }

    /**
     * Generates a unique identifier string.
     *
     * @return a unique identifier
     */
    public static synchronized String generateUniqueIdentifier() {
        return "UUID-" + UUID.randomUUID();
    }
}
