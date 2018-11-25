package max.com.junit5.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

import max.com.junit5.extensions.parameterresolver.RandomIntegerValueParameterResolver;

@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(RandomIntegerValueParameterResolver.class)
public @interface RandomIntegerValues {
	// nothing to do
}
