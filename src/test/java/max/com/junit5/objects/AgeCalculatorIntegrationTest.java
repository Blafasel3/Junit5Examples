package max.com.junit5.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

import max.com.junit5.base.annotation.IntegrationTest;
import max.com.junit5.base.annotation.RandomIntegerValues;
import max.com.junit5.extensions.parameterresolver.TestLoggerResolver;
import max.com.junit5.services.ILocalDateProvider;
import max.com.junit5.services.internal.LocalDateProvider;

@IntegrationTest
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith({ TestLoggerResolver.class })
@RandomIntegerValues
class AgeCalculatorIntegrationTest {

	private ILocalDateProvider localDateProvider;
	private AgeCalculator testClass;
	private Logger logger;

	@BeforeEach
	void setUp(Logger logger) {
		this.logger = logger;
		localDateProvider = new LocalDateProvider();
		testClass = new AgeCalculator(localDateProvider);
	}

	@RepeatedTest(value = 10)
	@Test
	void test_CalcAgeReturnsXYearsIfBirthDateOfPersonIsTodayMinusXYears(Integer yearsToAdd, TestInfo testInfo)
			throws Exception {
		assertNotNull(yearsToAdd);

		logger.info(testInfo.getDisplayName() + " yearsToAdd: " + yearsToAdd);

		LocalDate today = localDateProvider.provideToday();
		LocalDate birthDate = today.minusYears(yearsToAdd);

		Person person = new Person("Peter", "Parker", birthDate);

		Integer actualResult = testClass.calcAge(person);

		assertNotNull(actualResult);
		assertEquals(yearsToAdd, actualResult);
	}

}
