package com.avanti.api;

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

import com.framework.templates.ApiTestCase;
import com.gurock.testrail.HttpResponseDataObject;
import com.openqa.annotations.DataSource;
import com.openqa.common.CSVHandler;
import com.openqa.common.RestApiHelper;
import com.openqa.utils.FileUtils;
import com.relevantcodes.extentreports.LogStatus;
import config.Constants;

/**
 * Test objective: Verify Sales Orders creation by post API
 */
@DataSource(source = Constants.xmlIntegrationCSV)
public class XmlIntegrationTests extends ApiTestCase
{
	
	protected final static Logger logger = LogManager.getLogger(XmlIntegrationTests.class.getName());
	
	@DataProvider(name = "csvData")
	public Iterator<Object[]> data(ITestContext context, Method method) throws Exception
	{
		return CSVHandler.getHashmapFromCSV(context, method);
	}
	
	@Test(dataProvider = "csvData")
	public void xmlLoaderVerification(HashMap<String, String> csv) throws Exception
	{
		test = extent.startTest(" XML Integration: " + csv.get("folder") + " Expected " + csv.get("expectedCode"));
		test.log(LogStatus.INFO, "XML Integration: " + csv.get("folder") + " Expected " + csv.get("expectedCode"));
		String path = new File(Constants.RESOURCE_FOLDER + csv.get("folder")).getAbsolutePath();
		String uri = FileUtils.buildURIFromFileContent(path);
		String data = FileUtils.convertFileContentToDataStream(path + "/content." + csv.get("dataExtension"));
		HttpResponseDataObject response = RestApiHelper.performPostRequest(uri, data);
		Assert.assertTrue(response.getStatusCode().compareTo(csv.get("expectedCode")) == 0);
		Assert.assertTrue(response.isCallSuccesfull() == Boolean.valueOf(csv.get("expectedResult")));
	}
}
