package com.test.ui.webpages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.framework.templates.WebPage;
import com.test.ui.utilities.WebElementChecker;
import com.test.ui.utilities.WaitHandler;
import com.test.ui.utilities.WebActionPerformer;

import config.Constants;

public class HomePage extends WebPage {
	protected final static Logger logger = LogManager.getLogger(HomePage.class.getName());
	private static WebDriver driver;

	public HomePage(WebDriver driver) {
		HomePage.driver = driver;
		PageFactory.initElements(driver, this);
		logger.info("Home page is now ready, have fun!");
	}

	// Login Button
	//@FindBy(className = "button login")
	@FindBy(xpath = ".//*[contains(@class,'button login')]")
	private static WebElement loginButton;

	public boolean isLoginButtonDisplayed() {
		WaitHandler.explicitWait(driver, loginButton);
		return WebElementChecker.isElementDisplayed(loginButton);
	}

	public static boolean clickLoginButton() {
		WaitHandler.explicitWait(driver, loginButton);
		return WebActionPerformer.clickElement(driver, loginButton, Constants.CLICK_METHOD_ENUM.CLICK, 15);
	}

	public LoginPage gotoLoginPage() {
		if (clickLoginButton())
			return new LoginPage(driver);
		return null;
	}
}