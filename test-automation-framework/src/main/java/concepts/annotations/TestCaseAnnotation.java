package concepts.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a test method with metadata for test tracking and reporting.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestCaseAnnotation {
    /**
     * External test case identifier from a test management system.
     */
    int testCaseId() default 2013;

    /**
     * Human-readable description of what the test validates.
     */
    String description() default "Custom TestCaseAnnotation example";

    /**
     * Controls whether the annotated test is active.
     */
    boolean enabled() default true;
}
