package com.framework.helpers;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.relevantcodes.extentreports.ExtentReports;

import config.Constants;

public class ExtentReportHelper {
	private final static Logger logger = LogManager.getLogger(ExtentReportHelper.class.getName());
	private static String config = Constants.RESOURCE_FOLDER + Constants.EXTENT_REPORT_CONFIG_FILENAME;
	private static String reportFileFolder = Constants.TEST_REPORT_FOLDER;

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	private static LocalDate date = LocalDate.now();

	private static String reportFileName = "Test-Automation-" + formatter.format(date) + ".html";
	private static String reportFile = reportFileFolder + reportFileName;

	private ExtentReportHelper() {
		logger.info("I am here to guarantee singleton! ");
	}

	private static class ReportMaster {
		private static ExtentReports extent = null;
	}

	public static ExtentReports getExtentReporter(String browser) {
		if (ReportMaster.extent == null) {
			String configAbsolutePath = new File(config).getAbsolutePath();
			ReportMaster.extent = new ExtentReports(reportFile, true);
			ReportMaster.extent.loadConfig(new File(configAbsolutePath));
			ReportMaster.extent.addSystemInfo("Browser", browser);
		}
		logger.info(reportFile);
		logger.info(config);
		return ReportMaster.extent;
	}
}