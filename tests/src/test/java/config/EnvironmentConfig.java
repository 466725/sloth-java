package config;

import io.github.cdimascio.dotenv.Dotenv;

import java.nio.file.Files;
import java.nio.file.Path;

public final class EnvironmentConfig {
    private EnvironmentConfig() {
    }

    public static String getRequired(String name) {
        return getRequired(name, Path.of(System.getProperty("user.dir")));
    }

    public static String getRequired(String name, Path workingDirectory) {
        Path directory = workingDirectory.toAbsolutePath();
        while (!Files.exists(directory.resolve(".env"))
                && !Files.exists(directory.resolve(".git"))
                && directory.getParent() != null) {
            directory = directory.getParent();
        }

        String value = Dotenv.configure()
                .directory(directory.toString())
                .ignoreIfMissing()
                .load()
                .get(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Required configuration " + name
                    + " is missing or blank. Set the environment variable or add it to .env.");
        }
        return value;
    }
}
