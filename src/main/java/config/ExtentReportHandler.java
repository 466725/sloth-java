package config;

import com.relevantcodes.extentreports.ExtentReports;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public final class ExtentReportHandler {
    private final static Logger logger = LogManager.getLogger(ExtentReportHandler.class.getName());

    private ExtentReportHandler() {
    }

    public static void loadConfig(ExtentReports report, Logger logger) {
        if (report == null) {
            throw new IllegalArgumentException("ExtentReports must not be null");
        }

        URL configUrl = ExtentReportHandler.class.getClassLoader().getResource("extent-report-config.xml");
        if (configUrl != null) {
            try {
                report.loadConfig(new File(configUrl.toURI()));
                return;
            } catch (URISyntaxException e) {
                if (logger != null) {
                    logger.warn("Failed to load extent report config from classpath: " + configUrl, e);
                }
            }
        }

        File mainResourcesConfig = new File("src/main/resources/extent-report-config.xml");
        if (mainResourcesConfig.exists()) {
            report.loadConfig(mainResourcesConfig);
            return;
        }

        if (logger != null) {
            logger.warn("Extent report config not found (classpath or resources): extent-report-config.xml");
        }
    }
}
