package com.test.ui.webpages;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.test.ui.utilities.SeleniumWrapper;

import config.Constants;

/**
 * Template management page object to host all locators on it, and related methods
 * 
 * @author Weipeng Zheng
 *
 */
public class TemplateManagementPage extends HomePage {
	protected final static Logger logger = LogManager.getLogger(TemplateManagementPage.class.getName());
	private static final String ADD_CONCEPT_ICON = ".//i[contains(@class, 'fe-plus')]";
	private static final String ADD_CONCEPT_INPUT = ".//input[contains(@placeholder, 'Add Concept')]";
	private static final String VERTICAL_SELECTOR = ".//span[text() = 'Select']";
	private static final String FIRST_VERTICAL_OPTION = ".//div[text() = 'Restaurant']";
	private static final String SECOND_VERTICAL_OPTION = ".//div[text() = 'Hospitality']";
	private static final String THIRD_VERTICAL_OPTION = ".//div[text() = 'Health Care']";
	private static final String FOURTH_VERTICAL_OPTION = ".//div[text() = 'Entertaiment']";
	private static final String APPLY_VERTICAL_BUTTON = ".//div[text() = 'Apply']";
	private static final String SAVE_NEW_CONCEPT_CHECK = ".//i[contains(@class, 'fe-check ok')]";
	private static final String CANCEL_NEW_CONCEPT_UNCHECK = ".//i[contains(@class, 'fe-x danger')]";
	private static final String CONCEPT_ROW_DATA_LIST = ".//div[contains(@class, 'ListManagement-rowData')]";
	private static final String CONCEPT_ROW_DATA_TRASH_LIST = ".//i[contains(@class, 'fe fe-trash')]";
	private static final String CONCEPT_ROW_DATA_NAME_LIST = ".//div[contains(@class, 'EMTitle-eMTitle')]";
	private static final String CONCEPT_ROW_DATA_EDIT_LIST = ".//i[contains(@class, 'icon-edit')]";
	private static final String CONCEPT_ROW_DATA_EDIT_LIST_INPUT = ".//div[contains(@class, 'ListAddInput-listAddInput')]/child::*";
	private static final String POSITIVE_BUTTON = ".//div[text() = 'Delete' and contains(@class, 'Button-btn')]";
	private static final String NEGATIVE_BUTTON = ".//div[text() = 'Cancel' and contains(@class, 'Button-btn')]";
	private static final String CONFIRM_MESSAGE_TEXT = ".//div[text() = 'Are you sure you want to delete this item?']";
	
	@FindBy(xpath = ADD_CONCEPT_ICON)
	private static WebElement addConceptIcon;

	@FindBy(xpath = ADD_CONCEPT_INPUT)
	private static WebElement inputConceptName;

	@FindBy(xpath = VERTICAL_SELECTOR)
	private static WebElement verticalSelector;

	@FindBy(xpath = FIRST_VERTICAL_OPTION)
	private static WebElement firstVerticalOption;

	@FindBy(xpath = SECOND_VERTICAL_OPTION)
	private static WebElement secondVerticalOption;

	@FindBy(xpath = THIRD_VERTICAL_OPTION)
	private static WebElement thirdVerticalOption;

	@FindBy(xpath = FOURTH_VERTICAL_OPTION)
	private static WebElement fourthVerticalOption;

	@FindBy(xpath = APPLY_VERTICAL_BUTTON)
	private static WebElement applyVerticalButton;

	@FindBy(xpath = SAVE_NEW_CONCEPT_CHECK)
	private static WebElement saveNewConceptCheck;

	@FindBy(xpath = CANCEL_NEW_CONCEPT_UNCHECK)
	private static WebElement cancelNewConceptUncheck;

	@FindBy(xpath = CONCEPT_ROW_DATA_LIST)
	private static List<WebElement> rowsOfConcept;

	@FindBy(xpath = CONCEPT_ROW_DATA_TRASH_LIST)
	private static List<WebElement> rowsOfConceptTrashIcon;
	
	@FindBy(xpath = CONCEPT_ROW_DATA_NAME_LIST)
	private static List<WebElement> rowsOfConceptNameDiv;

	@FindBy(xpath = CONCEPT_ROW_DATA_EDIT_LIST)
	private static List<WebElement>  rowsOfConceptEditIcon;
	
	@FindBy(xpath = CONCEPT_ROW_DATA_EDIT_LIST_INPUT)
	private static WebElement rowsOfConceptEditIconInput;

	@FindBy(xpath = POSITIVE_BUTTON)
	private static WebElement positiveButton;

	@FindBy(xpath = NEGATIVE_BUTTON)
	private static WebElement negativeButton;

	@FindBy(xpath = CONFIRM_MESSAGE_TEXT)
	private static WebElement confirmMessageText;

	/**
	 * Add new concept by clicking on the '+' icon
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean addNewConcept()
	{
		SeleniumWrapper.explicitWait(driver, addConceptIcon, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, addConceptIcon, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
			return true;
		return false;
	}

	/**
	 * Input a name to the new concept
	 * 
	 * @param conceptName Name for the new concept
	 * @return true, if everything successful; otherwise false
	 */
	public boolean inputNewConceptName(String conceptName)
	{
		SeleniumWrapper.explicitWait(driver, inputConceptName, Constants.WAIT_TIME_SECOND);
		try
		{
			SeleniumWrapper.setInputFieldText(inputConceptName, conceptName, driver);
			return true;
		}
		catch (Exception e)
		{
			logger.debug("Exception is: " + e);
		}
		return false;
	}

	/**
	 * Select a vertical for the new concept
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean selectVertical()
	{
		SeleniumWrapper.explicitWait(driver, verticalSelector, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, verticalSelector, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
			return true;
		return false;
	}

	/**
	 * Select the first vertical option for the new concept
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean selectFirstVerticalOption()
	{
		SeleniumWrapper.explicitWait(driver, firstVerticalOption, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, firstVerticalOption, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
			return true;
		return false;
	}

	/**
	 * Select the second vertical option for the new concept
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean selectSecondVerticalOption()
	{
		SeleniumWrapper.explicitWait(driver, secondVerticalOption, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, secondVerticalOption, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
			return true;
		return false;
	}

	/**
	 * Select the third vertical option for the new concept
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean selectThirdVerticalOption()
	{
		SeleniumWrapper.explicitWait(driver, thirdVerticalOption, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, thirdVerticalOption, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
			return true;
		return false;
	}

	/**
	 * Select the fourth vertical option for the new concept
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean selectFourthVerticalOption()
	{
		SeleniumWrapper.explicitWait(driver, fourthVerticalOption, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, fourthVerticalOption, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
			return true;
		return false;
	}

	/**
	 * Apply selected verticals to the new concept
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean applyVerticalOption()
	{
		SeleniumWrapper.explicitWait(driver, applyVerticalButton, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, applyVerticalButton, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
			return true;
		return false;
	}

	/**
	 * Save the new concept
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean saveTheNewConcept()
	{
		SeleniumWrapper.explicitWait(driver, saveNewConceptCheck, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, saveNewConceptCheck, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
			return true;
		return false;
	}

	/**
	 * Cancel the new concept
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean cancelTheNewConcept()
	{
		SeleniumWrapper.explicitWait(driver, cancelNewConceptUncheck, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, cancelNewConceptUncheck, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
			return true;
		return false;
	}

	/**
	 * Hover mouse over a concept row
	 * 
	 * @param index index of the row to hover over
	 * @return true, if everything successful; otherwise false
	 */
	public boolean hoverMouseOverConceptRow(int index)
	{
		SeleniumWrapper.explicitWait(driver, rowsOfConcept.get(index), Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.hoverMouseOverElement(driver, rowsOfConcept.get(index)))
			return true;
		return false;
	}

	/**
	 * Hover mouse over a concept row, and then click trash icon
	 * 
	 * @param index index of the row to hover over
	 * @return true, if everything successful; otherwise false
	 */
	public boolean trashConceptByHoveringMouseOver(int index)
	{
		SeleniumWrapper.explicitWait(driver, rowsOfConcept.get(index), Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.hoverMouseOverElement(driver, rowsOfConcept.get(index)))
			if (SeleniumWrapper.clickElement(driver, rowsOfConceptTrashIcon.get(index),
					Constants.CLICK_METHOD_ENUM.CLICK, Constants.WAIT_TIME_SECOND))
				return true;
		return false;
	}

	/**
	 * Hover mouse over a concept row, and then click edit concept name icon
	 * 
	 * @param index index of the row to hover over
	 * @return true, if everything successful; otherwise false
	 */
	public boolean editConceptNameByHoveringMouseOver(int index)
	{
		SeleniumWrapper.waitForPageToLoad(driver);
		if (!SeleniumWrapper.hoverMouseOverElement(driver, rowsOfConceptEditIcon.get(index)))
			return false;
		if (!SeleniumWrapper.clickElement(driver, rowsOfConceptEditIcon.get(index), Constants.CLICK_METHOD_ENUM.CLICK, Constants.WAIT_TIME_SECOND))
			return false;
		return true;
	}

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
		if (!SeleniumWrapper.clickElement(driver, rowsOfConceptEditIcon.get(index), Constants.CLICK_METHOD_ENUM.CLICK, Constants.WAIT_TIME_SECOND))
			return false;
		SeleniumWrapper.waitForPageToLoad(driver);
		if (!SeleniumWrapper.setInputFieldText(rowsOfConceptEditIconInput, newConceptName, driver))
			return false;
		return true;
	}
	
	/**
	 * Select the positive button on the popup confirmation window
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean selectPositive()
	{
		SeleniumWrapper.explicitWait(driver, positiveButton, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, positiveButton, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
			return true;
		return false;
	}

	/**
	 * Select the negative button on the popup confirmation window
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	public boolean selectNegative()
	{
		SeleniumWrapper.explicitWait(driver, negativeButton, Constants.WAIT_TIME_SECOND);
		if (SeleniumWrapper.clickElement(driver, negativeButton, Constants.CLICK_METHOD_ENUM.CLICK,
				Constants.WAIT_TIME_SECOND))
			return true;
		return false;
	}

	/**
	 * Check the message on the popup confirmation window
	 * 
	 * @param expectedMessage expected popup message to be displayed on the window
	 * @return true, if everything successful; otherwise false
	 */
	public boolean checkPopupMessage(String expectedMessage)
	{
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
	public TemplateManagementPage(WebDriver driver, String URL, String userName, String password)
	{
		super(driver, URL, userName, password);
		logger.info("StoreViewPage is now ready, have fun!");
	}

	/**
	 * Page object navigator, to navigate to the page object
	 * 
	 * @return true, if everything successful; otherwise false
	 */
	@Override
	public boolean navigateTo()
	{
		super.navigateTo();
		return super.gotoTemplateManagement();
	}
}
