package com.tutorial.htmlparser;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class HtmlParserDemo extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(HtmlParserDemo.class.getName());
	
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
		logger.info("Resonse: " + response.asString());
		logger.info("response.headers(): " + response.headers());
		
		Document doc = Jsoup.parse(response.asString());
		String title = doc.title();
		String body = doc.body().text();
		logger.info("Title: " + title);
		logger.info("Body: " + body);
		
        Elements orderConfig = doc.getElementsByClass("order-config"); 
        logger.info("Elements: " + orderConfig);
	}
}