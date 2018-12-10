package max.com.junit5.examples;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import max.com.junit5.GeneralTestExecutionExceptionHandler;
import max.com.junit5.base.annotation.UnitTest;

/**
 * Test class to show testing under assumptions in JUnit 5
 *
 */
@UnitTest
@ExtendWith(GeneralTestExecutionExceptionHandler.class)
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
		// TODO check why false fails here....? ClassNotFoundException
		// ComparisonFailure. MIght be because of junit.jupiter versions not matching
	}

}
