package com.avanti.ui.webpages;

import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.framework.templates.WebPage;
import com.openqa.utils.WebPageUtils;
import config.Constants;

public class LoginPage extends WebPage
{
	protected final static Logger logger = LogManager.getLogger(LoginPage.class.getName());
	protected static List<WebElement> allWebElements = null;
	protected static WebDriver driver;
	
	public LoginPage(WebDriver driver)
	{
		LoginPage.driver = driver;
		PageFactory.initElements(driver, this);
		allWebElements = WebPageUtils.locateAllWebElements(driver);
		logger.info("Login page is now ready, have fun!");
	}
	
	public static boolean inputUsername(String userName)
	{
		logger.info("Going to input user name: " + userName);
		for (WebElement ele : allWebElements)
		{
			if (ele.getAttribute("class") != null && ele.getAttribute("type") != null && ele.getAttribute("tabindex") != null)
				if (ele.getAttribute("class").toString().contains("field") && ele.getAttribute("type").toString().contains("text") && ele.getAttribute("tabindex").toString().contains("1"))
				{
					return WebPageUtils.sendKeys(driver, ele, userName, 3);
				}
		}
		return false;
	}
	
	public static boolean inputPassword(String password)
	{
		logger.info("Going to input password: " + password);
		for (WebElement ele : allWebElements)
		{
			if (ele.getAttribute("class") != null && ele.getAttribute("type") != null && ele.getAttribute("tabindex") != null)
				if (ele.getAttribute("class").toString().contains("field") && ele.getAttribute("type").toString().contains("password") && ele.getAttribute("tabindex").toString().contains("2"))
				{
					WebPageUtils.sendKeys(driver, ele, password, 3);
					WebPageUtils.clickElement(driver, ele, Constants.CLICK_METHOD_ENUM.SENDENTER, 1);
					return WebPageUtils.clickElement(driver, ele, Constants.CLICK_METHOD_ENUM.SENDENTER, 15);
				}
		}
		return false;
	}
	
	public static boolean clickLoginButton()
	{
		logger.info("Going to click the Slingshot Login button (actually do nothing). ");
		return true;
	}
	
	public static boolean login(String userName, String password)
	{
		if (inputUsername(userName) && inputPassword(password))
			return clickLoginButton();
		return false;
	}

	@Override
	public boolean navigateTo() {
		// TODO Auto-generated method stub
		return false;
	}
}
