package com.tutorial.restassured;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.relevantcodes.extentreports.LogStatus;
import com.test.api.testcases.TokenManagementTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestAssuredDemo extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(RestAssuredDemo.class.getName());
	
	@Test(priority = 1)
	public static void generateToken() throws IOException, ParseException {
		test = extent.startTest("Generate Token");
		
		TokenManagementTest.generateTokenWithJsonFileBody();
	}
	
	@Test(priority = 2)
	public void getAllCategoryWithRestAssured() {
		test = extent.startTest("Category: Get all category");
		test.log(LogStatus.INFO, "Category: Get all category");
		RestAssured.baseURI = "https://dev.volantecloud.com";
		
		Response response = RestAssured.given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer" + ApiTestCase.globalToken)
				.when()
				.get("/api/store/v1/stores/?size=200")
				.then()
				.log()
				.ifValidationFails()
				.statusCode(200)
				.extract()
				.response();

		logger.info("Resonse time(): " + response.time());
	}
}