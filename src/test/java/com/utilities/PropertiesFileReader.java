package com.utilities;

import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import config.Constants;

public class PropertiesFileReader {
	protected final static Logger logger = LogManager.getLogger(PropertiesFileReader.class.getName());
	private static Properties properties = null;

	private static Properties initializePropertyFile() throws Exception {
		Properties properties = new Properties();
		properties.load(FileUtils.openFileAsInputStream(Constants.propertyFile));
		logger.info("Initializing property file \"" + Constants.propertyFile + "\". ");
		return properties;
	}

	public static Properties getPropertyFile() {
		try {
			if (properties == null) {
				synchronized (PropertiesFileReader.class) {
					if (properties == null)
						properties = initializePropertyFile();
				}
				return properties;
			}
			logger.info("Property file \"" + Constants.propertyFile + "\" is already loaded. ");
			return properties;
		} catch (Exception e) {
			logger.fatal("Failed to load property file \"" + Constants.propertyFile + "\". ");
			logger.error("Exception is: ", e);
			return null;
		}
	}

	public static String getProperty(String key) {
		return getPropertyFile().getProperty(key);
	}

	public static void main(String[] args) throws Exception {
		System.out.println(PropertiesFileReader.getProperty("webdriver.chrome.driver"));
	}
}
