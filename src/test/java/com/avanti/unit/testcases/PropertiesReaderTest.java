package com.avanti.unit.testcases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.templates.UnitTestCase;
import com.relevantcodes.extentreports.LogStatus;
import com.utilities.PropertiesFileReader;

public class PropertiesReaderTest extends UnitTestCase {
	protected final static Logger logger = LogManager.getLogger(PropertiesReaderTest.class.getName());

	@Test(priority = 1)
	public void readPropertyWithKey() {
		test = extent.startTest(" Read property value with key from default config file. ");
		test.log(LogStatus.INFO, "Read property value with key from default config file. ");
		Assert.assertTrue(PropertiesFileReader.getProperty("webdriver.chrome.driver").equals("lib/chromedriver.exe"));
		Assert.assertTrue(PropertiesFileReader.getProperty("selenium.hub.url")
				.equals("http://devselenium.toronto.com:4444/wd/hub"));
	}
}
