package com.volante.api.testcases.store;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.framework.templates.ApiTestCase;

import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;

/**
 * Encapsulates API based actions for Store
 * 
 * @author Weipeng Zheng
 *
 */
public class StoreAction {
	protected final static Logger logger = LogManager.getLogger(StoreAction.class.getName());

	public static Response createStore(String storeName, int storeId, int expectedResponseCode) throws IOException, ParseException {
		RestAssured.baseURI = "https://dev.volantecloud.com";
		StoreModal myStore = new StoreModal();
		myStore.setCityId(storeId);

		Response response = RestAssured.given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer" + ApiTestCase.globalToken)
				.body(myStore, ObjectMapperType.GSON)
				.when()
				.post("/api/store/v1/stores")
				.then()
				.log()
				.ifValidationFails()
				.statusCode(expectedResponseCode)
				.extract()
				.response();

		return response;
	}
	
	public static Response getAllStores() throws IOException, ParseException {
		RestAssured.baseURI = "https://dev.volantecloud.com";

		Response response = RestAssured.given()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer" + ApiTestCase.globalToken)
				.when()
				.get("/api/store/v1/stores/?size=20")
				.then()
				.log()
				.ifValidationFails()
				.statusCode(200)
				.extract()
				.response();

		return response;
	}
}
