package com.avanti.ui.webpages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.framework.templates.GuiTestCase;
import com.openqa.utils.WebPageUtils;
import config.Constants;

public class SalesOrdersPage extends HomePage
{
	
	protected final static Logger logger = LogManager.getLogger(SalesOrdersPage.class.getName());
	
	public SalesOrdersPage(WebDriver driver)
	{
		super(driver);
		super.login(GuiTestCase.userName, GuiTestCase.password);
		PageFactory.initElements(driver, this);
		allWebElements = WebPageUtils.locateAllWebElements(driver);
		for (WebElement ele : allWebElements)
		{
			if (ele.getText() != null)
				if (ele.getText().contains("Sales Orders"))
				{
					WebPageUtils.clickElement(driver, ele, Constants.CLICK_METHOD_ENUM.CLICK, 15);
					break;
				}
		}
		logger.info("Sales Orders page is now ready, have fun!");
		allWebElements = WebPageUtils.locateAllWebElements(driver);
	}
	
	public boolean isTableViewDisplayed()
	{
		for (WebElement ele : allWebElements)
		{
			try
			{
				if (ele.getText() != null)
					if (ele.getText().contains("Order - Table View"))
					{
						WebPageUtils.printWebElementInfo(ele);
						return true;
					}
			}
			catch (Exception e)
			{
				logger.error("Exception is: ", e);
			}
		}
		return false;
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
	}
}
