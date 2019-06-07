package com.volante.ui.testcases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.framework.templates.WebPage;
import com.utilities.SeleniumWrapper;

import config.Constants;

/**
 * Login page object to host all locators on it, and related methods
 * 
 * @author Weipeng Zheng
 *
 */
public class LoginPage extends WebPage {
	protected final static Logger logger = LogManager.getLogger(LoginPage.class.getName());
	protected String URL;
	protected String userName;
	protected String password;
	protected WebDriver driver;

	/**
	 * Page object constructor, to initialize the page
	 * 
	 * @param driver   web browser driver
	 * @param URL      web page URL
	 * @param userName login user name
	 * @param password login password
	 */
	public LoginPage(WebDriver driver, String URL, String userName, String password) {
		this.driver = driver;
		this.URL = URL;
		this.userName = userName;
		this.password = password;
		PageFactory.initElements(driver, this);
		logger.info("LoginPage is now ready, have fun!");
	}

	/**
	 * Page object navigator, to navigate to the page object
	 */
	@Override
	public boolean navigateTo() {
		try {
			driver.navigate().to(URL);
			return true;
		} catch (Exception e) {
			logger.error("Exception is: ", e);
			return false;
		}
	}

	@FindBy(xpath = ".//*[contains(@placeholder, 'Enter Your email or account')]")
	private static WebElement loginUsername;

	/**
	 * Input user name
	 * 
	 * @param userName login user name
	 */
	private boolean inputUsername(String userName) {
		SeleniumWrapper.explicitWaitClickable(driver, loginUsername, 15);
		try {
			loginUsername.clear();
			SeleniumWrapper.implicitWait(driver);
			loginUsername.sendKeys(userName);
			SeleniumWrapper.implicitWait(driver);
			return true;
		} catch (Exception e) {
			logger.error("Exception is: ", e);
			return false;
		}
	}

	@FindBy(xpath = ".//*[contains(@placeholder, 'Enter your password')]")
	private static WebElement loginPassword;

	/**
	 * Input password
	 * 
	 * @param password login password
	 */
	private boolean inputPassword(String password) {
		SeleniumWrapper.explicitWaitClickable(driver, loginPassword, 15);
		try {
			loginPassword.clear();
			SeleniumWrapper.implicitWait(driver);
			loginPassword.sendKeys(password);
			SeleniumWrapper.implicitWait(driver);
			return true;
		} catch (Exception e) {
			logger.error("Exception is: ", e);
			return false;
		}
	}

	@FindBy(xpath = ".//button[contains(@class, 'login')]")
	private static WebElement loginButton;

	/**
	 * Click login button
	 */
	private boolean clickLoginButton() {
		SeleniumWrapper.explicitWaitClickable(driver, loginButton, 15);
		return SeleniumWrapper.clickElement(driver, loginButton, Constants.CLICK_METHOD_ENUM.CLICK);
	}

	/**
	 * Login, input user name, input password, and then click login button
	 */
	public boolean login(String userName, String password) {
		if (inputUsername(userName) && inputPassword(password))
			return clickLoginButton();
		return false;
	}
}