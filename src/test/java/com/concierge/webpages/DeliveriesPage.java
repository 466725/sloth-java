package com.concierge.webpages;
import org.openqa.selenium.WebDriver;

public class DeliveriesPage extends HomePage {

	/**
	 * Page object constructor, to initialize the page
	 * 
	 * @param driver   web browser driver
	 * @param URL      web page URL
	 * @param userName login user name
	 * @param password login password
	 */
	public DeliveriesPage(WebDriver driver, String URL, String userName, String password) {
		super(driver, URL, userName, password);
		logger.info("DeliveriesPage is now ready, have fun!");
	}

	/**
	 * Page object navigator, to navigate to the page object
	 */
	@Override
	public boolean navigateTo() {
		return !super.gotoDeliveriesPage().equals(null);
	}
}
