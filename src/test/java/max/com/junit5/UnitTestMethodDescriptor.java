package max.com.junit5;

import java.lang.reflect.Method;

import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;
import org.junit.platform.engine.support.descriptor.MethodSource;

/**
 * Own Test descriptor
 * 
 * @author Max
 */
public class UnitTestMethodDescriptor extends AbstractTestDescriptor {

	private Method testMethod;
	private Class<?> testClass;

	/**
	 * @param engineId
	 * @param displayName
	 */
	public UnitTestMethodDescriptor(Method testMethod, Class<?> testClass, UnitTestClassDescriptor parent) {
		super( //
				parent.getUniqueId().append("method", testMethod.getName()), //
				determineDisplayName(testMethod), //
				MethodSource.from(testMethod) //
		);
		this.testMethod = testMethod;
		this.testClass = testClass;
		setParent(parent);
	}

	private static String determineDisplayName(Method testMethod) {
		return testMethod.getName() + " - Junit 5 Tutorial";
	}

	public Method getTestMethod() {
		return testMethod;
	}

	public Class<?> getTestClass() {
		return testClass;
	}

	@Override
	public Type getType() {
		return Type.TEST;
	}

}
