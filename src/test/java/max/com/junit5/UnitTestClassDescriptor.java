package max.com.junit5;

import java.lang.reflect.Method;
import java.util.function.Predicate;

import org.junit.platform.commons.util.ReflectionUtils;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;
import org.junit.platform.engine.support.descriptor.ClassSource;

/**
 * Own Test descriptor
 * 
 * @author Max
 */
public class UnitTestClassDescriptor extends AbstractTestDescriptor {

	private Class<?> testClass;

	/**
	 * @param engineId
	 * @param displayName
	 */
	public UnitTestClassDescriptor(Class<?> testClass, TestDescriptor parent) {
		super( //
				parent.getUniqueId().append("class", testClass.getName()), //
				determineDisplayName(testClass), //
				ClassSource.from(testClass) //
		);
		this.testClass = testClass;
		setParent(parent);
		addAllChildren();
	}

	private static String determineDisplayName(Class<?> testClass) {
		return testClass.getSimpleName();
	}

	private void addAllChildren() {
		Predicate<Method> isTestMethod = method -> {
			if (ReflectionUtils.isStatic(method))
				return false;
			if (ReflectionUtils.isPrivate(method))
				return false;
			if (ReflectionUtils.isAbstract(method))
				return false;
			if (method.getParameterCount() > 0)
				return false;
			return method.getReturnType().equals(boolean.class) || method.getReturnType().equals(Boolean.class);
		};
		ReflectionUtils.findMethods(testClass, isTestMethod).stream() //
				.map(method -> new UnitTestMethodDescriptor(method, testClass, this)) //
				.forEach(this::addChild);
	}

	@Override
	public Type getType() {
		return Type.CONTAINER;
	}

}
