package max.com.junit5.examples;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import max.com.junit5.GeneralTestExecutionExceptionHandler;

/**
 * Test class to show testing under assumptions in JUnit 5
 *
 */
@ExtendWith(GeneralTestExecutionExceptionHandler.class)
@DisplayName("JUnit 5 Nested Example")
class StringUtilsTest {

	@BeforeAll
	static void beforeAll() {
		System.out.println("Before all test methods");
	}

	@BeforeEach
	void beforeEach() {
		System.out.println("Before each test method");
	}

	@Nested
	class TestIsBlank {

		@Test
		@DisplayName("null argument is blank")
		void isBlankWithNull() {
			assertTrue(StringUtils.isBlank(null));
		}

		@Test
		@DisplayName("Empty string constant is blank")
		void isBlankWithEmptyStringConstant() {
			assertTrue(StringUtils.isBlank(StringUtils.EMPTY));
		}

		@Test
		@DisplayName("Empty argument is blank")
		void isBlankWithEmptyString() {
			assertTrue(StringUtils.isBlank(""));
		}

		@DisplayName("Any other argument is not blank")
		@ParameterizedTest
		@ValueSource(strings = { "stringProvider" }) // method without arguments in the same class as test
		void isBlankWithStringWithLengthGreaterZero(String value) {
			assertFalse(StringUtils.isBlank(value));
		}

	}

	@AfterEach
	void afterEach() {
		System.out.println("After each test method");
	}

	@AfterAll
	static void afterAll() {
		System.out.println("After all test methods");
	}

}
