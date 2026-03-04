package tutorial.json;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.testng.annotations.Test;
import testcases.ApiTestCase;

public class JsonSimpleDemo extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(JsonSimpleDemo.class.getName());

    @Test(priority = 1)
    public void jsonSimpleDemoTestOne() throws ParseException {
        JSONParser myParser = new JSONParser();
        String jsonExampleOne = "[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";

        Object jsonObject = myParser.parse(jsonExampleOne);

        JSONArray jsonArray = (JSONArray) jsonObject;
        logger.info("The second element of jsonArray: " + jsonArray.get(1));
        JSONObject jsonObjectTwo = (JSONObject) jsonArray.get(1);
        logger.info("Field \"1\" of jsonArray is: " + jsonObjectTwo.get("1"));

        logger.info("myParser.parse(\"{}\"): " + myParser.parse("{}"));
        logger.info("myParser.parse(\"[5,]\"): " + myParser.parse("[5,]"));
        logger.info("myParser.parse(\"[5,,2]\"): " + myParser.parse("[5,,2]"));

        Assert.assertTrue(true);
    }
}