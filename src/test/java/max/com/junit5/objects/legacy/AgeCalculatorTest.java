package max.com.junit5.objects.legacy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.logging.Level;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import max.com.junit5.exception.AgeCalculatorException;
import max.com.junit5.extensions.parameterresolver.LocalDateProviderParameterResolver;
import max.com.junit5.extensions.parameterresolver.NullLocalDateProviderParameterResolver;
import max.com.junit5.objects.AgeCalculator;
import max.com.junit5.objects.Person;
import max.com.junit5.services.ILocalDateProvider;

/**
 * Test class for {@link AgeCalculator}
 * 
 * @author Max
 */
class AgeCalculatorTest {

	/**
	 * Test for constructor with illegal argument
	 */
	@Test
	@ExtendWith(IgnoreAgeCalculatorExceptionExtension.class)
	void test_ConstructorThrowsIllegalArgumentExceptionIfLocalDateProviderIsNull() {
		// this actually returns the (typed) thrown exception ...
		NullPointerException exception = assertThrows(NullPointerException.class, () -> new AgeCalculator(null));

		// ... so we can assert stuff with it!!!
		assertNotNull(exception, "something went horrifically wrong");
		assertEquals("localDateProvider has to be provided", exception.getMessage(), "Messages did not match");
	}

	@Test
	@ExtendWith({ LocalDateProviderParameterResolver.class })
	public void test_calcAgeThrowsAgeCalculatorExceptionWithLevelWarningIfPersonHasNoValidDate(
			ILocalDateProvider localDateProvider) throws Exception {

		AgeCalculator ageCalculator = new AgeCalculator(localDateProvider);
		Person person = new Person("Al", "Bundy", null);
		AgeCalculatorException exception = assertThrows(AgeCalculatorException.class,
				() -> ageCalculator.calcAge(person));

		assertNotNull(exception, "something went horrifically wrong");
		assertEquals("invalid birthDate date", exception.getMessage());
		assertEquals(Level.SEVERE, exception.getLevel());
	}

	@Test
	@ExtendWith({ NullLocalDateProviderParameterResolver.class })
	public void test_calcAgeThrowsAgeCalculatorExceptionWithLevelWarningIfLocalDateProviderReturnsNullToday(
			ILocalDateProvider localDateProvider) throws Exception {

		AgeCalculator ageCalculator = new AgeCalculator(localDateProvider);
		Person person = new Person("Al", "Bundy", LocalDate.parse("2017-12-31"));
		AgeCalculatorException exception = assertThrows(AgeCalculatorException.class,
				() -> ageCalculator.calcAge(person));

		assertEquals("invalid today date", exception.getMessage(),
				() -> "The todays date was invalid " + localDateProvider.provideToday());
		assertEquals(Level.SEVERE, exception.getLevel());
	}

	public static class IgnoreAgeCalculatorExceptionExtension implements TestExecutionExceptionHandler {

		@Override
		public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
			if (throwable instanceof AgeCalculatorException) {
				return;
			}
			throw throwable;
		}

	}

}
