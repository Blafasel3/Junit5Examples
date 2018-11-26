package max.com.junit5.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Test class to show testing with parametrizations in JUnit 5
 * 
 * @author Max
 */
class ParametrizedTestDemo {

	@DisplayName("This test uses the value source annotation")
	@ParameterizedTest
	@ValueSource(strings = { "abc", "def", "ghx" })
	void parametrizedTest_AllStringsHaveLength3(String value) {
		assertNotNull(value, "This should not be null");
		assertEquals(value.length(), 3);
	}

	@DisplayName("This test uses a method source")
	@ParameterizedTest
	@MethodSource("stringProvider") // method without arguments in the same class as test
	void test_GetValuesFromAMethod(String value) {
		assertTrue("xx".equals(value) || "aa".equals(value));
	}

	static Stream<String> stringProvider() {
		return Stream.of("xx", "aa", "bb");
	}

}
