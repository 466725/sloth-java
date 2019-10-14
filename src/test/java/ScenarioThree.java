import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ScenarioThree {
	@BeforeTest
	public void setUp() {
		System.out.println("==========================333333-Setup==========================");
	}

	@AfterTest
	public void tearDown() {
		System.out.println("==========================333333-TearDown==========================");
	}

	@Test(priority = 6)
	public void testTrueThree() {
		System.out.println("==========================333333-True==========================");
		Assert.assertTrue(true);
	}

	@Test(priority = 8)
	public void testFalseThree() throws Exception {
		System.out.println("==========================333333-False==========================");
		throw new Exception("==========================333333-False==========================");
	}
}