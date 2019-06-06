package com.avanti.unit.self;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.templates.UnitTestCase;
import com.openqa.common.PropertiesReader;
import com.relevantcodes.extentreports.LogStatus;

public class PropertiesReaderTests extends UnitTestCase
{
	
	protected final static Logger logger = LogManager.getLogger(PropertiesReaderTests.class.getName());
	
	@Test(priority = 1)
	public void readPropertyWithKey()
	{
		test = extent.startTest(" Read property value with key from default config file. ");
		test.log(LogStatus.INFO, "Read property value with key from default config file. ");
		Assert.assertTrue(PropertiesReader.getProperty("webdriver.chrome.driver").equals("lib/chromedriver.exe"));
		Assert.assertTrue(PropertiesReader.getProperty("selenium.hub.url").equals("http://devselenium.toronto.com:4444/wd/hub"));
	}
}
