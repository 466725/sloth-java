package com.test.api.testcases.storeview;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.test.api.testcases.TokenManagementTest;

/**
 * Store view API test cases are all here, Add + Update + Delete...
 * 
 * @author Weipeng Zheng
 *
 */
public class StoreViewTest extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(StoreViewTest.class.getName());

	@Test(priority = 1)
	public static void generateToken() throws IOException, ParseException {
		test = extent.startTest("Store view API test: Generate Token");
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(TokenManagementTest.generateTokenStep());

		softly.assertAll();
	}
}
