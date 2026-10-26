package ui.tangerine.cucumber;

import io.cucumber.core.cli.Main;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class CucumberRunnerTest {
    @Test
    public void runCucumberFeatures() {
        byte exitStatus = Main.run(
                new String[]{"--glue", "ui.tangerine.cucumber", "--plugin", "pretty", "classpath:features"},
                Objects.requireNonNull(Thread.currentThread().getContextClassLoader()));

        Assert.assertEquals(exitStatus, (byte) 0, "Cucumber scenarios failed");
    }
}
