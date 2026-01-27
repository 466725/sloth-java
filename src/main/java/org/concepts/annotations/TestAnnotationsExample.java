package org.concepts.annotations;

import org.testng.annotations.*;

public class TestAnnotationsExample {
    @BeforeClass
    public static void beforeClass() {
        System.out.println("========================Before Class=======================");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("========================After Class========================");
    }

    @BeforeTest
    public void beforeTest() {

        System.out.println("=========================Before Test=======================");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("=========================After Test========================");
    }

    @Test(priority = 3)
    @TestCaseAnno(testCaseID = 466725, description = "Verify annotation lookup on method")
    public void printAnnoValueTest() throws Exception {
        System.out.println("============================333============================");
        TestCaseAnno testCaseAnno = TestAnnotationsExample.class
                .getMethod("printAnnoValueTest")
                .getAnnotation(TestCaseAnno.class);
        System.out.println("Test case ID should be 466725, actual is: " + testCaseAnno.testCaseID());
        System.out.println("Test case description: " + testCaseAnno.description());
        System.out.println("============================333============================");
    }
}
