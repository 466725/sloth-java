package scenarios.ui.amazon.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/amazon_invalid_login.feature",
        glue = "scenarios.ui.amazon.cucumber",
        plugin = {
                "pretty",
                "summary",
                "html:target/cucumber/amazon-invalid-login-report.html"
        }
)
public class AmazonInvalidLoginCucumberTest extends AbstractTestNGCucumberTests {
}
