package csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class CsvFileHandler {
    public static void printCSV() throws Exception {
        File file = new File("./skill-spring/tutorial/csv/load_test_data_enroll_students.csv");
        String filePath = file.getAbsolutePath();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split by comma, assuming standard CSV format
                String[] record = line.split(",");
                System.out.println("Record: ");
                for (String field : record) {
                    System.out.println(field);
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Let's get csv file handled! ");
        try {
            printCSV();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
