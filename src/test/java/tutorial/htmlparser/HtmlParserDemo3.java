package tutorial.htmlparser;

import com.framework.testcases.ApiTestCase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.StringReader;

public class HtmlParserDemo3 extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(HtmlParserDemo3.class.getName());

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

        String text = response.asString();
        ParserDelegator delegator = new ParserDelegator();
        final StringBuilder cleaned = new StringBuilder();

        HTMLEditorKit.ParserCallback callback = new HTMLEditorKit.ParserCallback() {
            public void handleText(char[] data, int pos) {
                cleaned.append(new String(data)).append(' ');
            }
        };

        try {
            delegator.parse(new StringReader(text), callback, false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("==================================");
        System.out.println(cleaned.toString());
        System.out.println("==================================");
    }
}