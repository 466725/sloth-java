package com.test.ui.webpages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class HospitalityPage extends StoreViewPage {
	protected final static Logger logger = LogManager.getLogger(HospitalityPage.class.getName());

	public HospitalityPage(WebDriver driver, String URL, String userName, String password) {
		super(driver, URL, userName, password);
		logger.info("HospitalityPage is now ready, have fun!");
	}

	@Override
	public boolean navigateTo() {
		super.navigateTo();
		super.login(super.userName, super.password);
		return super.gotoHospitalityPage();
	}
}
