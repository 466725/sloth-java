package utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

// Utility for reading sample Excel data from the classpath.
public final class ExcelFileReader {
    private final static Logger logger = LogManager.getLogger(ExcelFileReader.class.getName());

    private ExcelFileReader() {
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Reading Excel file...");
        readExcel();
    }

    // Reads and prints values from the first sheet of the sample workbook.
    public static void readExcel() {
        String resourceName = "/excel-test-data.xlsx";
        DataFormatter formatter = new DataFormatter();
        System.out.println("Reading Excel file: " + resourceName);
        try (InputStream in = ExcelFileReader.class.getResourceAsStream(resourceName)) {
            System.out.println("InputStream: " + in);
            if (in == null) {
                throw new IllegalStateException("Excel resource not found on classpath: " + resourceName
                        + " (place it under src/main/resources)");
            }

            System.out.println("Opened Excel resource: " + resourceName);

            try (XSSFWorkbook workbook = new XSSFWorkbook(in)) {
                XSSFSheet sheet = workbook.getSheetAt(0);
                System.out.println("Sheet name: " + sheet.getSheetName());
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    System.out.println("Row: " + row.getRowNum());
                    Iterator<Cell> cellIterator = row.cellIterator();
                    System.out.println("Cell: ");
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        System.out.println(formatter.formatCellValue(cell) + "\t");
                    }
                    System.out.println("Row: " + row.getRowNum());
                }
            }
        } catch (Exception e) {
            logger.error("Failed to read Excel file", e);
        }
    }
}
