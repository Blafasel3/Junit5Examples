package max.com.junit5.extensions.parameterresolver;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import max.com.junit5.objects.PersonUtils;
import max.com.junit5.services.internal.LocalDateService;

public class PersonUtilsParameterResolver implements ParameterResolver {

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		Class<?> type = parameterContext.getParameter().getType();
		return PersonUtils.class.isAssignableFrom(type);
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		LocalDateService localDateService = new LocalDateService();
		return new PersonUtils(localDateService);
	}

}
