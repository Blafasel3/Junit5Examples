package max.com.junit5.extensions.parameterresolver;

import java.time.LocalDate;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import max.com.junit5.services.ILocalDateProvider;

public class NullLocalDateProviderParameterResolver implements ParameterResolver {

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		Class<?> passedType = parameterContext.getParameter().getType();
		return ILocalDateProvider.class.equals(passedType);
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		return new ILocalDateProvider() {

			@Override
			public LocalDate provideToday() {
				return null;
			}

			@Override
			public LocalDate parseDate(String dateString) {
				return null;
			}

			@Override
			public LocalDate parseDate(String year, String month, String day) {
				return null;
			}
		};
	}
}
