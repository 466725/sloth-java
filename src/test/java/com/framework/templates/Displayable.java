package com.framework.templates;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Base class of everything can be displayed, for example web page and app
 * screen
 * 
 * @author Weipeng Zheng
 *
 */
public abstract class Displayable {
	protected final static Logger logger = LogManager.getLogger(Displayable.class.getName());

	/**
	 * Constructor of this base class
	 * 
	 */
	public Displayable() {
		logger.info("Here is base class of all page objects, good luck!");
	}

	/**
	 * Navigator
	 */
	abstract public boolean navigateTo();
}