package max.com.junit5.extensions.parameterresolver;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import max.com.junit5.services.ILocalDateProvider;
import max.com.junit5.services.internal.LocalDateProvider;

public class LocalDateProviderParameterResolver implements ParameterResolver {

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		Class<?> passedType = parameterContext.getParameter().getType();
		return ILocalDateProvider.class.equals(passedType);
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		return new LocalDateProvider();
	}
}
