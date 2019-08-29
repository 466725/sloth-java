package com.postman.demo;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * For the purpose of API test automation demo
 * 
 * @author Weipeng Zheng
 *
 */
public class DemoTest extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(DemoTest.class.getName());
	
	@Test(priority = 1)
	public static void firstDemoTestCase() throws IOException, ParseException {
		test = extent.startTest("Add the first store");
		
		Request request = new Request.Builder()
				.url("https://uat-search.cineplex.com/api/Search/Get?query=spider&pageSize=10")
				.get()
				.addHeader("cache-control", "no-cache")
				.addHeader("Postman-Token", "41bdacec-64bc-4959-9dc5-ab8e1b927083")
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();

		Assert.assertTrue(response.code() == 200);
	}
}
