package com.framework.helpers;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.relevantcodes.extentreports.ExtentReports;

import config.Constants;

/**
 * Extent report helper class
 * 
 * @author Weipeng Zheng
 *
 */
public class ExtentReportHelper {
	private final static Logger logger = LogManager.getLogger(ExtentReportHelper.class.getName());
	private static String config = Constants.RESOURCE_FOLDER + Constants.EXTENT_REPORT_CONFIG;
	private static String reportFileFolder = Constants.TEST_REPORT_FOLDER;

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	private static LocalDate date = LocalDate.now();

	private static String reportFileName = "Test-Automation-" + formatter.format(date) + ".html";
	private static String reportFile = reportFileFolder + reportFileName;

	/**
	 * Private Constructor, to make this class a singleton one.
	 * 
	 */
	private ExtentReportHelper() {
		logger.info("I am here to guarantee singleton! ");
	}

	/**
	 * Inner class, to encapsulate database statement securely
	 * 
	 */
	private static class ReportMaster {
		private static ExtentReports extent = null;
	}

	/**
	 * Get extent reporter
	 * 
	 * @param Browser to display browser info on extent report
	 * @return Extent report, test report with environment info
	 */
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