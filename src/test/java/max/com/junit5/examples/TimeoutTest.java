package max.com.junit5.examples;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import max.com.junit5.base.annotation.UnitTest;

/**
 * Simple test class to show JUnit 5 functionality
 *
 */
@UnitTest
@DisplayName("Does it take too long?")
class TimeoutTest {

	@Test
	@DisplayName("Got lucky!")
	void test_timeoutSucceeds() {
		assertTimeout(ofSeconds(1), () -> Thread.sleep(999));
	}

	@Test
	@DisplayName("Oh damn, it does!")
	void test_timeoutFails() {
		assertTimeout(ofSeconds(1), () -> Thread.sleep(1050));
	}

}
