package unit;

import config.EnvironmentConfig;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

public class EnvironmentConfigTest {
    private static final String TEST_KEY = "SLOTH_ENV_CONFIG_TEST_KEY";

    @Test
    public void readsRootEnvFromRootAndModule() throws Exception {
        Path root = Files.createTempDirectory("sloth-env-");
        Path module = Files.createDirectory(root.resolve("test-automation-framework"));
        Path env = root.resolve(".env");
        try {
            Files.writeString(env, "# Local test fixture\n" + TEST_KEY + "=\"test-key\"\n");
            Assert.assertEquals(EnvironmentConfig.getRequired(TEST_KEY, root), "test-key");
            assertEquals(EnvironmentConfig.getRequired(TEST_KEY, module), "test-key");
        } finally {
            Files.deleteIfExists(env);
            Files.delete(module);
            Files.delete(root);
        }
    }

    @DataProvider
    public Object[][] missingValues() {
        return new Object[][] {{null}, {""}, {TEST_KEY + "=\n"}, {TEST_KEY + "=\"   \"\n"}};
    }

    @Test(dataProvider = "missingValues")
    public void rejectsMissingOrBlankValues(String content) throws Exception {
        Path root = Files.createTempDirectory("sloth-env-");
        Path boundary = Files.createDirectory(root.resolve(".git"));
        Path env = root.resolve(".env");
        try {
            if (content != null) {
                Files.writeString(env, content);
            }
            IllegalStateException error = expectThrows(IllegalStateException.class,
                    () -> EnvironmentConfig.getRequired(TEST_KEY, root));
            assertEquals(error.getMessage(), "Required configuration " + TEST_KEY
                    + " is missing or blank. Set the environment variable or add it to .env.");
        } finally {
            Files.deleteIfExists(env);
            Files.delete(boundary);
            Files.delete(root);
        }
    }

    @Test
    public void environmentOverridesFile() throws Exception {
        Path root = Files.createTempDirectory("sloth-env-");
        Path env = root.resolve(".env");
        try {
            Files.writeString(env, "PATH=file-value\n");
            assertEquals(EnvironmentConfig.getRequired("PATH", root), System.getenv("PATH"));
        } finally {
            Files.deleteIfExists(env);
            Files.delete(root);
        }
    }
}
