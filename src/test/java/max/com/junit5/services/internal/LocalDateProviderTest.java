package max.com.junit5.services.internal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import max.com.junit5.base.annotation.UnitTest;
import max.com.junit5.base.extensioninterface.TestLifecycleLogger;
import max.com.junit5.base.extensioninterface.TimeExecutionLogger;
import max.com.junit5.extensions.parameterresolver.LocalDateProviderParameterResolver;
import max.com.junit5.extensions.parameterresolver.TestLoggerResolver;
import max.com.junit5.services.ILocalDateProvider;

/**
 * Test class for {@link LocalDateProvider}
 * 
 * @author Max
 */
@DisplayName("LocalDateProvider Test")
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith({ LocalDateProviderParameterResolver.class, TestLoggerResolver.class })
@UnitTest
class LocalDateProviderTest implements TimeExecutionLogger, TestLifecycleLogger {

	private ILocalDateProvider localDateProvider;

	private Logger logger;

	LocalDateProviderTest(TestInfo testInfo) {
		assertEquals("LocalDateProvider Test", testInfo.getDisplayName());
	}

	@BeforeAll
	void beforeEach(ILocalDateProvider localDateProvider, Logger logger) {
		assertNotNull(localDateProvider);
		assertNotNull(logger);
		this.localDateProvider = localDateProvider;
		this.logger = logger;
	}

	@Test
	@DisplayName("provideToday() returns current date")
	void test_provideTodayReturnsTodaysYearDayAndMonth(TestReporter testReporter) {
		logger.info(() -> "This is my internal test logger! " + logger.getName());

		LocalDate actualResult = localDateProvider.provideToday();

		assertIsValidDate(actualResult);

		long currentTimeMillis = System.currentTimeMillis();
		LocalDate expectedResult = Instant.ofEpochMilli(currentTimeMillis).atZone(ZoneId.systemDefault()).toLocalDate();
		testReporter.publishEntry(expectedResult.toString(), actualResult.toString());
		assertEquals(expectedResult, actualResult, "These dates should match");
	}

	@ParameterizedTest
	@ValueSource(strings = { StringUtils.EMPTY, StringUtils.SPACE })
	void test_parseDateThrowsIllegalArgumentExceptionWhenCalledWithNullOrBlankDateString(String dateString) {
		assertThrows(IllegalArgumentException.class, () -> localDateProvider.parseDate(dateString));
	}

	@ParameterizedTest
	@CsvSource({ ", 01, 01", "2002, , 02", "2002, 03, " })
	void test_parseDateThrowsIllegalArgumentExceptionWhenCalledWithBlankDayYearOrMonth(String year, String month,
			String day) {
		assertThrows(IllegalArgumentException.class, () -> localDateProvider.parseDate(year, month, day));
	}

	@DisplayName("It's morphing time!")
	@ParameterizedTest
	@ValueSource(strings = { "2017-02-28", "2017-01-01", "1999-12-31" })
	@MethodSource("validDateStrings")
	@ArgumentsSource(ValidParsingDateArgumentsProvider.class)
	@CsvFileSource(resources = "/MoreValidDates.csv")
	void test_ParseDateReturnsValidDateWhenPassedDateTimeFormatter_ISO_LOCAL_DATE_conformStringUsingAllMethods(
			String dateString, TestInfo testInfo) {
		LocalDate result = localDateProvider.parseDate(dateString);
		assertIsValidDate(result);
	}

	@DisplayName("Passing DateTimeFormatter ISO_LOCAL_DATE conform Strings")
	@ParameterizedTest
	@ValueSource(strings = { "2017-02-28", "2017-01-01", "1999-12-31" })
	void test_ParseDateReturnsValidDateWhenPassedDateTimeFormatter_ISO_LOCAL_DATE_conformString(String dateString) {
		LocalDate result = localDateProvider.parseDate(dateString);
		assertIsValidDate(result);
	}

	@ParameterizedTest
	@CsvSource({ "2001, 01, 01", "2002, 02, 02", "2002, 03, 3" })
	void test_PassValidYearMonthAndDateReturnsValidDate(String year, String month, String day) {
		LocalDate result = localDateProvider.parseDate(year, month, day);
		assertIsValidDate(result);
	}

	@ParameterizedTest
	@ValueSource(strings = { "2001, 01, 01", "2002, 02, 02", "2002, 03, 03" })
	void test_PassValidYearMonthAndDateReturnsValidDate(@ConvertWith(DateArgumentConverter.class) String dateString) {
		LocalDate result = localDateProvider.parseDate(dateString);
		assertIsValidDate(result);
	}

	private static void assertIsValidDate(LocalDate date) {
		assertNotNull(date);
		assertTrue(date.getYear() > 0);
		assertAll(() -> assertTrue(date.getMonth().getValue() > 0), //
				() -> assertTrue(date.getMonth().getValue() <= 12));
		assertTrue(date.getDayOfYear() > 0);
		assertAll(() -> assertTrue(date.getDayOfMonth() > 0), //
				() -> assertTrue(date.getDayOfMonth() <= 31));
	}

	Stream<String> validDateStrings() {
		return Stream.of("2017-02-27", "2017-01-13", "1776-01-30");
	}

	public static class ValidParsingDateArgumentsProvider implements ArgumentsProvider {

		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext arg0) throws Exception {
			return Stream.of("2017-02-27", "2017-01-31", "1988-02-28").map(Arguments::of);
		}

	}

	public static class DateArgumentConverter implements ArgumentConverter {

		@Override
		public Object convert(Object arg0, ParameterContext arg1) throws ArgumentConversionException {
			if (!String.class.isAssignableFrom(arg0.getClass())) {
				throw new ArgumentConversionException(
						arg0.getClass() + " can not be converted. Has to be of type String.");
			}
			String dateString = (String) arg0;
			return StringUtils.deleteWhitespace(dateString).replaceAll(",", "-");
		}

	}

}
