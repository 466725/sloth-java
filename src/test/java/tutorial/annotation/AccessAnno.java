package tutorial.annotation;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import testcases.ApiTestCase;

import java.lang.reflect.Method;

public class AccessAnno extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(AccessAnno.class.getName());

    @Test(priority = 1)
    public void testAccessAnnotationData() throws NoSuchMethodException, SecurityException {
        ApplyAnno applyAnno = new ApplyAnno();
        Method method = applyAnno.getClass().getMethod("applyAnnotation");
        AnnoDemo anno = method.getAnnotation(AnnoDemo.class);
        logger.info("Integer value is: " + anno.intValue());
        logger.info("String value is: " + anno.strValue());
    }
}							