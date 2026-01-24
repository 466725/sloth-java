package testcases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationTransformer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class FailureListener implements ITestListener, IAnnotationTransformer {
    protected final static Logger logger = LogManager.getLogger(FailureListener.class.getName());

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test started: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.info("Test failed: " + result.getName());
    }

    @Override
    public void transform(ITestAnnotation annotation,
                          Class testClass,
                          Constructor testConstructor,
                          Method testMethod) {

        // Apply only to real @Test methods (not configs) and don't override if already set.
        if (testMethod != null && annotation.getRetryAnalyzerClass() == null) {
            logger.info("Retry analyzer attached to: " + testMethod.getName());
            annotation.setRetryAnalyzer(FailureRetryAnalyzer.class);
        }
    }
}