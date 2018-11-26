package max.com.junit5.examples;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import max.com.junit5.base.annotation.UnitTest;

/**
 * Simple test class to show JUnit 5 functionality
 *
 */
@UnitTest
class AssumingTest {

	@Test
	@DisplayName("assumeTrue() => Test is 'ignored' if assumoption not met")
	void test_assumingStuffIsIgnoreWhen() {
		assumeTrue(runningOnIntegrationServer());
		assertTrue(false);
	}

	@Test
	@DisplayName("assumingThat() => Test is reported as green if assumoption not met")
	void test_assumingStuffReportsGreen() {
		assumingThat(runningOnIntegrationServer(), () -> assertTrue(false));
	}

	static boolean runningOnIntegrationServer() {
		return false;
	}

}
