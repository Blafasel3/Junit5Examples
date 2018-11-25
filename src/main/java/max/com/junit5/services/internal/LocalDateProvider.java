package max.com.junit5.services.internal;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;

import max.com.junit5.services.ILocalDateProvider;

/**
 * 
 * @author Max
 */
public class LocalDateProvider implements ILocalDateProvider {

	private static final String SEPARATOR = "-";

	/** {@inheritDoc} */
	public LocalDate provideToday() {
		return LocalDate.now();
	}

	/** {@inheritDoc} */
	@Override
	public LocalDate parseDate(String dateString) {
		argumentCheck(dateString);
		return LocalDate.parse(dateString);
	}

	/** {@inheritDoc} */
	@Override
	public LocalDate parseDate(String year, String month, String day) {
		argumentCheck(year, month, day);
		month = fillToTwoDigits(month);
		day = fillToTwoDigits(day);
		return parseDate(year + SEPARATOR + month + SEPARATOR + day);
	}

	private static void argumentCheck(String... strings) {
		for (String string : strings) {
			if (StringUtils.isBlank(string)) {
				throw new IllegalArgumentException("Argument invalid " + strings);
			}
		}
	}

	private static String fillToTwoDigits(String value) {
		if (value.length() == 1) {
			value = "0" + value;
		}
		return value;
	}
}
