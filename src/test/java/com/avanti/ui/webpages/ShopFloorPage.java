package com.avanti.ui.webpages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.framework.templates.GuiTestCase;
import com.openqa.utils.WebPageUtils;
import config.Constants;

public class ShopFloorPage extends HomePage {

	protected final static Logger logger = LogManager.getLogger(ShopFloorPage.class.getName());

	public ShopFloorPage(WebDriver driver) {
		super(driver);
		super.login(GuiTestCase.userName, GuiTestCase.password);
		PageFactory.initElements(driver, this);
		logger.info("Shop Floor page is now ready, have fun!");
		WebPageUtils.gotoSleep(3);
		allWebElements = WebPageUtils.locateAllWebElements(driver);
	}

	public static boolean inputEmployeeCode(String userName) {
		for (WebElement ele : allWebElements) {
			if (ele.getAttribute("class") != null && ele.getAttribute("class") != null
					&& ele.getAttribute("onfocus") != null)
				if (ele.getAttribute("class").toString().contains("field")
						&& ele.getAttribute("type").toString().contains("text")) {
					return WebPageUtils.sendKeys(driver, ele, userName, 3);
				}
		}
		return false;
	}

	public static boolean inputShopFloorPassword(String password) {
		return true; // Disabled Shop Floor password, for now
	}

	public static boolean clickShopFloorLoginButton() {
		for (WebElement ele : allWebElements) {
			if (ele.getText() != null)
				if (ele.getText().contains("Login")) {
					return WebPageUtils.clickElement(driver, ele, Constants.CLICK_METHOD_ENUM.CLICK, 15);
				}
		}
		return false;
	}

	public static boolean loginShopFloor(String userName, String password) {
		if (inputEmployeeCode(userName) && inputShopFloorPassword(password))
			return clickShopFloorLoginButton();
		return false;
	}

	public boolean isShopFloorPageDisplayedIndependently() {
		allWebElements = WebPageUtils.locateAllWebElements(driver);
		for (WebElement ele : allWebElements) {
			if (ele.getText() != null)
				if ((ele.getText().contains("start your shift") || ele.getText().contains("Start Shift"))) {
					return true;
				}
		}
		return false;
	}
}
