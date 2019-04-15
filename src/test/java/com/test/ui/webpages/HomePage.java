package com.test.ui.webpages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.test.ui.utilities.WaitHandler;
import com.test.ui.utilities.WebActionPerformer;

import config.Constants;

public class HomePage extends LoginPage {
	protected final static Logger logger = LogManager.getLogger(HomePage.class.getName());
	
	public HomePage(WebDriver driver, String URL, String userName, String password) {
		super(driver, URL, userName, password);
		logger.info("HomePage is now ready, have fun!");
	}

	@Override
	public boolean navigateTo() {
		super.navigateTo();
		return super.login(super.userName, super.password);
	}
	
	@FindBy(xpath = ".//div[text() = 'Store View']")
	public static WebElement storeViewQuickAccess;

	public boolean gotoStoreViewPage() {
		WaitHandler.explicitWait(driver, storeViewQuickAccess);
		if (WebActionPerformer.clickElement(driver, storeViewQuickAccess, Constants.CLICK_METHOD_ENUM.CLICK, 10))
			return true;
		return false;
	}
}
