package com.tutorial.htmlparser;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class HtmlParserDemo2 extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(HtmlParserDemo2.class.getName());
	
	@Test(priority = 2)
	public void getAllCategoryWithRestAssured() {
		RestAssured.baseURI = "https://uat-onlineticketing.cineplex.com";
		
		Response response = RestAssured.given()
				.header("Content-Type", "application/json")
				.when()
				.get("/PaymentOptions/870eb3cd-e35d-4f23-91f4-2ae6cfbf6eea")
				.then()
				.log()
				.ifValidationFails()
				.statusCode(200)
				.extract()
				.response();
		//logger.info("Resonse: " + response.asString());
		//logger.info("response.headers(): " + response.headers());

		String responseAsString = response.asString();
		int begining = responseAsString.indexOf("\"ResourcesGroupName\":\"");
		int ending = responseAsString.indexOf("\",\"ResourcesCacheTimeToKeepInSeconds\"");
		String resourcesGroupName = responseAsString.substring(begining + 22, ending);
		
		logger.info("=====================================================================");
		logger.info("=====================================================================");
		logger.info("=====================================================================");
		logger.info(begining);
		logger.info(ending);
		logger.info(resourcesGroupName);
		logger.info("=====================================================================");
		logger.info("=====================================================================");
		logger.info("=====================================================================");
	}
}