package utilities;

import java.util.UUID;

/**
 * Utility methods for string checks used by automation tests.
 * <p>
 * <b>Do not change these methods without reviewing impact on existing tests.</b>
 * </p>
 */
public final class StringUtils {
    private static final String UUID_PREFIX = "UUID-";

    private StringUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Returns true when the input is null or empty (after trim).
     *
     * @return true if the string is null or empty
     */
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Returns true when the input is not null and not empty (after trim).
     *
     * @return true if the string is not null and not empty
     */
    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    /**
     * Generates a unique identifier string.
     *
     * @return a unique identifier
     */
    public static String generateUniqueIdentifier() {
        return UUID_PREFIX + UUID.randomUUID();
    }
}
