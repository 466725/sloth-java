package com.test.ui.webpages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * Hospitality page object to host all locators on it, and related methods
 * 
 * @author Weipeng Zheng
 *
 */
public class HospitalityPage extends StoreViewPage {
	protected final static Logger logger = LogManager.getLogger(HospitalityPage.class.getName());

	/**
	 * Page object constructor, to initialize the page
	 * 
	 * @param driver   web browser driver
	 * @param URL      web page URL
	 * @param userName login user name
	 * @param password login password
	 */
	public HospitalityPage(WebDriver driver, String URL, String userName, String password) {
		super(driver, URL, userName, password);
		logger.info("HospitalityPage is now ready, have fun!");
	}

	/**
	 * Page object navigator, to navigate to the page object
	 */
	@Override
	public boolean navigateTo() {
		super.navigateTo();
		return super.gotoHospitalityPage();
	}
}
