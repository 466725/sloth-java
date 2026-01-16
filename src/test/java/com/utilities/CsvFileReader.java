package com.utilities;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CsvFileReader {
    protected final static Logger logger = LogManager.getLogger(CsvFileReader.class.getName());
    private File sourceFile;
    private CSVReader csvReader;
    private String[] csvHeader;

    public CsvFileReader(File csvFile) throws Exception {
        this.sourceFile = csvFile;
        this.csvReader = new CSVReader(new FileReader(this.sourceFile));
        this.csvHeader = this.csvReader.readNext();
        boolean isValidHeader = true;
        for (String column : this.csvHeader)
            if (column == null || column.trim().length() == 0) {
                logger.fatal("Unexpected CSV file format! ");
                isValidHeader = false;
                break;
            }
        if (!isValidHeader)
            throw new Exception("CSV file must have valid [non-blank] column headers. ");
    }

    public String[] getCSVHeader() {
        return this.csvHeader;
    }

    /**
     * @return An ArrayList of HashMap data read from CSV file. Each HashMap data is
     * basically a csvHeader and a column value for a particular row
     * @throws Exception
     */
    public ArrayList<HashMap<String, String>> getHashmapList() throws Exception {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
        String[] csvRow;
        while ((csvRow = this.csvReader.readNext()) != null) {
            logger.info("CSV info: " + csvRow);
            HashMap<String, String> hashMap = new LinkedHashMap<String, String>();
            if (this.csvHeader.length < csvRow.length) {
                logger.fatal("Unexpected CSV file format! ");
                throw new Exception("Incorrect CSV format, data doesn't match header. ");
            }
            for (int i = 0; i < csvRow.length; i++) {
                hashMap.put(this.csvHeader[i], csvRow[i]);
            }
            arrayList.add(hashMap);
        }
        this.csvReader.close();
        return arrayList;
    }

    /**
     * @param csvGroup One or more group names from the CSV divided by commas
     * @param xmlGroup One or more group names from the XML divided by commas
     * @return
     */
    private static boolean matchGroups(String csvGroup, String xmlGroup) {
        String[] csvGroups = csvGroup.split(",");
        String[] xmlGroups = xmlGroup.split(",");
        for (String csv : csvGroups) {
            for (String xml : xmlGroups) {
                if (csv.equalsIgnoreCase(xml.trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return meta data(scenario, dataGroup) for a given data sourceFile file
     *
     * @param csvFilePath
     * @return
     */
    public static HashMap<String, HashMap<String, String>> retrieveMetaData(String csvFilePath) throws Exception {
        HashMap<String, HashMap<String, String>> metaData = new HashMap<String, HashMap<String, String>>();
        URL furl = Thread.currentThread().getContextClassLoader().getResource(csvFilePath);
        if (furl == null)
            throw new Exception("CSV file provided is not valid: " + csvFilePath);
        File csvFile = new File(furl.toURI());
        CsvFileReader csvDf = new CsvFileReader(csvFile);
        ArrayList<HashMap<String, String>> inputData = csvDf.getHashmapList();
        for (HashMap<String, String> dataRow : inputData) {
            String csvScenario = dataRow.get("scenario");
            String csvDataGroup = dataRow.get("dataGroup");
            if (StringUtils.isEmpty(csvScenario) && StringUtils.isEmpty(csvDataGroup))
                continue;
            HashMap<String, String> dataGroup = new HashMap<String, String>();
            dataGroup.put("dataGroup", csvDataGroup);
            metaData.put(csvScenario, dataGroup);
        }
        return metaData;
    }
}
