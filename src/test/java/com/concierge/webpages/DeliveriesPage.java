package com.concierge.webpages;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.utilities.SeleniumWrapper;
import com.utilities.TestResultValidator;

import config.Constants;

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

	@FindBy(xpath = "//*[@id=\"datatable_packages\"]/thead/tr/th[1]")
	public static WebElement columnHeaderID;

	@FindBy(xpath = "//*[@id=\"datatable_packages\"]/tbody/tr/td[1]")
	public static List<WebElement> columnID;

	/**
	 * Check sort per column ID
	 */
	public boolean sortByColumnID() {
		SeleniumWrapper.explicitWaitClickable(driver, columnHeaderID, 15);
		if (SeleniumWrapper.clickElement(driver, columnHeaderID, Constants.CLICK_METHOD_ENUM.CLICK))
			return TestResultValidator.isIntegerElementsAscendingOrdered(columnID);
		return false;
	}

	@FindBy(xpath = "//*[@id=\"datatable_packages\"]/thead/tr/th[2]")
	public static WebElement columnHeaderReceived;

	@FindBy(xpath = "//*[@id=\"datatable_packages\"]/tbody/tr/td[2]")
	public static List<WebElement> columnReceived;

	/**
	 * Check sort per column Received
	 */
	public boolean sortByColumnReceived() {
		SeleniumWrapper.explicitWaitClickable(driver, columnHeaderReceived, 15);
		if (SeleniumWrapper.clickElement(driver, columnHeaderReceived, Constants.CLICK_METHOD_ENUM.CLICK))
			return TestResultValidator.isDateElementsAscendingOrdered(columnReceived);
		return false;
	}

	@FindBy(xpath = "//*[@id=\"datatable_packages\"]/thead/tr/th[3]")
	public static WebElement columnHeaderUnit;

	@FindBy(xpath = "//*[@id=\"datatable_packages\"]/tbody/tr/td[3]")
	public static List<WebElement> columnUnit;

	/**
	 * Check sort per column Unit
	 */
	public boolean sortByColumnUnit() {
		SeleniumWrapper.explicitWaitClickable(driver, columnHeaderUnit, 15);
		if (SeleniumWrapper.clickElement(driver, columnHeaderUnit, Constants.CLICK_METHOD_ENUM.CLICK))
			return TestResultValidator.isStringElementsAscendingOrdered(columnUnit);
		return false;
	}

	@FindBy(xpath = "//*[@id=\"datatable_packages\"]/thead/tr/th[4]")
	public static WebElement columnHeaderLocation;

	@FindBy(xpath = "//*[@id=\"datatable_packages\"]/tbody/tr/td[4]")
	public static List<WebElement> columnLocation;

	/**
	 * Check sort per column Location
	 */
	public boolean sortByColumnLocation() {
		SeleniumWrapper.explicitWaitClickable(driver, columnHeaderLocation, 15);
		if (SeleniumWrapper.clickElement(driver, columnHeaderLocation, Constants.CLICK_METHOD_ENUM.CLICK))
			return TestResultValidator.isStringElementsAscendingOrdered(columnLocation);
		return false;
	}

	@FindBy(xpath = "//*[@id=\"datatable_packages\"]/thead/tr/th[5]")
	public static WebElement columnHeaderType;

	@FindBy(xpath = "//*[@id=\"datatable_packages\"]/tbody/tr/td[5]")
	public static List<WebElement> columnType;

	/**
	 * Check sort per column Type
	 */
	public boolean sortByColumnType() {
		SeleniumWrapper.explicitWaitClickable(driver, columnHeaderType, 15);
		if (SeleniumWrapper.clickElement(driver, columnHeaderType, Constants.CLICK_METHOD_ENUM.CLICK))
			return TestResultValidator.isStringElementsAscendingOrdered(columnType);
		return false;
	}

	@FindBy(xpath = "//*[@id=\"datatable_packages\"]/thead/tr/th[6]")
	public static WebElement columnHeaderRecipient;

	@FindBy(xpath = "//*[@id=\"datatable_packages\"]/tbody/tr/td[6]")
	public static List<WebElement> columnRecipient;

	/**
	 * Check sort per column Recipient
	 */
	public boolean sortByColumnRecipient() {
		SeleniumWrapper.explicitWaitClickable(driver, columnHeaderRecipient, 15);
		if (SeleniumWrapper.clickElement(driver, columnHeaderRecipient, Constants.CLICK_METHOD_ENUM.CLICK))
			return TestResultValidator.isStringElementsAscendingOrdered(columnRecipient);
		return false;
	}

	@FindBy(xpath = "//*[@id=\"datatable_packages\"]/thead/tr/th[7]")
	public static WebElement columnHeaderItem;

	@FindBy(xpath = "//*[@id=\"datatable_packages\"]/tbody/tr/td[7]")
	public static List<WebElement> columnItem;

	/**
	 * Check sort per column Item
	 */
	public boolean sortByColumnItem() {
		SeleniumWrapper.explicitWaitClickable(driver, columnHeaderItem, 15);
		if (SeleniumWrapper.clickElement(driver, columnHeaderItem, Constants.CLICK_METHOD_ENUM.CLICK))
			return TestResultValidator.isStringElementsAscendingOrdered(columnItem);
		return false;
	}
}
