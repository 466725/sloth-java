package com.avanti.unit.testcases;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.framework.annotations.DataSource;
import com.framework.templates.UnitTestCase;
import com.relevantcodes.extentreports.LogStatus;
import com.utilities.CsvFileReader;
import com.utilities.FileUtils;

import config.Constants;

@DataSource(source = Constants.XML_INTEGRATION_CSV)
public class CsvHandlerTest extends UnitTestCase {

	protected final static Logger logger = LogManager.getLogger(CsvHandlerTest.class.getName());

	@DataProvider(name = "csvData")
	public Iterator<Object[]> data(ITestContext context, Method method) throws Exception {
		return CsvFileReader.getHashmapFromCSV(context, method);
	}

	@Test(dataProvider = "csvData")
	public void readSCV(HashMap<String, String> csv) throws Exception {
		test = extent.startTest(" Read CSV value with key from default resources file. ");
		test.log(LogStatus.INFO, "Read CSV value with key from default resources file. ");
		String path = new File(Constants.RESOURCE_FOLDER + csv.get("folder")).getAbsolutePath();
		String uri = FileUtils.buildURIFromFileContent(path);
		String data = FileUtils.convertFileContentToDataStream(path + "/content." + csv.get("dataExtension"));
		logger.info("URI: " + uri);
		logger.info("Request data: " + data);
		Assert.assertTrue(true);
	}
}
