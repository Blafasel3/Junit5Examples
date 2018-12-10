package max.com.junit5.exception;

import java.util.Optional;
import java.util.logging.Level;

import max.com.junit5.objects.PersonUtils;

/**
 * Exception thrown in context of {@link PersonUtils}
 * 
 * @author Max
 * @see PersonUtils
 * @see Exception
 */
public class PersonUtilsException extends Exception {

	/**
	 * Serialization purposes
	 */
	private static final long serialVersionUID = 8381105260250944388L;

	private Level level;

	private Optional<Integer> optionalResult;

	/**
	 * Constructor
	 * 
	 * @param message
	 */
	public PersonUtilsException(String message, Level level) {
		this(message, level, null);
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 */
	public PersonUtilsException(String message, Level level, Integer result) {
		super(message);
		this.level = level;
		optionalResult = Optional.ofNullable(result);
	}

	/**
	 * The level of severity
	 * 
	 * @return the level
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * There might have been some result during the calculation. Check with
	 * ifPresent()
	 * 
	 * @return the optionalResult
	 */
	public Optional<Integer> getOptionalResult() {
		return optionalResult;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "AgeCalculatorException [level=" + level + ", optionalResult=" + optionalResult + ", getMessage()="
				+ getMessage() + "]";
	}

}
