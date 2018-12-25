package max.com.junit5.objects;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.logging.Level;

import max.com.junit5.exception.PersonUtilsException;
import max.com.junit5.services.ILocalDateService;

/**
 * This calculates ages
 * 
 * @author Max
 */
public class PersonUtils {

	private ILocalDateService localDateService;

	/**
	 * Constructor
	 * 
	 * @param localDateService
	 */
	public PersonUtils(ILocalDateService localDateService) {
		Objects.requireNonNull(localDateService, () -> "localDateService has to be provided");
		this.localDateService = localDateService;
	}

	/**
	 * @param person
	 * @throws PersonUtilsException
	 */
	public Integer calcAge(Person person) throws PersonUtilsException {
		Objects.requireNonNull(person, () -> "Null person passed");
		LocalDate birthDate = person.getBirthDate();
		if (Objects.isNull(birthDate)) {
			throw new PersonUtilsException("invalid birthDate date", Level.SEVERE);
		}
		LocalDate today = localDateService.provideToday();
		if (Objects.isNull(today)) {
			throw new PersonUtilsException("invalid today date", Level.SEVERE);
		}
		Period between = Period.between(birthDate, today);
		int years = between.getYears();
		if (years < 0) {
			throw new PersonUtilsException("Negative age", Level.WARNING, years);
		}
		return years;
	}

	public Person writeToDatabase(Person person) throws InterruptedException {
		// do something with the database
		/*
		 * insert code here
		 */
		Thread.sleep(500);
		//
		return person;
	}

}
