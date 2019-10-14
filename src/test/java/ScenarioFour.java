import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ScenarioFour {
	@BeforeTest
	public void setUp() {
		System.out.println("==========================444444-Setup==========================");
	}

	@AfterTest
	public void tearDown() {
		System.out.println("==========================444444-TearDown==========================");
	}

	@Test(priority = 6)
	public void testTrueFour() {
		System.out.println("==========================444444-True==========================");
		Assert.assertTrue(true);
	}

	@Test(priority = 8)
	public void testFalseFour() throws Exception {
		System.out.println("==========================444444-False==========================");
		throw new Exception("==========================444444-False==========================");
	}
}