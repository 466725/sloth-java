package com.test.ui.utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

public class WebElementChecker {
	protected final static Logger logger = LogManager.getLogger(WebElementChecker.class.getName());

	public static boolean isElementDisplayed(WebElement element) {
		try {
			if (element.isDisplayed())
				return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
		return false;
	}
}