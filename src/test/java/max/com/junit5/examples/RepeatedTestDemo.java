package max.com.junit5.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

/**
 * Test class to show testing with repeated tests in JUnit 5
 * 
 * @author Max
 */
class RepeatedTestDemo {

	@DisplayName("This is a repeated test")
	@RepeatedTest(value = 5, name = RepeatedTest.LONG_DISPLAY_NAME)
	void repeatedTest() {
		assertEquals(2 * 2, 4);
	}

}
