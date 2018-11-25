package max.com.junit5.extensions.parameterresolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * Resolves parameters
 * 
 * @author Max
 */
public class RandomIntegerValueParameterResolver implements ParameterResolver {

	/**
	 * Provides the min & max for the given random integer resolver
	 * 
	 * @author Max
	 */
	@Target({ ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface RandomIntegerRange {

		/**
		 * @return the min value to be available (inclusive)
		 */
		int min() default DEFAULT_MIN;

		/**
		 * @return the max value to be available (inclusive)
		 */
		int max() default DEFAULT_MAX;
	}

	private static final int DEFAULT_MIN = 0;

	private static final int DEFAULT_MAX = 100;

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		Class<?> type = parameterContext.getParameter().getType();

		return Integer.class.equals(type) || int.class.equals(type);
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		Random random = new Random();
		Optional<RandomIntegerRange> optionalRange = parameterContext.findAnnotation(RandomIntegerRange.class);
		if (optionalRange.isPresent()) {
			RandomIntegerRange range = optionalRange.get();
			return random.nextInt(range.max() - range.min() + 1) + range.min();
		}
		return random.nextInt(getDefaultMax() - getDefaultMin() + 1) + getDefaultMin();
	}

	/**
	 * @return the defaultMin
	 */
	public static int getDefaultMin() {
		return DEFAULT_MIN;
	}

	/**
	 * @return the defaultMax
	 */
	public static int getDefaultMax() {
		return DEFAULT_MAX;
	}

}
