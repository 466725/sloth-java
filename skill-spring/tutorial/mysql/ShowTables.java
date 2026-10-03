package tutorial.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ShowTables {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/slothdb";
        String user = "slothuser";
        String pass = "slothpass123";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement()) {

            stmt.execute("USE slothdb");
            try (ResultSet rs = stmt.executeQuery("SHOW TABLES")) {
                System.out.println("Tables in slothdb:");
                while (rs.next()) {
                    System.out.println("- " + rs.getString(1));
                }
            }
        }
    }
}