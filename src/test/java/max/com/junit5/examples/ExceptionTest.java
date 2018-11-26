package max.com.junit5.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class to show example exception testing in JUnit 5
 *
 */
@DisplayName("Exception testing")
class ExceptionTest {

	@Test
	@DisplayName("Exceptions are typed, yay!")
	void test_AssertExceptions() {
		MaxCustomException exception = assertThrows(MaxCustomException.class, () -> throwsException());
		assertEquals(exception.getMessage(), "Let's assert this message");
		assertFalse(exception.isSevere());

	}

	private static void throwsException() throws MaxCustomException {
		throw new MaxCustomException("Let's assert this message", Boolean.FALSE);
	}

	static class MaxCustomException extends Throwable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7659611638832626247L;

		private Boolean isSevere;

		MaxCustomException(String message, Boolean isSevere) {
			super(message);
			this.isSevere = isSevere;
		}

		/**
		 * @return the isSevere
		 */
		public Boolean isSevere() {
			return isSevere;
		}

	}
}
