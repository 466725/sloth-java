import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ScenarioTwo {
	@BeforeTest
	public void setUp() {
		System.out.println("==========================222222-Setup==========================");
	}

	@AfterTest
	public void tearDown() {
		System.out.println("==========================222222-TearDown==========================");
	}

	@Test(priority = 6)
	public void testTrue() {
		System.out.println("==========================222222-True==========================");
		Assert.assertTrue(true);
	}

	@Test(priority = 8)
	public void testFalse() {
		System.out.println("==========================222222-False==========================");
		Assert.assertTrue(false);
	}
}