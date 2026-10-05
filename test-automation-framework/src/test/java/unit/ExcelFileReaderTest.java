package unit;

import org.junit.Assert;
import org.junit.Test;
import utils.ExcelFileReader;

import java.util.List;
import java.util.Map;

public class ExcelFileReaderTest {
    @Test
    public void readExcel_shouldReturnHeaderAndDataRows() {
        List<List<String>> rows = ExcelFileReader.readExcel("/excel-test-data.xlsx");

        Assert.assertEquals(2, rows.size());
        Assert.assertEquals("location_id", rows.get(0).get(0));
        Assert.assertEquals("1129", rows.get(1).get(0));
    }

    @Test
    public void readAsMaps_shouldReturnRowsMappedByHeader() {
        List<Map<String, String>> rows = ExcelFileReader.readAsMaps("/excel-test-data.xlsx");

        Assert.assertEquals(1, rows.size());
        Map<String, String> firstRow = rows.get(0);
        Assert.assertEquals("1129", firstRow.get("location_id"));
        Assert.assertEquals("YP", firstRow.get("ticket_code"));
        Assert.assertEquals("174", firstRow.get("Seats"));
    }

    @Test(expected = IllegalStateException.class)
    public void readExcel_shouldThrowForMissingResource() {
        ExcelFileReader.readExcel("/missing-file.xlsx");
    }
}
