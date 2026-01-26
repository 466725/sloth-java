package config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// To be continued...
public final class ExcelFileReader {
    private final static Logger logger = LogManager.getLogger(ExcelFileReader.class.getName());

    private ExcelFileReader() {
    }

    public static List<List<String>> readSheetFromClasspath(String resourcePath, int sheetIndex) throws IOException {
        try (InputStream in = openClasspathResource(resourcePath)) {
            return readSheet(in, sheetIndex);
        }
    }

    public static List<List<String>> readTestDataSheet() throws IOException {
        return readSheetFromClasspath("excel-test-data.xls", 0);
    }

    private static InputStream openClasspathResource(String resourcePath) throws IOException {
        // Normalize to absolute-from-classpath-root for Class.getResourceAsStream
        String normalized = resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath;

        InputStream in = ExcelFileReader.class.getResourceAsStream(normalized);
        if (in == null) {
            throw new IOException(
                    "Resource not found on classpath: " + normalized + System.lineSeparator() +
                            "Expected it under src/main/resources (so it ends up in target/classes)."
            );
        }
        return in;
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

    public static void main(String[] args) throws IOException {
        // Optional: proves whether the resource is actually visible at runtime
        URL url = ExcelFileReader.class.getResource("/excel-test-data.xls");
        System.out.println("excel-test-data.xls URL = " + url);

        List<List<String>> rows = readTestDataSheet();
        for (List<String> row : rows) {
            System.out.println(String.join("\t", row));
        }
    }
}
