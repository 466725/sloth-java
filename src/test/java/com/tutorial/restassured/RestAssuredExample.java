package com.tutorial.restassured;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestAssuredExample extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(RestAssuredExample.class.getName());
	
	@Test(priority = 1)
	public void getAllCategoryWithRestAssured() {
		test = extent.startTest("Category: Get all category");
		test.log(LogStatus.INFO, "Category: Get all category");
		
		Response response = RestAssured.given()
				.header("Content-Type", "application/json")
				.when().get("/v1/concept/categories")
				.then().log().ifValidationFails().statusCode(502)
				.extract()
				.response();

		logger.info("Response toString(): " + response.toString());
		logger.info("Resonse getHeaders(): " + response.getHeaders());
		logger.info("Resonse time(): " + response.time());
		logger.info("Response code(): " + response.getStatusCode());
		Assert.assertTrue(response.getStatusCode() == 502);
	}
}
