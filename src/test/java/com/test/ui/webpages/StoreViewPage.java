package com.test.ui.webpages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.test.ui.utilities.WaitHandler;
import com.test.ui.utilities.WebActionPerformer;

import config.Constants;

public class StoreViewPage extends HomePage {
	protected final static Logger logger = LogManager.getLogger(StoreViewPage.class.getName());

	public StoreViewPage(WebDriver driver, String URL, String userName, String password) {
		super(driver, URL, userName, password);
		logger.info("StoreViewPage is now ready, have fun!");
	}

	@Override
	public boolean navigateTo() {
		super.navigateTo();
		super.login(super.userName, super.password);
		return super.gotoStoreViewPage();
	}
	
	
	@FindBy(xpath = ".//*[text() = 'Hospitality']//parent::div//parent::button")
	public static WebElement hospitality;

	public boolean gotoHospitalityPage() {
		WaitHandler.explicitWait(driver, hospitality);
		return WebActionPerformer.clickElement(driver, hospitality, Constants.CLICK_METHOD_ENUM.CLICK, 10);
	}
}
