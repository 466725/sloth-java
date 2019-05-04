package com.framework.templates;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Base class of all page objects
 * 
 * @author Weipeng Zheng
 *
 */
public abstract class WebPage {
	protected final static Logger logger = LogManager.getLogger(WebPage.class.getName());

	/**
	 * Constructor of page object base class
	 * 
	 */
	public WebPage() {
		logger.info("Here is base class of all page objects, good luck!");
	}

	/**
	 * Page object navigator, to navigate to the page object
	 */
	abstract public boolean navigateTo();
}