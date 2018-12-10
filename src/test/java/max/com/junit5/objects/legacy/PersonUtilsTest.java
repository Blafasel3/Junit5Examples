package max.com.junit5.objects.legacy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.logging.Level;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import max.com.junit5.exception.PersonUtilsException;
import max.com.junit5.extensions.parameterresolver.LocalDateServiceParameterResolver;
import max.com.junit5.extensions.parameterresolver.NullLocalDateServiceParameterResolver;
import max.com.junit5.objects.Person;
import max.com.junit5.objects.PersonUtils;
import max.com.junit5.services.ILocalDateService;

/**
 * Test class for {@link PersonUtils}
 * 
 * @author Max
 */
class PersonUtilsTest {

	/**
	 * Test for constructor with illegal argument
	 */
	@Test
	@ExtendWith(IgnoreAgeCalculatorExceptionExtension.class)
	void test_ConstructorThrowsIllegalArgumentExceptionIfLocalDateProviderIsNull() {
		// this actually returns the (typed) thrown exception ...
		NullPointerException exception = assertThrows(NullPointerException.class, () -> new PersonUtils(null));

		// ... so we can assert stuff with it!!!
		assertNotNull(exception, "something went horrifically wrong");
		assertEquals("localDateProvider has to be provided", exception.getMessage(), "Messages did not match");
	}

	@Nested
	@ExtendWith({ LocalDateServiceParameterResolver.class })
	class FunctionalLocalDateProviderTest {

		@Test
		public void test_calcAgeThrowsAgeCalculatorExceptionWithLevelWarningIfPersonHasNoValidDate(
				ILocalDateService localDateProvider) throws Exception {

			PersonUtils ageCalculator = new PersonUtils(localDateProvider);
			Person person = new Person("Al", "Bundy", null);
			PersonUtilsException exception = assertThrows(PersonUtilsException.class,
					() -> ageCalculator.calcAge(person));

			assertNotNull(exception, "something went horrifically wrong");
			assertEquals("invalid birthDate date", exception.getMessage());
			assertEquals(Level.SEVERE, exception.getLevel());
		}

	}

	@Nested
	@ExtendWith({ NullLocalDateServiceParameterResolver.class })
	class BrokenLocalDateProviderTest {

		@Test
		public void test_calcAgeThrowsAgeCalculatorExceptionWithLevelWarningIfLocalDateProviderReturnsNullToday(
				ILocalDateService localDateProvider) throws Exception {

			PersonUtils ageCalculator = new PersonUtils(localDateProvider);
			Person person = new Person("Al", "Bundy", LocalDate.parse("2017-12-31"));
			PersonUtilsException exception = assertThrows(PersonUtilsException.class,
					() -> ageCalculator.calcAge(person));

			assertEquals("invalid today date", exception.getMessage(),
					() -> "The todays date was invalid " + localDateProvider.provideToday());
			assertEquals(Level.SEVERE, exception.getLevel());
		}
	}

	public static class IgnoreAgeCalculatorExceptionExtension implements TestExecutionExceptionHandler {

		@Override
		public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
			System.out.println("supppp");
			if (throwable instanceof PersonUtilsException) {
				return;
			}
			throw throwable;
		}

	}

}
