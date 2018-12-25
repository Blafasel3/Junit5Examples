package max.com.junit5.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Test class to show testing with parameterizations in JUnit 5
 * 
 * @author Max
 */
class ParameterizedTestDemo {

	@ParameterizedTest
	@ValueSource(strings = { "abc", "def", "ghx4" })
	@DisplayName("This test uses the value source annotation")
	void parametrizedTest_AllStringsHaveLengthThre(String value) {
		assertNotNull(value, () -> "This should not be null");
		assertEquals(value.length(), 3, () -> "This string is too long");
	}

	@ParameterizedTest
	@MethodSource("stringProvider") // method without arguments in the same class as test
	@DisplayName("This test uses the method source annotation")
	void test_GetValuesFromAMethod(String value) {
		List<String> allowedValues = Arrays.asList("xx", "aa");
		assertTrue(allowedValues.contains(value), () -> "Value should be any of " + allowedValues);
	}

	static Stream<String> stringProvider() {
		return Stream.of("xx", "aa", "bb");
	}

}
