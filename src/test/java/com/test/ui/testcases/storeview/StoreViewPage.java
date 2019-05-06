package com.test.ui.testcases.storeview;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.test.ui.testcases.HomePage;
import com.test.ui.utilities.SeleniumWrapper;

import config.Constants;

/**
 * Store view page object to host all locators on it, and related methods
 * 
 * @author Weipeng Zheng
 *
 */
public class StoreViewPage extends HomePage {
	protected final static Logger logger = LogManager.getLogger(StoreViewPage.class.getName());

	/**
	 * Page object constructor, to initialize the page
	 * 
	 * @param driver   web browser driver
	 * @param URL      web page URL
	 * @param userName login user name
	 * @param password login password
	 */
	public StoreViewPage(WebDriver driver, String URL, String userName, String password) {
		super(driver, URL, userName, password);
		logger.info("StoreViewPage is now ready, have fun!");
	}

	/**
	 * Page object navigator, to navigate to the page object
	 */
	@Override
	public boolean navigateTo() {
		super.navigateTo();
		return super.gotoStoreViewPage();
	}

	@FindBy(xpath = ".//*[text() = 'HOSPITALITY']//parent::div//parent::button")
	public static WebElement hospitality;

	/**
	 * Navigate to hospitality page object
	 */
	public boolean gotoHospitalityPage() {
		SeleniumWrapper.explicitWaitClickable(driver, hospitality, 15);
		return SeleniumWrapper.clickElement(driver, hospitality, Constants.CLICK_METHOD_ENUM.CLICK);
	}
}
