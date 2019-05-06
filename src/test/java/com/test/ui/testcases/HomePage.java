package com.test.ui.testcases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.test.ui.utilities.SeleniumWrapper;

import config.Constants;

/**
 * Home page object to host all locators on it, and related methods
 * 
 * @author Weipeng Zheng
 *
 */
public class HomePage extends LoginPage {
	protected final static Logger logger = LogManager.getLogger(HomePage.class.getName());

	/**
	 * Page object constructor, to initialize the page
	 * 
	 * @param driver   web browser driver
	 * @param URL      web page URL
	 * @param userName login user name
	 * @param password login password
	 */
	public HomePage(WebDriver driver, String URL, String userName, String password) {
		super(driver, URL, userName, password);
		logger.info("HomePage is now ready, have fun!");
	}

	/**
	 * Page object navigator, to navigate to the page object
	 */
	@Override
	public boolean navigateTo() {
		super.navigateTo();
		return super.login(super.userName, super.password);
	}

	@FindBy(xpath = ".//div[text() = 'Store View']")
	public static WebElement storeView;

	/**
	 * Navigate to store view page object
	 */
	public boolean gotoStoreViewPage() {
		SeleniumWrapper.explicitWait(driver, storeView, 15);
		if (SeleniumWrapper.clickElement(driver, storeView, Constants.CLICK_METHOD_ENUM.CLICK))
			return true;
		return false;
	}

	@FindBy(xpath = ".//div[text() = 'Template Management']")
	public static WebElement templateManagement;

	/**
	 * Navigate to template management page object
	 */
	public boolean gotoTemplateManagement() {
		SeleniumWrapper.explicitWait(driver, templateManagement, 15);
		if (SeleniumWrapper.clickElement(driver, templateManagement, Constants.CLICK_METHOD_ENUM.CLICK))
			return true;
		return false;
	}
}
