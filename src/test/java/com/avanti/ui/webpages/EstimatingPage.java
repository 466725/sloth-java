package com.avanti.ui.webpages;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilities.WebPageIframeHandler;
import com.utilities.WebPageUtils;

import config.Constants;

public class EstimatingPage extends HomePage
{
	protected final static Logger logger = LogManager.getLogger(EstimatingPage.class.getName());
	
	public EstimatingPage(WebDriver driver)
	{
		super(driver);
		PageFactory.initElements(driver, this);
		for (WebElement ele : allWebElements)
			if (ele.getText() != null && ele.getAttribute("onclick") != null)
				if (ele.getText().contains("Estimating") && ele.getAttribute("onclick").contains("function"))
				{
					WebPageUtils.clickElement(driver, ele, Constants.CLICK_METHOD_ENUM.CLICK, 15);
					break;
				}
		allWebElements = WebPageUtils.locateAllWebElements(driver);
		logger.info("Estimating page is now ready, have fun!");
	}
	
	public boolean isTableViewDisplayed()
	{
		return allWebElements.size() > 2500;// Do it this way, for a better performance
		/*
		 * for (WebElement ele : allWebElements)
		 * {
		 * if (ele.getText() != null)
		 * if (ele.getText().contains("Copy Order"))
		 * {
		 * WebPageUtils.printWebElementInfo(ele);
		 * return true;
		 * }
		 * }
		 * return false;
		 */
	}
	
	// Quick search input
	@FindBy(name = "83")
	public static WebElement quickSearchInput;
	
	public boolean quickSearch(String estimateID)
	{
		try
		{
			WebPageUtils.sendKeys(driver, quickSearchInput, estimateID, 1);
			return WebPageUtils.clickElement(driver, quickSearchInput, Constants.CLICK_METHOD_ENUM.SENDENTER, 5);
		}
		catch (Exception e)
		{
			logger.error("Exception is: ", e);
			return false;
		}
	}
	
	// Show Estimate Details arrow column
	@FindBy(className = "sv__E5589724_ACBC_4AC5_AA78_797329400103")
	public static List<WebElement> estimateDetailsArrowColumn;
	// Estimate ID column
	@FindBy(className = "sv__72CF09C8_C1FC_49F3_863F_D9C777194564")
	public static List<WebElement> estimateIDColumn;
	
	public boolean showEstimateDetails(String estimateID)
	{
		logger.info("In total: " + estimateDetailsArrowColumn.size() + " detail arrow found! ");
		logger.info("In total: " + estimateIDColumn.size() + " estimate ID found! ");
		if (estimateDetailsArrowColumn.size() != estimateIDColumn.size())
			return false;
		for (int i = 0; i < estimateIDColumn.size(); i++)
			if (estimateIDColumn.get(i).getAttribute("value").equals(estimateID))
				return WebPageUtils.clickElement(driver, estimateDetailsArrowColumn.get(i), Constants.CLICK_METHOD_ENUM.CLICK, 10);
		return false;
	}
	
	String parentWindow = "";
	// Duplicate Estimate icon
	@FindBy(id = "id14a_img")
	public static WebElement duplicateEstimateIcon;
	
	public static boolean handleConfirmPopup(WebDriver driver, Constants.ALLERT_METHOD_ENUM confirmPopup)
	{
		boolean result = false;
		if (!WebPageUtils.clickElement(driver, duplicateEstimateIcon, Constants.CLICK_METHOD_ENUM.CLICK, 8))
			return result;
		List<WebElement> allIframes = WebPageIframeHandler.locateAllIframes(driver);
		WebPageIframeHandler.printAllIframes(allIframes);
		if (allIframes.size() < 1)
			return result;
		if (confirmPopup.equals(Constants.ALLERT_METHOD_ENUM.CANCEL))
		{
			for (WebElement ele : allIframes)
				WebPageIframeHandler.switchToIframe(driver, ele);
			result = WebPageUtils.clickElement(driver, driver.findElement(By.name("0")), Constants.CLICK_METHOD_ENUM.CLICK, 1);
			WebPageIframeHandler.switchParentIframe(driver);
		}
		else if (confirmPopup.equals(Constants.ALLERT_METHOD_ENUM.OK))
		{
			for (WebElement ele : allIframes)
				WebPageIframeHandler.switchToIframe(driver, ele);
			result = WebPageUtils.clickElement(driver, driver.findElement(By.name("1")), Constants.CLICK_METHOD_ENUM.CLICK, 18);
			WebPageIframeHandler.switchParentIframe(driver);
		}
		return result;
	}
	
	// Call below method after clicking OK button confirm pop-up
	public static boolean handleUpdateCostPopup(WebDriver driver, Constants.ALLERT_METHOD_ENUM updateCostPopup)
	{
		boolean result = false;
		List<WebElement> allIframes = WebPageIframeHandler.locateAllIframes(driver);
		WebPageIframeHandler.printAllIframes(allIframes);
		if (allIframes.size() < 1)
			return result;
		for (WebElement ele : allIframes)
			WebPageIframeHandler.switchToIframe(driver, ele);
		if (updateCostPopup.equals(Constants.ALLERT_METHOD_ENUM.NO))
		{
			result = WebPageUtils.clickElement(driver, driver.findElement(By.name("1")), Constants.CLICK_METHOD_ENUM.CLICK, 3);
			WebPageIframeHandler.switchParentIframe(driver);
		}
		else if (updateCostPopup.equals(Constants.ALLERT_METHOD_ENUM.YES))
		{
			result = WebPageUtils.clickElement(driver, driver.findElement(By.name("0")), Constants.CLICK_METHOD_ENUM.CLICK, 25);
			WebPageIframeHandler.switchParentIframe(driver);
		}
		return result;
	}
	
	public boolean gotoLineItemsTab()
	{
		allWebElements = WebPageUtils.locateAllWebElements(driver);
		for (WebElement ele : allWebElements)
			if (ele.getText() != null && ele.getAttribute("class") != null)
				if (ele.getText().contains("Line Items") && ele.getAttribute("class").contains("tablink"))
					return WebPageUtils.clickElement(driver, ele, Constants.CLICK_METHOD_ENUM.CLICK, 6);
		return false;
	}
	
	@FindBy(name = "15")
	public static WebElement laborAdjustedPrice;
	
	public boolean overRideLaborAdjustedPrice()
	{
		boolean result = false;
		result = WebPageUtils.sendKeys(driver, laborAdjustedPrice, "1931.44", 5);
		result = WebPageUtils.clickElement(driver, laborAdjustedPrice, Constants.CLICK_METHOD_ENUM.SENDENTER, 3);
		return result;
	}
	
	@FindBy(className = "sv__00DFD84B_DD6B_4853_B6DC_DA5B0DE79BC3")
	public static WebElement lineItemCalculator;
	
	public boolean launchLineItemsCalculator(boolean switchIframe)
	{
		boolean result = false;
		WebPageUtils.printWebElementInfo(lineItemCalculator);
		result = WebPageUtils.clickElement(driver, lineItemCalculator, Constants.CLICK_METHOD_ENUM.CLICK, 15);
		if (switchIframe)
		{
			List<WebElement> allIframes = WebPageIframeHandler.locateAllIframes(driver);
			WebPageIframeHandler.printAllIframes(allIframes);
			if (allIframes.size() < 1)
				return result;
			for (WebElement ele : allIframes)
				WebPageIframeHandler.switchToIframe(driver, ele);
		}
		return result;
	}
	
	@FindBy(className = "w_close")
	public static WebElement lineItemCalculatorCloseIcon;
	
	public boolean closeLineItemsCalculator(boolean switchIframe)
	{
		boolean result = false;
		if (switchIframe)
		{
			result = WebPageIframeHandler.switchParentIframe(driver);
		}
		WebPageUtils.printWebElementInfo(lineItemCalculatorCloseIcon);
		result = WebPageUtils.clickElement(driver, lineItemCalculatorCloseIcon, Constants.CLICK_METHOD_ENUM.CLICK, 3);
		return result;
	}
	
	public boolean gotoSectionsTab()
	{
		allWebElements = WebPageUtils.locateAllWebElements(driver);
		for (WebElement ele : allWebElements)
			try
			{
				if (ele.getText() != null)
					if (ele.getText().contains("Sections") && ele.getAttribute("class").contains("tablink"))
						return WebPageUtils.clickElement(driver, ele, Constants.CLICK_METHOD_ENUM.CLICK, 5);
			}
			catch (Exception e)
			{
				logger.error("Exception is: ", e);
				return false;
			}
		return false;
	}
	
	public boolean gotoSectionDetails()
	{
		allWebElements = WebPageUtils.locateAllWebElements(driver);
		parentWindow = driver.getWindowHandle(); // Store parent window
		logger.info("parentWindow.toString(): " + parentWindow.toString());
		ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
		logger.info("windowHandlers.size(): " + windows.size());
		logger.info("windowHandlers.toString(): " + windows.toString());
		for (WebElement ele : allWebElements)
			if (ele.getAttribute("onmouseover") != null && ele.getAttribute("onmouseover").contains("Section Details"))
			{
				if (!WebPageUtils.clickElement(driver, ele, Constants.CLICK_METHOD_ENUM.CLICK, 6))
					return false;
				windows = new ArrayList<String>(driver.getWindowHandles());
				logger.info("windowHandlers.size(): " + windows.size());
				logger.info("windowHandlers.toString(): " + windows.toString());
				if (windows.size() > 1)
					windows.remove(parentWindow);
				driver.switchTo().window(windows.get(0));
				return true;
			}
		return false;
	}
	
	public boolean gotoPressTab(Boolean useActions)
	{
		if (useActions)
		{
			for (int i = 0; i < 119; i++)
			{
				new Actions(driver).sendKeys(Keys.TAB).perform();
				WebPageUtils.gotoSleep(2);
			}
			new Actions(driver).sendKeys(Keys.RETURN).perform();
			WebPageUtils.gotoSleep(8);
			return true;
		}
		allWebElements = WebPageUtils.locateAllWebElements(driver);
		for (WebElement ele : allWebElements)
			if (ele.getText() != null)
				if (ele.getText().contains("Press") && ele.getAttribute("onclick").contains("onABC()"))
					return WebPageUtils.clickElement(driver, ele, Constants.CLICK_METHOD_ENUM.SUBMIT, 8);
		return false;
	}
	
	public boolean recalculateSelectedSection()
	{
		for (WebElement ele : allWebElements)
			if (ele.getText() != null && ele.getAttribute("class") != null)
				if (ele.getText().contains("Recalculate Section") && ele.getAttribute("class").contains("button"))
					return WebPageUtils.clickElement(driver, ele, Constants.CLICK_METHOD_ENUM.RUNJS, 25);
		return false;
	}
	
	public boolean clickSectionDetailsCloseBotton()
	{
		for (WebElement ele : allWebElements)
			if (ele.getText() != null && ele.getAttribute("class") != null)
				if (ele.getText().contains("Close") && ele.getAttribute("class").contains("button"))
				{
					WebPageUtils.clickElement(driver, ele, Constants.CLICK_METHOD_ENUM.CLICK, 1);
					WebPageUtils.clickElement(driver, ele, Constants.CLICK_METHOD_ENUM.SENDENTER, 1);
					WebPageUtils.clickElement(driver, ele, Constants.CLICK_METHOD_ENUM.SENDRETURN, 1);
					WebPageUtils.clickElement(driver, ele, Constants.CLICK_METHOD_ENUM.SUBMIT, 1);
					WebPageUtils.clickElement(driver, ele, Constants.CLICK_METHOD_ENUM.RUNJS, 25);
					driver.switchTo().window(parentWindow);
					return true;
				}
		driver.switchTo().window(parentWindow);
		return false;
	}
	
	// Green check icon(Save Record)
	@FindBy(id = "id150_img")
	public static WebElement saveEstimateIcon;
	
	public boolean saveDuplicatedEstimate()
	{
		return WebPageUtils.clickElement(driver, saveEstimateIcon, Constants.CLICK_METHOD_ENUM.CLICK, 15);
	}
}
