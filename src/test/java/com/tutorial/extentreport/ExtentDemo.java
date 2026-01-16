package com.tutorial.extentreport;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ExtentDemo {
    static ExtentTest test;
    static ExtentReports report;
    @BeforeClass
    public static void startTest()
    {
        report = new ExtentReports(System.getProperty("user.dir")+"ExtentReportResults.html");
        test = report.startTest("ExtentDemo");
    }
    @Test
    public void extentReportsDemo()
    {
            test.log(LogStatus.PASS, "Looks all good! ");
    }
    @AfterClass
    public static void endTest()
    {
        report.endTest(test);
        report.flush();
    }
}
