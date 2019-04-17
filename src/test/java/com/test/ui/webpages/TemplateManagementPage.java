package com.test.ui.webpages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * Template management page object to host all locators on it, and related methods
 * 
 * @author Weipeng Zheng
 *
 */
public class TemplateManagementPage extends HomePage {
	protected final static Logger logger = LogManager.getLogger(TemplateManagementPage.class.getName());

	/**
	 * Page object constructor, to initialize the page
	 * 
	 * @param driver   web browser driver
	 * @param URL      web page URL
	 * @param userName login user name
	 * @param password login password
	 */
	public TemplateManagementPage(WebDriver driver, String URL, String userName, String password) {
		super(driver, URL, userName, password);
		logger.info("StoreViewPage is now ready, have fun!");
	}

	/**
	 * Page object navigator, to navigate to the page object
	 */
	@Override
	public boolean navigateTo() {
		super.navigateTo();
		return super.gotoTemplateManagement();
	}
}
