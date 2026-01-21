package com.framework.webpages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Base class for all displayable components, such as web pages and mobile screens.
 */
public abstract class Displayable {
    private static final Logger LOGGER = LogManager.getLogger(Displayable.class);

    /**
     * Navigates to the specific displayable component.
     *
     * @return true if navigation was successful, false otherwise.
     */
    public abstract boolean navigateTo();
}