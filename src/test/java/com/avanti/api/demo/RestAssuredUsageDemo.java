package com.avanti.api.demo;

import static org.hamcrest.core.IsEqual.equalTo;
import java.io.IOException;
import java.io.InputStream;
import static org.hamcrest.Matchers.hasItems;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.when;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.relevantcodes.extentreports.LogStatus;

// Usage Demo of RestAssured
public class RestAssuredUsageDemo extends ApiTestCase {

	protected final static Logger logger = LogManager.getLogger(RestAssuredUsageDemo.class.getName());

	@Test(priority = 1)
	public void testGetResponseAsString() {
		test = extent.startTest(" ID-01: Name in jsonPath should be C-3PO ");
		test.log(LogStatus.INFO, "ID-01: Name in jsonPath should be C-3PO ");
		Response response = get("http://swapi.co/api/people/2/?format=json").andReturn();
		String json = response.getBody().asString();
		logger.info(json);
		Assert.assertEquals((new JsonPath(json)).getString("name"), "C-3PO");
	}

	@Test(priority = 2)
	public void testGetResponseAsInputStream() throws IOException {
		test = extent.startTest(" ID-02: Test get response as InputStream and byte array ");
		test.log(LogStatus.INFO, "ID-02: Test get response as InputStream and byte array ");
		InputStream stream = get("http://swapi.co/api/people/2/?format=json").asInputStream();
		logger.info(stream.toString().length());
		stream.close();
		byte[] byteArray = get("http://services.groupkt.com/country/get/iso2code/cn").asByteArray();
		logger.info(byteArray.length);
	}

	@Test(priority = 3)
	public void testExtractDetailsUsingPath() {
		test = extent.startTest(" ID-03: Test extraction of details using path ");
		test.log(LogStatus.INFO, "ID-03: Test extraction of details using path ");
		String href = when().get("http://jsonplaceholder.typicode.com/photos/1").then().contentType(ContentType.JSON)
				.body("albumId", equalTo(1)).extract().path("url");
		logger.info(href);
		Response response = get(href).andReturn();
		String json = response.getBody().asString();
		logger.info(json.length());
	}

	@Test(priority = 5)
	public void testHasItemFunction() {
		test = extent.startTest(" ID-05: hasItems of get services.groupkt.com should function ");
		test.log(LogStatus.INFO, "ID-05: hasItems of get services.groupkt.com should function ");
		given().get("http://services.groupkt.com/country/get/all").then().statusCode(200)
				.body("RestResponse.result.name", hasItems("Afghanistan", "Albania", "Algeria"));
	}

	@Test(priority = 6)
	public void verifyMultipleContentsWithRoot() {
		test = extent.startTest(" ID-06: equalTo (root/detachRoot root) of get services.groupkt.com should function ");
		test.log(LogStatus.INFO, "ID-06: equalTo (root/detachRoot root) of get services.groupkt.com should function ");
		given().get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10/").then().root("CUSTOMER")
				.body("ID", equalTo("10")).body("FIRSTNAME", equalTo("Sue")).body("LASTNAME", equalTo("Fuller"))
				.detachRoot("CUSTOMER").body("CUSTOMER.STREET", equalTo("135 Upland Pl."))
				.body("CUSTOMER.CITY", equalTo("Dallas")).log().all();
	}
}
