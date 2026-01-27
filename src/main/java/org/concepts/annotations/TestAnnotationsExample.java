package org.concepts.annotations;

import org.testng.annotations.*;

import java.lang.reflect.Method;

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

    @Test(priority = 1)
    @TestCaseAnno(testCaseID = 466725)
    public void printAnnoDescriptionTest() throws Exception {
        System.out.println("============================111============================");
        for(Method method : TestAnnotationsExample.class.getDeclaredMethods()){
            if(method.isAnnotationPresent(TestCaseAnno.class)){
                TestCaseAnno testCaseAnno = method.getAnnotation(TestCaseAnno.class);
                System.out.println("Test Case ID: " + testCaseAnno.testCaseID());
            }
        }
        System.out.println("============================111============================");
    }

    @Test(priority = 3)
    @TestCaseAnno(testCaseID = 466725)
    public void printAnnoValueTest() throws Exception {
        System.out.println("============================333============================");
        TestCaseAnno testCaseAnno = TestAnnotationsExample.class
                .getMethod("printAnnoValueTest")
                .getAnnotation(TestCaseAnno.class);
        System.out.println("Should be 466725, actual is: " + testCaseAnno.testCaseID());
        System.out.println("============================333============================");
    }
}
