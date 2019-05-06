package com.test.ui.testcases.template;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.test.ui.testcases.HomePage;
import com.test.ui.utilities.SeleniumWrapper;

import config.Constants;

/**
 * Template management page to host all locators on it, and related methods
 * 
 * @author Weipeng Zheng
 *
 */
public class TemplatePage extends HomePage {
	protected final static Logger logger = LogManager.getLogger(TemplatePage.class.getName());

	/**
	 * Page object constructor, to initialize the page
	 * 
	 * @param driver   web browser driver
	 * @param URL      web page URL
	 * @param userName login user name
	 * @param password login password
	 * @return true, if everything successful; otherwise false
	 */
	public TemplatePage(WebDriver driver, String URL, String userName, String password) {
		super(driver, URL, userName, password);
		logger.info("TemplatePage is now ready, have fun!");
	}

	/**
	 * Page object navigator, to navigate to the page object
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	@Override
	public boolean navigateTo() {
		super.navigateTo();
		return super.gotoTemplateManagement();
	}

	@FindBy(xpath = ".//i[contains(@class, 'fe-plus')]")
	private static WebElement addConceptIcon;

	/**
	 * Add new concept by clicking on the '+' icon
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean clickAddNewConceptIcon() {
		SeleniumWrapper.explicitWait(driver, addConceptIcon, Constants.EXPLICIT_WAIT_TIME);
		if (SeleniumWrapper.clickElement(driver, addConceptIcon, Constants.CLICK_METHOD_ENUM.CLICK))
			return true;
		return false;
	}

	@FindBy(xpath = ".//input[contains(@placeholder, 'Add Concept')]")
	private static WebElement inputConceptName;

	/**
	 * Input a name to the new concept
	 * 
	 * @param conceptName Name for the new concept
	 * @return true, if everything successful; otherwise false
	 */
	public boolean inputNewConceptName(String conceptName) {
		SeleniumWrapper.explicitWait(driver, inputConceptName, Constants.EXPLICIT_WAIT_TIME);
		try {
			SeleniumWrapper.setInputFieldText(inputConceptName, conceptName, driver);
			return true;
		} catch (Exception e) {
			logger.error("Exception is: " + e);
			return false;
		}
	}

	@FindBy(xpath = ".//span[text() = 'Select']")
	private static WebElement verticalSelector;

	/**
	 * Click the vertical select drop-down, to multi-select any of them
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean clickVerticalSelectDropdown() {
		SeleniumWrapper.explicitWait(driver, verticalSelector, Constants.EXPLICIT_WAIT_TIME);
		if (SeleniumWrapper.clickElement(driver, verticalSelector, Constants.CLICK_METHOD_ENUM.CLICK))
			return true;
		return false;
	}

	@FindBy(xpath = ".//div[text() = 'Restaurant']")
	private static WebElement firstVerticalOption;

	/**
	 * Select the first vertical option for the new concept
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean selectFirstVerticalOption() {
		SeleniumWrapper.explicitWait(driver, firstVerticalOption, Constants.EXPLICIT_WAIT_TIME);
		if (SeleniumWrapper.clickElement(driver, firstVerticalOption, Constants.CLICK_METHOD_ENUM.CLICK))
			return true;
		return false;
	}

	@FindBy(xpath = ".//div[text() = 'Hospitality']")
	private static WebElement secondVerticalOption;

	/**
	 * Select the second vertical option for the new concept
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean selectSecondVerticalOption() {
		SeleniumWrapper.explicitWait(driver, secondVerticalOption, Constants.EXPLICIT_WAIT_TIME);
		if (SeleniumWrapper.clickElement(driver, secondVerticalOption, Constants.CLICK_METHOD_ENUM.CLICK))
			return true;
		return false;
	}

	@FindBy(xpath = ".//div[text() = 'Health Care']")
	private static WebElement thirdVerticalOption;

	/**
	 * Select the third vertical option for the new concept
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean selectThirdVerticalOption() {
		SeleniumWrapper.explicitWait(driver, thirdVerticalOption, Constants.EXPLICIT_WAIT_TIME);
		if (SeleniumWrapper.clickElement(driver, thirdVerticalOption, Constants.CLICK_METHOD_ENUM.CLICK))
			return true;
		return false;
	}

	@FindBy(xpath = ".//div[text() = 'Entertaiment']")
	private static WebElement fourthVerticalOption;

	/**
	 * Select the fourth vertical option for the new concept
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean selectFourthVerticalOption() {
		SeleniumWrapper.explicitWait(driver, fourthVerticalOption, Constants.EXPLICIT_WAIT_TIME);
		if (SeleniumWrapper.clickElement(driver, fourthVerticalOption, Constants.CLICK_METHOD_ENUM.CLICK))
			return true;
		return false;
	}

	@FindBy(xpath = ".//div[text() = 'Apply']")
	private static WebElement applyVerticalButton;

	/**
	 * Apply selected verticals to the new concept
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean applySelectedVerticalOptions() {
		SeleniumWrapper.explicitWait(driver, applyVerticalButton, Constants.EXPLICIT_WAIT_TIME);
		if (SeleniumWrapper.clickElement(driver, applyVerticalButton, Constants.CLICK_METHOD_ENUM.CLICK))
			return true;
		return false;
	}

	@FindBy(xpath = ".//i[contains(@class, 'fe-check ok')]")
	private static WebElement saveConcept;

	/**
	 * Save the new concept, by clicking the icon
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean clickSaveNewConceptIcon() {
		SeleniumWrapper.explicitWait(driver, saveConcept, Constants.EXPLICIT_WAIT_TIME);
		if (SeleniumWrapper.clickElement(driver, saveConcept, Constants.CLICK_METHOD_ENUM.CLICK))
			return true;
		return false;
	}

	@FindBy(xpath = ".//i[contains(@class, 'fe-x danger')]")
	private static WebElement cancelConcept;

	/**
	 * Cancel the new concept, by clicking the icon
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean clickCancelNewConceptIcon() {
		SeleniumWrapper.explicitWait(driver, cancelConcept, Constants.EXPLICIT_WAIT_TIME);
		if (SeleniumWrapper.clickElement(driver, cancelConcept, Constants.CLICK_METHOD_ENUM.CLICK))
			return true;
		return false;
	}

	@FindBy(xpath = ".//div[contains(@class, 'ListManagement-rowData')]")
	private static List<WebElement> rowsOfConcept;

	/**
	 * Hover mouse over a concept row
	 * 
	 * @param index index of the row to hover over
	 * @return true, if everything successful; otherwise false
	 */
	public boolean hoverMouseOverConceptRow(int index) {
		SeleniumWrapper.explicitWait(driver, rowsOfConcept.get(index), Constants.EXPLICIT_WAIT_TIME);
		if (SeleniumWrapper.hoverMouseOverElement(driver, rowsOfConcept.get(index)))
			return true;
		return false;
	}

	@FindBy(xpath = ".//i[contains(@class, 'icon-edit')]")
	private static List<WebElement> conceptNameEditIcon;

	/**
	 * Hover mouse over a concept row, and then click edit concept name icon
	 * 
	 * @param index index of the row to hover over
	 * @return true, if everything successful; otherwise false
	 */
	public boolean clickEditConceptNameIcon(int index) {
		SeleniumWrapper.waitForDomToBeRendered(driver);
		if (!SeleniumWrapper.hoverMouseOverElement(driver, conceptNameEditIcon.get(index)))
			return false;
		if (!SeleniumWrapper.clickElement(driver, conceptNameEditIcon.get(index), Constants.CLICK_METHOD_ENUM.CLICK))
			return false;
		return true;
	}

	@FindBy(xpath = ".//div[contains(@class, 'EMTitle-eMTitle')]")
	private static List<WebElement> conceptNameDiv;

	@FindBy(xpath = ".//div[contains(@class, 'ListAddInput-listAddInput')]/child::*")
	private static WebElement conceptNameInput;

	@FindBy(xpath = ".//div[contains(@class, 'ListAddInput-endRender')]")
	private static WebElement conceptNameCheckIcon;

	/**
	 * Hover mouse over a concept row, and then specify a new concept name
	 * 
	 * @param index          index of the row to hover over
	 * @param newConceptName the new concept name to be specified
	 * @return true, if everything successful; otherwise false
	 */
	public boolean specifyNewConceptNameByHoveringMouseOver(int index, String newConceptName) {
		SeleniumWrapper.waitForDomToBeRendered(driver);
		if (!SeleniumWrapper.hoverMouseOverElement(driver, conceptNameEditIcon.get(index)))
			return false;
		SeleniumWrapper.waitForDomToBeRendered(driver);
		if (!SeleniumWrapper.clickElement(driver, conceptNameEditIcon.get(index), Constants.CLICK_METHOD_ENUM.CLICK))
			return false;
		SeleniumWrapper.waitForDomToBeRendered(driver);
		if (!SeleniumWrapper.setInputFieldText(conceptNameInput, newConceptName, driver))
			return false;
		SeleniumWrapper.waitForDomToBeRendered(driver);
		if (!SeleniumWrapper.clickElement(driver, conceptNameCheckIcon, Constants.CLICK_METHOD_ENUM.CLICK))
			return false;
		return true;
	}

	@FindBy(xpath = ".//i[contains(@class, 'fe fe-trash')]")
	private static List<WebElement> deleteConceptIcon;

	/**
	 * Hover mouse over a concept row, and then click trash icon
	 * 
	 * @param index index of the row to hover over
	 * @return true, if everything successful; otherwise false
	 */
	public boolean hoverAndClickRrashConceptIcon(int index) {
		SeleniumWrapper.waitForDomToBeRendered(driver);
		if (SeleniumWrapper.hoverMouseOverElement(driver, deleteConceptIcon.get(index)))
			if (SeleniumWrapper.clickElement(driver, deleteConceptIcon.get(index),
					Constants.CLICK_METHOD_ENUM.CLICK))
				return true;
		return false;
	}
	
	@FindBy(xpath = ".//div[text() = 'Delete' and contains(@class, 'Button-btn')]")
	private static WebElement positiveButton;

	/**
	 * Select the positive button on the popup confirmation window
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean selectPositiveOnDeleteConceptPopup() {
		SeleniumWrapper.explicitWait(driver, positiveButton, Constants.EXPLICIT_WAIT_TIME);
		if (SeleniumWrapper.clickElement(driver, positiveButton, Constants.CLICK_METHOD_ENUM.CLICK))
			return true;
		return false;
	}

	@FindBy(xpath = ".//div[text() = 'Cancel' and contains(@class, 'Button-btn')]")
	private static WebElement negativeButton;

	/**
	 * Select the negative button on the popup confirmation window
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean selectNegativeOnDeleteConceptPopup() {
		SeleniumWrapper.explicitWait(driver, negativeButton, Constants.EXPLICIT_WAIT_TIME);
		if (SeleniumWrapper.clickElement(driver, negativeButton, Constants.CLICK_METHOD_ENUM.CLICK))
			return true;
		return false;
	}

	@FindBy(xpath = ".//div[text() = 'Are you sure you want to delete this item?']")
	private static WebElement confirmMessage;

	/**
	 * Check the message on the popup confirmation window
	 * 
	 * @param expectedMessage expected popup message to be displayed on the window
	 * @return true, if everything successful; otherwise false
	 */
	public boolean checkPopupMessageOnDeleteConceptPopup(String expectedMessage) {
		SeleniumWrapper.explicitWait(driver, confirmMessage, Constants.EXPLICIT_WAIT_TIME);
		if (confirmMessage.getText().contains(expectedMessage))
			return true;
		return false;
	}
}
