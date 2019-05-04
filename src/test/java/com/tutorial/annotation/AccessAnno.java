package com.tutorial.annotation;

import java.lang.reflect.Method;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.relevantcodes.extentreports.LogStatus;

public class AccessAnno extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(ApplyAnno.class.getName());
	
	@Test(priority = 1)
	public void testAccessAnnotationData() throws NoSuchMethodException, SecurityException {
		test = extent.startTest("Annotation: Anno apply and access simple example");
		test.log(LogStatus.INFO, "Annotation: Anno apply and access simple example");

		ApplyAnno applyAnno = new ApplyAnno();
		Method method = applyAnno.getClass().getMethod("applyAnnotation");
		AnnoDemo anno = method.getAnnotation(AnnoDemo.class);
		logger.info("Integer value is: " + anno.intValue());
		logger.info("String value is: " + anno.strValue());
	}
}							