package com.tutorial.htmlparser;

import com.framework.templates.ApiTestCase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

public class HtmlParserDemo2 extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(HtmlParserDemo2.class.getName());

    @Test(priority = 2)
    public void getAllCategoryWithRestAssured() {
        RestAssured.baseURI = "https://uat-www.cineplex.com";

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .when()
                .get("/")
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