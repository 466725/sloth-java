package com.volante.api.testcases.store;

import com.framework.templates.ApiTestCase;
import com.volante.api.testcases.TokenManagementTest;
import io.restassured.response.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Store API test cases are all here, Add + Update + Delete...
 *
 * @author Weipeng Zheng
 *
 */
public class StoreActionTest extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(StoreActionTest.class.getName());

    @Test(priority = 1)
    public static void generateToken() throws IOException, ParseException {
        TokenManagementTest.generateTokenWithJsonFileBody();
    }

    @Test(priority = 3, description = "VOL-888")
    public static void getAllStores() throws IOException, ParseException {
        Response getStoreResponse = StoreAction.getAllStores();
        logger.info(getStoreResponse.toString());
        logger.info(getStoreResponse.body().asString());
        logger.info(getStoreResponse.headers());

        Assert.assertTrue(getStoreResponse.getStatusCode() == 200);
    }

    @Test(priority = 5, description = "VOL-888")
    public static void addFirstStore() throws IOException, ParseException {
        Response addStoreResponse = StoreAction.createStore("StoreName", 4952206, 201);
        logger.info(addStoreResponse.toString());
        logger.info(addStoreResponse.body().asString());
        logger.info(addStoreResponse.headers());

        Assert.assertTrue(addStoreResponse.getStatusCode() == 201);
    }
}
