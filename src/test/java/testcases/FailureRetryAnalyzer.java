package testcases;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class FailureRetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 3;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if(retryCount <= MAX_RETRY_COUNT && !iTestResult.isSuccess()){
            retryCount++;
            System.out.println("Retrying test: " + iTestResult.getName() + " - Attempt: " + retryCount);
            return true;
        }
        return false;
    }
}
