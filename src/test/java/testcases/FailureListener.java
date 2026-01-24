package testcases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationTransformer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class FailureListener extends GuiTestCase implements ITestListener, IAnnotationTransformer {
    protected final static Logger logger = LogManager.getLogger(FailureListener.class.getName());

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test started");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.info("Trying to rerun failed test: " + result.getName());
    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        logger.info("Retry analyzer set for failed test. ");
        annotation.setRetryAnalyzer(FailureRetryAnalyzer.class);
    }
}