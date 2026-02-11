package testcases;

import config.PropertiesFileReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class FailureRetryAnalyzer implements IRetryAnalyzer {
    protected final static Logger logger = LogManager.getLogger(FailureRetryAnalyzer.class.getName());
    private static final int MAX_RETRY_COUNT = PropertiesFileReader.getMaxRetryCount();

    @Override
    public boolean retry(ITestResult result) {
        int retryCount = 0;
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            logger.info("Retrying test: " + result.getName() + " - Attempt: " + retryCount + "/" + MAX_RETRY_COUNT);
            return true;
        }
        return false;
    }
}
