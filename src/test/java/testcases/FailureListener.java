package testcases;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.IAnnotationTransformer;
import org.testng.ITestListener;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;

public class FailureListener implements ITestListener, IAnnotationTransformer {
    protected final static Logger logger = LogManager.getLogger(FailureListener.class.getName());

    static {
        // Ensure Log4j is configured before any listener logging happens.
        // TestNG listeners can be instantiated before @BeforeSuite.
        try {
            boolean hasAppenders = LogManager.getRootLogger().getAllAppenders().hasMoreElements();
            if (!hasAppenders) {
                URL configUrl = Thread.currentThread().getContextClassLoader().getResource("log4j-config.xml");
                if (configUrl != null) {
                    DOMConfigurator.configure(configUrl);
                } else {
                    BasicConfigurator.configure();
                }
            }
        } catch (Throwable ignored) {
            // Last-resort: never prevent tests from running due to logging bootstrap
        }
    }

    @Override
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
