package utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Utility for reading sample Excel data from the classpath.
public final class ExcelFileReader {
    private static final Logger logger = LogManager.getLogger(ExcelFileReader.class.getName());
    private static final String DEFAULT_RESOURCE = "/excel-test-data.xlsx";

    private ExcelFileReader() {
    }

    public static void main(String[] args) {
        logger.info("Reading Excel file...");
        printRows(readExcel(DEFAULT_RESOURCE));
    }

    // Backward-compatible method used by existing callers.
    public static void readExcel() {
        printRows(readExcel(DEFAULT_RESOURCE));
    }

    // Reads values from the first sheet and returns each row as a list of strings.
    public static List<List<String>> readExcel(String resourceName) {
        logger.info("Reading Excel file: " + resourceName);
        try (InputStream in = openClasspathResource(resourceName);
             XSSFWorkbook workbook = new XSSFWorkbook(in)) {
            return extractRows(workbook.getSheetAt(0));
        } catch (Exception e) {
            logger.error("Failed to read Excel file: " + resourceName, e);
            throw new IllegalStateException("Failed to read Excel file: " + resourceName, e);
        }
    }

    // Reads values and maps each row to header -> value using the first row as header.
    public static List<Map<String, String>> readAsMaps(String resourceName) {
        List<List<String>> rows = readExcel(resourceName);
        if (rows.isEmpty()) {
            return List.of();
        }

        List<String> headers = rows.get(0);
        List<Map<String, String>> mappedRows = new ArrayList<>();
        for (int rowIndex = 1; rowIndex < rows.size(); rowIndex++) {
            List<String> row = rows.get(rowIndex);
            Map<String, String> rowMap = new LinkedHashMap<>();
            for (int colIndex = 0; colIndex < headers.size(); colIndex++) {
                String key = headers.get(colIndex);
                String value = colIndex < row.size() ? row.get(colIndex) : "";
                rowMap.put(key, value);
            }
            mappedRows.add(rowMap);
        }

        return mappedRows;
    }

    public static List<Map<String, String>> readAsMaps() {
        return readAsMaps(DEFAULT_RESOURCE);
    }

    private static InputStream openClasspathResource(String resourceName) {
        InputStream in = ExcelFileReader.class.getResourceAsStream(resourceName);
        if (in == null) {
            throw new IllegalStateException("Excel resource not found on classpath: " + resourceName
                    + " (place it under src/main/resources)");
        }
        return in;
    }

    private static List<List<String>> extractRows(XSSFSheet sheet) {
        DataFormatter formatter = new DataFormatter();
        List<List<String>> rows = new ArrayList<>();

        for (Row row : sheet) {
            List<String> values = new ArrayList<>();
            for (Cell cell : row) {
                values.add(formatter.formatCellValue(cell));
            }
            rows.add(values);
        }

        return rows;
    }

    private static void printRows(List<List<String>> rows) {
        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            List<String> row = rows.get(rowIndex);
            logger.info("Row " + rowIndex + ":");
            logger.info(String.join("\t", row));
        }
    }
}
