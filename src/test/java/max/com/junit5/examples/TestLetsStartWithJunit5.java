package max.com.junit5.examples;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import max.com.junit5.base.annotation.UnitTest;

/**
 * Simple test class to show JUnit 5 functionality
 *
 */
@UnitTest
class TestLetsStartWithJunit5 {

	@BeforeAll
	static void beforeAll() {
		//
	}

	@BeforeEach
	void beforeEach() {
		//
	}

	@AfterEach
	void afterEach() {
		//
	}

	@AfterAll
	static void afterAll() {
		//
	}

	@Test
	@DisplayName("but I cannot think of a better one...")
	@UnitTest
	void test_GroupedAssertions() {
		assertAll(() -> assertTrue(true), () -> assertEquals(4, 2 + 2));
	}

}
