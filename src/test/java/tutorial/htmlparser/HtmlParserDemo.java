package tutorial.htmlparser;

import com.framework.testcases.ApiTestCase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;

public class HtmlParserDemo extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(HtmlParserDemo.class.getName());

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