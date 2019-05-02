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
public class TemplateManagementPage extends HomePage {
	protected final static Logger logger = LogManager.getLogger(TemplateManagementPage.class.getName());

	@FindBy(xpath = ".//i[contains(@class, 'fe-plus')]")
	private static WebElement addConceptIcon;

	/**
	 * Add new concept by clicking on the '+' icon
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean addNewConcept() {
		SeleniumWrapper.explicitWait(driver, addConceptIcon, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, addConceptIcon, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
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
		SeleniumWrapper.explicitWait(driver, inputConceptName, Constants.WAIT_TIME_SECOND);
		try {
			SeleniumWrapper.setInputFieldText(inputConceptName, conceptName, driver);
			return true;
		} catch (Exception e) {
			logger.debug("Exception is: " + e);
		}
		return false;
	}

	@FindBy(xpath = ".//span[text() = 'Select']")
	private static WebElement verticalSelector;

	/**
	 * Select a vertical for the new concept
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean selectVertical() {
		SeleniumWrapper.explicitWait(driver, verticalSelector, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, verticalSelector, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
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
		SeleniumWrapper.explicitWait(driver, firstVerticalOption, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, firstVerticalOption, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
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
		SeleniumWrapper.explicitWait(driver, secondVerticalOption, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, secondVerticalOption, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
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
		SeleniumWrapper.explicitWait(driver, thirdVerticalOption, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, thirdVerticalOption, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
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
		SeleniumWrapper.explicitWait(driver, fourthVerticalOption, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, fourthVerticalOption, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
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
	public boolean applyVerticalOption() {
		SeleniumWrapper.explicitWait(driver, applyVerticalButton, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, applyVerticalButton, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
			return true;
		return false;
	}

	@FindBy(xpath = ".//i[contains(@class, 'fe-check ok')]")
	private static WebElement saveNewConceptCheck;

	/**
	 * Save the new concept
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean saveTheNewConcept() {
		SeleniumWrapper.explicitWait(driver, saveNewConceptCheck, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, saveNewConceptCheck, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
			return true;
		return false;
	}

	@FindBy(xpath = ".//i[contains(@class, 'fe-x danger')]")
	private static WebElement cancelNewConceptUncheck;

	/**
	 * Cancel the new concept
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean cancelTheNewConcept() {
		SeleniumWrapper.explicitWait(driver, cancelNewConceptUncheck, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, cancelNewConceptUncheck, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
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
		SeleniumWrapper.explicitWait(driver, rowsOfConcept.get(index), Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.hoverMouseOverElement(driver, rowsOfConcept.get(index)))
			return true;
		return false;
	}

	@FindBy(xpath = ".//div[contains(@class, 'ListManagement-rowData')]")
	private static List<WebElement> rowsOfConceptTrashIcon;

	/**
	 * Hover mouse over a concept row, and then click trash icon
	 * 
	 * @param index index of the row to hover over
	 * @return true, if everything successful; otherwise false
	 */
	public boolean trashConceptByHoveringMouseOver(int index) {
		SeleniumWrapper.explicitWait(driver, rowsOfConcept.get(index), Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.hoverMouseOverElement(driver, rowsOfConcept.get(index)))
			if (SeleniumWrapper.clickElement(driver, rowsOfConceptTrashIcon.get(index),
					Constants.CLICK_METHOD_ENUM.CLICK, Constants.WAIT_TIME_SECOND))
				return true;
		return false;
	}

	@FindBy(xpath = ".//i[contains(@class, 'icon-edit')]")
	private static List<WebElement> rowsOfConceptEditIcon;

	/**
	 * Hover mouse over a concept row, and then click edit concept name icon
	 * 
	 * @param index index of the row to hover over
	 * @return true, if everything successful; otherwise false
	 */
	public boolean editConceptNameByHoveringMouseOver(int index) {
		SeleniumWrapper.waitForPageToLoad(driver);
		if (!SeleniumWrapper.hoverMouseOverElement(driver, rowsOfConceptEditIcon.get(index)))
			return false;
		if (!SeleniumWrapper.clickElement(driver, rowsOfConceptEditIcon.get(index), Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
			return false;
		return true;
	}

	@FindBy(xpath = ".//div[contains(@class, 'EMTitle-eMTitle')]")
	private static List<WebElement> rowsOfConceptNameDiv;

	@FindBy(xpath = ".//div[contains(@class, 'ListAddInput-listAddInput')]/child::*")
	private static WebElement rowsOfConceptEditIconInput;

	@FindBy(xpath = ".//div[contains(@class, 'ListAddInput-endRender')]")
	private static WebElement rowsOfConceptEditIconCheck;

	/**
	 * Hover mouse over a concept row, and then specify a new concept name
	 * 
	 * @param index          index of the row to hover over
	 * @param newConceptName the new concept name to be specified
	 * @return true, if everything successful; otherwise false
	 */
	public boolean specifyNewConceptNameByHoveringMouseOver(int index, String newConceptName) {
		SeleniumWrapper.waitForPageToLoad(driver);
		if (!SeleniumWrapper.hoverMouseOverElement(driver, rowsOfConceptEditIcon.get(index)))
			return false;
		SeleniumWrapper.waitForPageToLoad(driver);
		if (!SeleniumWrapper.clickElement(driver, rowsOfConceptEditIcon.get(index), Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
			return false;
		SeleniumWrapper.waitForPageToLoad(driver);
		if (!SeleniumWrapper.setInputFieldText(rowsOfConceptEditIconInput, newConceptName, driver))
			return false;
		SeleniumWrapper.waitForPageToLoad(driver);
		if (!SeleniumWrapper.clickElement(driver, rowsOfConceptEditIconCheck, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
			return false;
		return true;
	}

	@FindBy(xpath = ".//div[text() = 'Delete' and contains(@class, 'Button-btn')]")
	private static WebElement positiveButton;

	/**
	 * Select the positive button on the popup confirmation window
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean selectPositive() {
		SeleniumWrapper.explicitWait(driver, positiveButton, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, positiveButton, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
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
	public boolean selectNegative() {
		SeleniumWrapper.explicitWait(driver, negativeButton, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, negativeButton, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
			return true;
		return false;
	}

	@FindBy(xpath = ".//div[text() = 'Are you sure you want to delete this item?']")
	private static WebElement confirmMessageText;

	/**
	 * Check the message on the popup confirmation window
	 * 
	 * @param expectedMessage expected popup message to be displayed on the window
	 * @return true, if everything successful; otherwise false
	 */
	public boolean checkPopupMessage(String expectedMessage) {
		SeleniumWrapper.explicitWait(driver, confirmMessageText, Constants.WAIT_TIME_SECOND);
		if (confirmMessageText.getText().contains(expectedMessage))
			return true;
		return false;
	}

	/**
	 * Page object constructor, to initialize the page
	 * 
	 * @param driver   web browser driver
	 * @param URL      web page URL
	 * @param userName login user name
	 * @param password login password
	 * @return true, if everything successful; otherwise false
	 */
	public TemplateManagementPage(WebDriver driver, String URL, String userName, String password) {
		super(driver, URL, userName, password);
		logger.info("StoreViewPage is now ready, have fun!");
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
}
