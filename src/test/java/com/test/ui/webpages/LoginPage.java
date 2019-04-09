package com.test.ui.webpages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.framework.templates.WebPage;
import com.test.ui.utilities.WaitHandler;
import com.test.ui.utilities.WebActionPerformer;

import config.Constants;

public class LoginPage extends WebPage {
	protected final static Logger logger = LogManager.getLogger(LoginPage.class.getName());
	private static WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		LoginPage.driver = driver;
		PageFactory.initElements(driver, this);
		logger.info("Login page is now ready, have fun!");
	}

	// User name
	//@FindBy(id = "login_user")
	@FindBy(xpath = ".//*[contains(@id,'login_user')]")
	public static WebElement username;

	public static boolean inputUsername(String userName) {
		WaitHandler.explicitWait(driver, username);
		try {
			username.clear();
			WaitHandler.implicitWait(driver);
			username.sendKeys(userName);
			WaitHandler.implicitWait(driver);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	// Password
	//@FindBy(id = "login_pw")
	@FindBy(xpath = ".//*[contains(@id,'login_pw')]")
	public static WebElement userPassword;

	public static boolean inputPassword(String password) {
		WaitHandler.explicitWait(driver, userPassword);
		try {
			userPassword.clear();
			WaitHandler.implicitWait(driver);
			userPassword.sendKeys(password);
			WaitHandler.implicitWait(driver);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	// Login button
	@FindBy(id = "login_btn")
	public static WebElement loginButton;

	public static boolean clickLoginButton() {
		WaitHandler.explicitWait(driver, loginButton);
		return WebActionPerformer.clickElement(driver, loginButton, Constants.CLICK_METHOD_ENUM.CLICK, 10);
	}

	public static boolean login(String userName, String password) {
		if (inputUsername(userName) && inputPassword(password))
			return clickLoginButton();
		return false;
	}

	// @FindBy(className = "form-message m15t")
	@FindBy(xpath = ".//*[contains(@class,'form-message m15t')]")
	public static WebElement invalidCredentials;

	public static boolean verifyInvalidEmailMessage() {
		WaitHandler.explicitWait(driver, invalidCredentials);
		return invalidCredentials.getText().contains("User credentials are invalid");
	}
}