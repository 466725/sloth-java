package com.framework.templates;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public abstract class WebPage {
	protected final static Logger logger = LogManager.getLogger(WebPage.class.getName());

	public WebPage() {
	}

	abstract public boolean navigateTo();
}