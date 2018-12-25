package max.com.junit5.examples;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import max.com.junit5.base.annotation.UnitTest;

/**
 * Simple test class to show JUnit 5 timeout functionality
 *
 * @author Max
 */
@UnitTest
@DisplayName("Does it take too long?")
class TimeoutTest {

	@Test
	@DisplayName("Got lucky!")
	void test_timeoutSucceeds() {
		String actualResult = assertTimeout(ofSeconds(1), () -> { // this is typed!
			Thread.sleep(800);
			return "42";
		});
		assertEquals("42", actualResult, () -> "The answer is 42!");
	}

	@Test
	@DisplayName("Oh damn, it does!")
	void test_timeoutFails() {
		assertTimeout(ofSeconds(1), () -> Thread.sleep(1050));
	}

}
