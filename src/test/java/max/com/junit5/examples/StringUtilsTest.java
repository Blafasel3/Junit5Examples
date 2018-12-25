package max.com.junit5.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import max.com.junit5.AssumptionExceptionHandler;

/**
 * Test class to show testing under assumptions in JUnit 5
 *
 */
@ExtendWith({ AssumptionExceptionHandler.class })
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
	@DisplayName("Test isBlank method")
	class TestIsBlank {

		@BeforeEach
		void beforeEach() {
			System.out.println("Before each isBlank test");
		}

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
		@ValueSource(strings = { "xa", "abcccc", "1      " })
		void isBlankWithStringWithLengthGreaterZero(String value) {
			assertFalse(StringUtils.isBlank(value));
		}

	}

	@Nested
	@DisplayName("Test deleteWhitespace method")
	class TestDeleteWhitespace {

		@BeforeEach
		void beforeEach() {
			System.out.println("Before each deleteWhitespace test");
		}

		@Test
		@DisplayName("Null returns null")
		void shouldReturnNulllOnNull() {
			assertEquals(null, StringUtils.deleteWhitespace(null));
		}

		@Test
		@DisplayName("Empty string returns empty string")
		void shouldReturnEmptyStringonEmptyString() {
			assertEquals(StringUtils.EMPTY, StringUtils.deleteWhitespace(StringUtils.EMPTY));
		}

		@Test
		@DisplayName("Whitespace only returns empty string")
		void shouldReturnEmptyStringIfWhitespaceOnly() {
			assertEquals(StringUtils.EMPTY, StringUtils.deleteWhitespace(StringUtils.repeat(" ", 100)));
		}

		@ParameterizedTest
		@ValueSource(strings = { "xa", "abcccc", "123" })
		@DisplayName("Should leave string without whitespace as is")
		void shouldLeaveNonWhitespaceStringAlone(String value) {
			assertEquals(value, StringUtils.deleteWhitespace(value));
		}

		@Test
		@ExtendWith(WhitespaceMapProvider.class)
		@DisplayName("Should return string without whitespace")
		void shouldReturnClearedString(Map<String, String> whitespaceMap) {
			whitespaceMap.entrySet().stream().forEach( //
					entry -> assertEquals( //
							entry.getKey(), StringUtils.deleteWhitespace(entry.getValue()),
							() -> this.getMessageForEntry(entry)));
		}

		private String getMessageForEntry(Entry<String, String> entry) {
			return "Value " + entry.getKey() + " did not match deleteWhitespace version of '" + entry.getValue() + "'";
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

	static class WhitespaceMapProvider implements ParameterResolver {

		private static final Map<String, String> WHITESPACED_FIELDS = new HashMap<>();

		static {
			WHITESPACED_FIELDS.put("abc", "a b c");
			WHITESPACED_FIELDS.put("4", " 4 ");
			WHITESPACED_FIELDS.put("&5/1", "    & 5    / 1");
		}

		@Override
		public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
				throws ParameterResolutionException {
			Class<?> type = parameterContext.getParameter().getType();
			return Map.class.isAssignableFrom(type);
		}

		@Override
		public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
				throws ParameterResolutionException {

			return Collections.unmodifiableMap(WHITESPACED_FIELDS);
		}

	}

}
