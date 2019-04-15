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
	protected String URL = "";
	protected String userName = "";
	protected String password = "";
	protected WebDriver driver = null;

	public LoginPage(WebDriver driver, String URL, String userName, String password) {
		this.driver = driver;
		this.URL = URL;
		this.userName = userName;
		this.password = password;
		PageFactory.initElements(driver, this);
		logger.info("LoginPage is now ready, have fun!");
	}

	@Override
	public boolean navigateTo() {
		driver.navigate().to(URL);
		return true;
	}

	@FindBy(xpath = ".//*[contains(@placeholder, 'Enter Your email or account')]")
	public static WebElement loginUsername;

	public boolean inputUsername(String userName) {
		WaitHandler.explicitWait(driver, loginUsername);
		try {
			loginUsername.clear();
			WaitHandler.implicitWait(driver);
			loginUsername.sendKeys(userName);
			WaitHandler.implicitWait(driver);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	@FindBy(xpath = ".//*[contains(@placeholder, 'Enter your password')]")
	public static WebElement loginPassword;

	public boolean inputPassword(String password) {
		WaitHandler.explicitWait(driver, loginPassword);
		try {
			loginPassword.clear();
			WaitHandler.implicitWait(driver);
			loginPassword.sendKeys(password);
			WaitHandler.implicitWait(driver);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	@FindBy(xpath = ".//button[contains(@class, 'login')]")
	public static WebElement loginButton;

	public boolean clickLoginButton() {
		WaitHandler.explicitWait(driver, loginButton);
		return WebActionPerformer.clickElement(driver, loginButton, Constants.CLICK_METHOD_ENUM.CLICK, 10);
	}

	public boolean login(String userName, String password) {
		if (inputUsername(userName) && inputPassword(password))
			return clickLoginButton();
		return false;
	}
}