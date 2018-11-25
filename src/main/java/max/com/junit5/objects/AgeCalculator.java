package max.com.junit5.objects;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.logging.Level;

import max.com.junit5.exception.AgeCalculatorException;
import max.com.junit5.services.ILocalDateProvider;

/**
 * This calculates ages
 * 
 * @author Max
 */
public class AgeCalculator {

	private ILocalDateProvider localDateProvider;

	/**
	 * Constructor
	 * 
	 * @param localDateProvider
	 */
	public AgeCalculator(ILocalDateProvider localDateProvider) {
		Objects.requireNonNull(localDateProvider, () -> "localDateProvider has to be provided");
		this.localDateProvider = localDateProvider;
	}

	/**
	 * @param person
	 * @throws AgeCalculatorException
	 */
	public Integer calcAge(Person person) throws AgeCalculatorException {
		Objects.requireNonNull(person, () -> "Null person passed");
		LocalDate birthDate = person.getBirthDate();
		if (Objects.isNull(birthDate)) {
			throw new AgeCalculatorException("invalid birthDate date", Level.SEVERE);
		}
		LocalDate today = localDateProvider.provideToday();
		if (Objects.isNull(today)) {
			throw new AgeCalculatorException("invalid today date", Level.SEVERE);
		}
		Period between = Period.between(birthDate, today);
		int years = between.getYears();
		if (years < 0) {
			throw new AgeCalculatorException("Negative age", Level.WARNING, years);
		}
		return years;
	}

}
