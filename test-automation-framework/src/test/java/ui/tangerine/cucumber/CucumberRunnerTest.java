package ui.tangerine.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "classpath:features",
        glue = "ui.tangerine.cucumber",
        plugin = {"pretty"}
)
public class CucumberRunnerTest extends AbstractTestNGCucumberTests {
}
