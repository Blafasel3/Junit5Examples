package max.com.junit5.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Tag;

/**
 * Annotation to use on actual unit tests. Can be used on classes and methods.
 */

@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Tag("IntegrationTest") // JUnit's Tag annotation can be used at class or method level
public @interface IntegrationTest {
	// Nothing to do
}
