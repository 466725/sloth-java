package testcases;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.IFactoryAnnotation;
import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationTransformer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FailureListener extends GuiTestCase implements ITestListener, IAnnotationTransformer {
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test started");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test failed");
        System.out.println("Test name: " + result.getName());
        System.out.println("Test status: " + result.getStatus());
        System.out.println("Test exception: " + result.getThrowable());
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);
        String dynamicScreenshotName = result.getName() + formattedDate + ".png";
        System.out.println("Test failed at: " + formattedDate);
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File(System.getProperty("user.dir") + File.separator + dynamicScreenshotName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method method) {
        annotation.setRetryAnalyzer(FailureRetryAnalyzer.class);
    }
}
