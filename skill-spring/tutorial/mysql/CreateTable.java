package tutorial.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateTable {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/slothdb";
        String user = "slothuser";
        String pass = "slothpass123";

        String sql = """
                CREATE TABLE IF NOT EXISTS hello_test (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(50) NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
                """;

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table created (or already exists): hello_test");
        }
    }
}