package max.com.junit5.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.logging.Level;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import max.com.junit5.base.annotation.RandomIntegerValues;
import max.com.junit5.base.annotation.UnitTest;
import max.com.junit5.exception.PersonUtilsException;
import max.com.junit5.extensions.parameterresolver.LocalDateParameterResolver;
import max.com.junit5.extensions.parameterresolver.RandomIntegerValueParameterResolver.RandomIntegerRange;
import max.com.junit5.services.ILocalDateService;

/**
 * Tests for AgeCalculator including mocking
 * 
 * @author Max
 */
@ExtendWith({ MockitoExtension.class, LocalDateParameterResolver.class })
@TestInstance(Lifecycle.PER_CLASS)
@UnitTest
class PersonUtilsUnitTest {

	@Mock
	private ILocalDateService localDateProvider;

	@Mock
	private Person person;

	private PersonUtils ageCalculator;

	private LocalDate referenceDate;

	@BeforeAll
	void setUpAll(LocalDate referenceDate) {
		assertNotNull(referenceDate);
		this.referenceDate = referenceDate;
	}

	@BeforeEach
	void setUp() {
		assertNotNull(localDateProvider, "Mock of localDateProvider went wrong");
		assertNotNull(person, "Mock of person went wrong");
		ageCalculator = new PersonUtils(localDateProvider);
	}

	@Test
	void test_calcAgeThrowsAgeCulatorExceptionWhenPersonHasNullBirthDate() throws Exception {
		when(person.getBirthDate()).thenReturn(null);

		PersonUtilsException exception = assertThrows(PersonUtilsException.class,
				() -> ageCalculator.calcAge(person));

		assertNotNull(exception);
		assertEquals("invalid birthDate date", exception.getMessage());
		assertEquals(Level.SEVERE, exception.getLevel());
		assertFalse(exception.getOptionalResult().isPresent());
	}

	@Test
	void test_calcAgeThrowsAgeCulatorExceptionWhenLocalDateProviderReturnsNullForToday() throws Exception {
		when(person.getBirthDate()).thenReturn(LocalDate.parse("2017-01-01"));
		when(localDateProvider.provideToday()).thenReturn(null);

		PersonUtilsException exception = assertThrows(PersonUtilsException.class,
				() -> ageCalculator.calcAge(person));

		assertNotNull(exception);
		assertEquals("invalid today date", exception.getMessage());
		assertEquals(Level.SEVERE, exception.getLevel());
		assertFalse(exception.getOptionalResult().isPresent());

	}

	@Test
	void test_CalcAgeReturns0IfCurrentDateIsTheBirthDate() throws Exception {
		String dateString = "2018-01-01";
		LocalDate expectedToday = LocalDate.parse(dateString);
		when(localDateProvider.provideToday()).thenReturn(expectedToday);
		LocalDate actualToday = localDateProvider.provideToday();
		assertNotNull(actualToday);
		assertEquals(expectedToday, actualToday);
		when(person.getBirthDate()).thenReturn(LocalDate.parse(dateString));
		Integer expectedResult = 0;

		Integer actualResult = ageCalculator.calcAge(person);

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void test_CalcAgeReturns0IfPeriodIsLessThanAYear() throws Exception {
		LocalDate expectedToday = LocalDate.parse("2018-01-01");
		when(localDateProvider.provideToday()).thenReturn(expectedToday);
		LocalDate actualToday = localDateProvider.provideToday();
		assertNotNull(actualToday);
		assertEquals(expectedToday, actualToday);
		when(person.getBirthDate()).thenReturn(LocalDate.parse("2018-12-31"));
		Integer expectedResult = 0;

		Integer actualResult = ageCalculator.calcAge(person);

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void test_CalcAgeReturns1IfPeriodIsExactlyOneYear() throws Exception {
		assertNotNull(referenceDate);
		LocalDate expectedToday = referenceDate.plusYears(1);
		when(localDateProvider.provideToday()).thenReturn(expectedToday);
		when(person.getBirthDate()).thenReturn(referenceDate);
		Integer expectedResult = 1;

		Integer actualResult = ageCalculator.calcAge(person);

		assertEquals(expectedResult, actualResult);
	}

	@RepeatedTest(value = 10, name = RepeatedTest.LONG_DISPLAY_NAME)
	@RandomIntegerValues
	void test_CalcAgeReturnsXTIfExactlyXyearsAreAddedToReferenceDate(
			@RandomIntegerRange(min = 10, max = 50) int yearsToAdd) throws Exception {
		LocalDate expectedToday = referenceDate.plusYears(yearsToAdd);
		when(localDateProvider.provideToday()).thenReturn(expectedToday);
		when(person.getBirthDate()).thenReturn(referenceDate);

		Integer actualResult = ageCalculator.calcAge(person);

		assertEquals(Integer.valueOf(yearsToAdd), actualResult);
	}

	@RepeatedTest(value = 10, name = RepeatedTest.LONG_DISPLAY_NAME)
	@RandomIntegerValues
	void test_CalcAgeReturnsXIfXTimes12MonthsAreAddedToReferenceDate(
			@RandomIntegerRange(min = 0, max = 100) int yearsToAdd) throws Exception {
		int monthsToAdd = yearsToAdd * 12;
		LocalDate expectedToday = referenceDate.plusMonths(monthsToAdd);
		when(localDateProvider.provideToday()).thenReturn(expectedToday);
		when(person.getBirthDate()).thenReturn(referenceDate);

		Integer actualResult = ageCalculator.calcAge(person);

		assertEquals(Integer.valueOf(yearsToAdd), actualResult);
	}

}
