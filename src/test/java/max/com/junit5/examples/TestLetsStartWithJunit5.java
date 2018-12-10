package max.com.junit5.examples;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Simple test class to show JUnit 5 functionality
 *
 */
@DisplayName("Our first JUnit5 test class") // name shown in results
@Tag("UnitTest")
class TestLetsStartWithJunit5 {

	// test lifecycle hook methods
	@BeforeAll
	static void beforeAll() {
		System.out.println("before all...");
	}

	@BeforeEach
	void beforeEach() {
		System.out.println("before each...");
	}

	@AfterEach
	void afterEach() {
		System.out.println("after each...");
	}

	@AfterAll
	static void afterAll() {
		System.out.println("after all...");
	}

	@Test
	@DisplayName("Our first JUnit 5 Test")
	void test_firstTest() {
		assertTrue(true);
	}

	@Test
	@Disabled("We skip this one")
	void test_disabled() {
		// not executed
	}

	@Test
	@DisplayName("Failure messsage is now the last argument. Lazy evaluation via supplier...")
	void test_lazyEvaluations() {
		assertTrue(false, () -> "Lazyliy evaluated message provided by supplier");
	}

	@Test
	@DisplayName("Assertions can be grouped and nested")
	@Tag("UnitTest")
	void test_GroupedAssertions() {
		assertAll( //
				() -> assertTrue(true), //
				() -> assertEquals(4, 2 + 2), //
				() -> assertEquals(0, 1, "This can't be right..."), //
				() -> {
					assertTrue(true, "nested works...");
					assertTrue(true);
				}, //
				() -> {
					assertTrue(false, "nested fail...");
					assertTrue(false, "this does not get called anymore");
				});
	}

}
