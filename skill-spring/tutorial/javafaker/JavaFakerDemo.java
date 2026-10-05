package tutorial.javafaker;

import core.ApiTestCase;
import com.github.javafaker.Faker;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

public class JavaFakerDemo extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(JavaFakerDemo.class.getName());

    @Test(priority = 1)
    public void assertjAssertionsTestOne() {
        SoftAssertions softly = new SoftAssertions();
        Faker faker = new Faker();

        logger.info("Fake full name: " + faker.name().fullName());
        logger.info("Fake first name: " + faker.name().firstName());
        logger.info("Fake last name: " + faker.name().lastName());
        logger.info("Fake address: " + faker.address().streetAddress());
        logger.info("Fake zip code: " + faker.address().zipCode());
        logger.info("Fake city: " + faker.address().city());
        logger.info("Fake state: " + faker.address().state());
        logger.info("Fake cell phone number: " + faker.phoneNumber().cellPhone());
        logger.info("Fake phone number: " + faker.phoneNumber().phoneNumber());
        logger.info("Fake number: " + faker.number().digit());

        softly.assertThat(1).isGreaterThan(0);
        softly.assertThat(1).usingDefaultComparator();
        softly.assertThat(1).usingDefaultComparator().isGreaterThan(0);

        softly.assertThat("a").asString();
        softly.assertThat("a").asString().hasSize(1);

        softly.assertAll();
    }
}