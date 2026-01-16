package com.tutorial.csv;

import config.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class CsvFileHandler {
    public static void printCSV() throws Exception {
        File file = new File(Constants.RESOURCE_FOLDER + Constants.THEATRE_SHOWTIME_CSV);
        String filePath = file.getAbsolutePath();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split by comma, assuming standard CSV format
                String[] record = line.split(",");

                // Ensure the record has enough columns before accessing indices
                if (record.length > 5) {
                    String jsonBody = "[\r\n    {"
                            + "\r\n        \"ITTicketTypeID\": " + record[3].trim() + ","
                            + "\r\n        \"TicketTypeCode\": \"" + record[4].trim() + "\","
                            + "\r\n        \"TicketCode\": \"" + record[5].trim() + "\","
                            + "\r\n        \"Quantity\": 1,"
                            + "\r\n        \"ITSessionId\": " + record[2].trim() + ""
                            + "\r\n    }\r\n]";
                    System.out.println(jsonBody);
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
