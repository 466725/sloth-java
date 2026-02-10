package org.concepts.annotations;

import org.testng.annotations.Test;

public class AnnotationsExampleTest {
    @Test(priority = 3)
    @TestcaseAnnotation(testCaseID = 466725, description = "Verify annotation lookup on method")
    public void printAnnoValueTest() throws Exception {
        System.out.println("============================333============================");
        TestcaseAnnotation testCaseAnno = AnnotationsExampleTest.class
                .getMethod("printAnnoValueTest")
                .getAnnotation(TestcaseAnnotation.class);
        System.out.println("Test case ID should be 466725, actual is: " + testCaseAnno.testCaseID());
        System.out.println("Test case description: " + testCaseAnno.description());
        System.out.println("============================333============================");
    }
}
