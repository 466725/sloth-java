package com.tutorial.assertj;

import com.framework.templates.ApiTestCase;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

public class AssertjSimpleDemo extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(AssertjSimpleDemo.class.getName());

    @Test(priority = 1)
    public void assertjAssertionsTestOne() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(1);
        softly.assertThat(1).isGreaterThan(0);
        softly.assertThat(1).usingDefaultComparator();
        softly.assertThat(1).usingDefaultComparator().isGreaterThan(0);
        softly.assertThat("a").asString();
        softly.assertThat("a").asString().hasSize(1);
        softly.assertAll();
    }

    @Test(priority = 2)
    public void assertjAssertionsTestTwo() {
        SoftAssertions softly = new SoftAssertions();
        final int[] ACTUAL = new int[]{2, 5, 7};
        final int[] EXPECTED = new int[]{2, 5, 7};

        softly.assertThat(true).isTrue();
        softly.assertThat(1).isGreaterThan(0);
        softly.assertThat(ACTUAL).isEqualTo(EXPECTED);

        softly.assertAll();
    }

    @Test(priority = 3)
    public void assertjAssertionsTestThree() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(false).isTrue();
        softly.assertThat(0).isGreaterThan(1);
        softly.assertAll();
    }
}