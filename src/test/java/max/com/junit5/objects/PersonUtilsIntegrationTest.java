package max.com.junit5.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

import max.com.junit5.base.annotation.IntegrationTest;
import max.com.junit5.base.annotation.RandomIntegerValues;
import max.com.junit5.extensions.parameterresolver.LocalDateServiceParameterResolver;
import max.com.junit5.extensions.parameterresolver.RandomIntegerValueParameterResolver.RandomIntegerRange;
import max.com.junit5.extensions.parameterresolver.TestLoggerResolver;
import max.com.junit5.services.ILocalDateService;

@IntegrationTest
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith({ TestLoggerResolver.class, LocalDateServiceParameterResolver.class })
@RandomIntegerValues
class PersonUtilsIntegrationTest {

	private ILocalDateService localDateService;
	private PersonUtils testClass;
	private Logger logger;

	@BeforeAll
	void setUp(Logger logger, ILocalDateService localDateService) {
		this.logger = logger;
		this.localDateService = localDateService;
		this.testClass = new PersonUtils(localDateService);
	}

	@RepeatedTest(value = 10)
	@DisplayName("calcAge() method returns x years if birth date is today minus x years")
	void test_CalcAgeReturnsXYearsIfBirthDateOfPersonIsTodayMinusXYears(
			@RandomIntegerRange(min = 0, max = 100) Integer expectedAge, TestInfo testInfo) throws Exception {
		assertNotNull(expectedAge);

		logger.info(testInfo.getDisplayName() + " expectedAgec: " + expectedAge);

		LocalDate today = localDateService.provideToday();
		LocalDate birthDate = today.minusYears(expectedAge);

		Person person = new Person("Peter", "Parker", birthDate);

		Integer actualResult = testClass.calcAge(person);

		assertNotNull(actualResult);
		assertEquals(expectedAge, actualResult);
	}

}
