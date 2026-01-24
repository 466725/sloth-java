package config;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// To be continued...
public final class ExcelReader {
    private ExcelReader() {
    }

    public static List<List<String>> readSheet(String filePath, String sheetName) throws IOException {
        try (InputStream in = new FileInputStream(filePath)) {
            return readSheet(in, sheetName);
        }
    }

    public static List<List<String>> readSheet(String filePath, int sheetIndex) throws IOException {
        try (InputStream in = new FileInputStream(filePath)) {
            return readSheet(in, sheetIndex);
        }
    }

    public static List<List<String>> readSheetFromClasspath(String resourcePath, String sheetName) throws IOException {
        try (InputStream in = openClasspathResource(resourcePath)) {
            return readSheet(in, sheetName);
        }
    }

    public static List<List<String>> readSheetFromClasspath(String resourcePath, int sheetIndex) throws IOException {
        try (InputStream in = openClasspathResource(resourcePath)) {
            return readSheet(in, sheetIndex);
        }
    }

    public static List<List<String>> readTestDataSheet(String sheetName) throws IOException {
        return readSheetFromClasspath("testData.xls", sheetName);
    }

    public static List<List<String>> readTestDataSheet() throws IOException {
        return readSheetFromClasspath("testData.xls", 0);
    }

    public static void main(String[] args) throws IOException {
        // Optional: proves whether the resource is actually visible at runtime
        URL url = ExcelReader.class.getResource("/testData.xls");
        System.out.println("testData.xls URL = " + url);

        List<List<String>> rows = readTestDataSheet();
        for (List<String> row : rows) {
            System.out.println(String.join("\t", row));
        }
    }

    private static InputStream openClasspathResource(String resourcePath) throws IOException {
        // Normalize to absolute-from-classpath-root for Class.getResourceAsStream
        String normalized = resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath;

        InputStream in = ExcelReader.class.getResourceAsStream(normalized);
        if (in == null) {
            throw new IOException(
                    "Resource not found on classpath: " + normalized + System.lineSeparator() +
                    "Expected it under src/main/resources (so it ends up in target/classes)."
            );
        }
        return in;
    }

    private static List<List<String>> readSheet(InputStream in, String sheetName) throws IOException {
        try (Workbook workbook = new HSSFWorkbook(in)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet not found: " + sheetName);
            }
            return readSheet(sheet);
        }
    }

    private static List<List<String>> readSheet(InputStream in, int sheetIndex) throws IOException {
        try (Workbook workbook = new HSSFWorkbook(in)) {
            if (sheetIndex < 0 || sheetIndex >= workbook.getNumberOfSheets()) {
                throw new IOException("Sheet index out of range: " + sheetIndex);
            }
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            return readSheet(sheet);
        }
    }

    private static List<List<String>> readSheet(Sheet sheet) {
        DataFormatter formatter = new DataFormatter();
        List<List<String>> rows = new ArrayList<>();
        for (Row row : sheet) {
            List<String> cells = new ArrayList<>();
            int lastCellNum = row.getLastCellNum();
            if (lastCellNum < 0) {
                rows.add(cells);
                continue;
            }
            for (int i = 0; i < lastCellNum; i++) {
                String value = formatter.formatCellValue(row.getCell(i));
                cells.add(value);
            }
            rows.add(cells);
        }
        return rows;
    }
}
