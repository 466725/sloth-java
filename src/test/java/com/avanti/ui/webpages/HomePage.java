package com.avanti.ui.webpages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.framework.templates.GuiTestCase;
import com.utilities.WebPageUtils;

import config.Constants;

public class HomePage extends LoginPage
{
	protected final static Logger logger = LogManager.getLogger(HomePage.class.getName());
	
	public HomePage(WebDriver driver)
	{
		super(driver);
		super.login(GuiTestCase.userName, GuiTestCase.password);
		PageFactory.initElements(driver, this);
		allWebElements = WebPageUtils.locateAllWebElements(driver);
		logger.info("Home page is now ready, have fun!");
	}
	
	public Boolean isUsernameDisplayed()
	{
		for (WebElement ele : allWebElements)
		{
			try
			{
				if (ele.getText() != null)
					if (ele.getText().contains("Username : Andy Zheng - Q/A Data BM"))
					{
						WebPageUtils.printWebElementInfo(ele);
						return true;
					}
			}
			catch (Exception e)
			{
				logger.error("Exception is: ", e);
				return false;
			}
		}
		return false;
	}
	
	// Shipping navigator "+" icon
	@FindBy(id = "id106")
	public static WebElement shippingModule;
	// Purchasing navigator "+" icon
	@FindBy(id = "idfe")
	public static WebElement purchasingModule;
	// Production navigator "+" icon
	@FindBy(id = "idf6")
	public static WebElement productionModule;
	
	public boolean gotoSlingshotModules(Constants.SLING_SHOT_MODULE mudule)
	{
		switch (mudule)
		{
		case PURCHASING:
			if (WebPageUtils.clickElement(driver, purchasingModule, Constants.CLICK_METHOD_ENUM.CLICK, 1))
				return true;
			return WebPageUtils.clickElement(driver, (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("idfe"))), Constants.CLICK_METHOD_ENUM.CLICK, 1);
		case SHIPPING:
			if (WebPageUtils.clickElement(driver, shippingModule, Constants.CLICK_METHOD_ENUM.CLICK, 1))
				return true;
			return WebPageUtils.clickElement(driver, (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("id106"))), Constants.CLICK_METHOD_ENUM.CLICK, 1);
		case PRODUCTION:
			if (WebPageUtils.clickElement(driver, productionModule, Constants.CLICK_METHOD_ENUM.CLICK, 1))
				return true;
			return WebPageUtils.clickElement(driver, (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("idf6"))), Constants.CLICK_METHOD_ENUM.CLICK, 1);
		default:
			return false;
		}
	}
	
	public boolean isEstimatingLaunched()
	{
		for (WebElement ele : allWebElements)
		{
			if (ele.getText() != null)
				if (ele.getText().contains("Estimating"))
				{
					return WebPageUtils.clickElement(driver, ele, Constants.CLICK_METHOD_ENUM.CLICK, 15);
				}
		}
		return false;
	}
	
	public boolean isShopFloorLaunched()
	{
		allWebElements = WebPageUtils.locateAllWebElements(driver);
		for (WebElement ele : allWebElements)
		{
			if (ele.getText() != null)
				if (ele.getText().contains("Shop Floor"))
				{
					return WebPageUtils.clickElement(driver, ele, Constants.CLICK_METHOD_ENUM.CLICK, 15);
				}
		}
		return false;
	}
}
