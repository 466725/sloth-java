package concepts.annotations;

import org.testng.annotations.Test;

public class AnnotationsExampleTest {
    @Test(priority = 3)
    @TestCaseAnnotation(testCaseId = 466725, description = "Verify annotation lookup on method")
    public void printAnnoValueTest() throws Exception {
        System.out.println("============================333============================");
        TestCaseAnnotation testCaseAnno = AnnotationsExampleTest.class
                .getMethod("printAnnoValueTest")
                .getAnnotation(TestCaseAnnotation.class);
        System.out.println("Test case ID: " + testCaseAnno.testCaseId());
        System.out.println("Test case description: " + testCaseAnno.description());
        System.out.println("============================333============================");
    }
}
