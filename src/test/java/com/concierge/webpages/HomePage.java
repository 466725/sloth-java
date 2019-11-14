package com.concierge.webpages;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.utilities.SeleniumWrapper;

import config.Constants;

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

	@FindBy(xpath = "//*[@id=\"sidebar-menu-items\"]/div/ul/li[3]/a/span")
	public static WebElement deliveriesPage;

	/**
	 * Navigate to deliveries page
	 */
	public DeliveriesPage gotoDeliveriesPage() {
		SeleniumWrapper.explicitWaitClickable(driver, deliveriesPage, 15);
		if (SeleniumWrapper.clickElement(driver, deliveriesPage, Constants.CLICK_METHOD_ENUM.CLICK))
			return new DeliveriesPage(driver, URL, password, userName);
		return null;
	}
}