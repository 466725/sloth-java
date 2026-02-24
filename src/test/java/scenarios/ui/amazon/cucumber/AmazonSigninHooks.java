package scenarios.ui.amazon.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import webpages.BaseWebPage;

public class AmazonSigninHooks {

    @Before
    public void beforeScenario() {
        BaseWebPage.quitDriver();
    }

    @After
    public void afterScenario() {
        BaseWebPage.quitDriver();
    }
}
