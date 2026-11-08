package core;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.ITestListener;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class FailureListener implements ITestListener, IAnnotationTransformer {
    protected final static Logger logger = LogManager.getLogger(FailureListener.class.getName());

    @Override
    @SuppressWarnings("rawtypes")
    public void transform(ITestAnnotation annotation,
                          Class testClass,
                          Constructor testConstructor,
                          Method testMethod) {

        // Apply only to real @Test methods (not configs) and don't override a custom retry analyzer.
        Class<?> retryAnalyzerClass = annotation.getRetryAnalyzerClass();
        boolean hasCustomRetry = retryAnalyzerClass != null
                && !retryAnalyzerClass.getName().equals("org.testng.internal.annotations.DisabledRetryAnalyzer");
        if (testMethod != null && !hasCustomRetry) {
            logger.info("Retry analyzer attached to: " + testMethod.getName());
            annotation.setRetryAnalyzer(FailureRetryAnalyzer.class);
        }
    }
}
