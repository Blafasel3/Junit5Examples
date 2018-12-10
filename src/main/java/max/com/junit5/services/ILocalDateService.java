package max.com.junit5.services;

import java.time.LocalDate;

/**
 * Provides local date values
 * 
 * @author Max
 */
public interface ILocalDateService {

	/**
	 * @return Current date
	 */
	LocalDate provideToday();

	/**
	 * @param dateString
	 * @return the parsed date
	 */
	LocalDate parseDate(String dateString);

	/**
	 * @param year
	 *            - format YYYY
	 * @param month
	 *            - format MM
	 * @param day
	 *            - format DD
	 * @return the parsed date
	 */
	LocalDate parseDate(String year, String month, String day);
}
