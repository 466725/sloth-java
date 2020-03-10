package com.tutorial.csv;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import config.Constants;

public class CsvFileHandler {
	public static void printCSV() throws Exception {
		File file = new File(Constants.RESOURCE_FOLDER + Constants.THEATRE_SHOWTIME_CSV);
		String filePath = file.getAbsolutePath();
		Reader in = new FileReader(filePath);
		Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
		for (CSVRecord record : records) {
			String jsonBody = "[\r\n    {"
					+ "\r\n        \"ITTicketTypeID\": " + record.get(3) + ","
					+ "\r\n        \"TicketTypeCode\": \"" + record.get(4) + "\","
					+ "\r\n        \"TicketCode\": \"" + record.get(5) + "\","
					+ "\r\n        \"Quantity\": 1,"
					+ "\r\n        \"ITSessionId\": " + record.get(2) + ""
					+ "\r\n    }\r\n]";
			System.out.println(jsonBody);
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
