package com.avanti.api.testcases;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.gurock.TestRailConnector;
import com.gurock.testrail.APIClient;
import com.relevantcodes.extentreports.LogStatus;

public class TestRailTest extends ApiTestCase {

	protected final static Logger logger = LogManager.getLogger(TestRailConnector.class.getName());
	protected final static APIClient client = new APIClient("https://avanti.testrail.com/");

	@Test(priority = 1)
	public void main() throws Exception {
		test = extent.startTest(" Simple example on how to use TestRail APIClient. ");
		test.log(LogStatus.INFO, "Simple example on how to use TestRail APIClient. ");
		client.setUser("testautomationavanti@gmail.com");
		client.setPassword("Avanti313");
		logger.info(client.toString());
		TestRailTest.getTestCase("get_case/1");
	}

	protected static void getTestCase(String testCaseID) throws Exception {
		JSONObject jsonObject = (JSONObject) client.sendGet(testCaseID);
		logger.info(jsonObject);
		logger.info(jsonObject.get("title"));
	}

	protected static void postTestResult(String testCaseID) throws Exception {
		@SuppressWarnings("rawtypes")
		Map<String, Comparable> data = new HashMap<String, Comparable>();
		data.put("status_id", new Integer(1));
		data.put("comment", "This test worked fine!");
		JSONObject jsonObject = (JSONObject) client.sendPost("add_result_for_case/1/1", data);
		logger.info(jsonObject);
	}
}
