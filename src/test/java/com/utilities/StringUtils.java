package com.utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.UUID;

/**
 * This utility class is prepared keeping in mind the checks required on the
 * string for automation tests.
 * <p>
 * <b>PLEASE DO NOT MAKE CHANGE TO ANY METHOD WITHOUT ANALIZING THE IMPACT OF
 * THE CHANGE TO EXISTING AUTOMATION TESTS</b>
 * </P>
 */
public class StringUtils {
    protected final static Logger logger = LogManager.getLogger(StringUtils.class.getName());

    /**
     * Checks for both NULL and EMPTY string and returns true if either is true.
     *
     * @return true if string is NULL or is a empty string
     */
    public static boolean isEmpty(String stringToCheck) {
        return (stringToCheck == null || stringToCheck.trim().isEmpty());
    }

    /**
     * Checks for both NULL and EMPTY string and returns true if both are NOT true.
     *
     * @return true if string is not NULL and is not a empty string
     */
    public static boolean isNotEmpty(String stringToCheck) {
        return !isEmpty(stringToCheck);
    }

    /**
     * To generate and return a unique identifier string
     *
     * @return a unique identifier
     */
    public static synchronized String generateUniqueIdentifier() {
        return "UUID-" + UUID.randomUUID();
    }
}
