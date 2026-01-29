package org.concepts.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestcaseAnnotation {
    int testCaseID() default 2013;

    String description() default "Customized TestcaseAnnotation example";

    boolean enabled() default true;

}