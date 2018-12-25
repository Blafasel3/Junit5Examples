package max.com.junit5;

import java.util.function.Predicate;

import org.junit.platform.commons.support.AnnotationSupport;
import org.junit.platform.commons.support.ReflectionSupport;
import org.junit.platform.commons.util.ReflectionUtils;
import org.junit.platform.engine.EngineDiscoveryRequest;
import org.junit.platform.engine.ExecutionRequest;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestEngine;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.engine.discovery.PackageSelector;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;

import max.com.junit5.base.annotation.UnitTest;

/**
 * 
 * @author Max
 */
public class UnitTestEngine implements TestEngine {

	private static final String TEST_ENGINE_ID = "max.com.junit5.base.engine.UnitTestEngine";

	private static final Predicate<Class<?>> IS_UNIT_TEST_CONTAINER = aClass -> {
		if (ReflectionUtils.isAbstract(aClass)) {
			return false;
		}
		if (ReflectionUtils.isPrivate(aClass)) {
			return false;
		}
		return AnnotationSupport.isAnnotated(aClass, UnitTest.class);
	};

	/** {@inheritDoc} */
	@Override
	public TestDescriptor discover(EngineDiscoveryRequest discoveryRequest, UniqueId uniqueId) {
		EngineDescriptor engineDescriptor = new EngineDescriptor(uniqueId, "Unit Test Engine");

		discoveryRequest.getSelectorsByType(PackageSelector.class)
				.forEach(selector -> appendTestsInPackage(selector.getPackageName(), engineDescriptor));

		discoveryRequest.getSelectorsByType(ClassSelector.class)
				.forEach(selector -> appendTestsInClass(selector.getClass(), engineDescriptor));
		return engineDescriptor;
	}

	private void appendTestsInPackage(String packageName, TestDescriptor engineDescriptor) {
		ReflectionSupport.findAllClassesInPackage(packageName, IS_UNIT_TEST_CONTAINER, name -> true) //
				.stream() //
				.map(aClass -> new UnitTestClassDescriptor(aClass, engineDescriptor)) //
				.forEach(engineDescriptor::addChild);
	}

	private void appendTestsInClass(Class<?> javaClass, TestDescriptor engineDescriptor) {
		if (IS_UNIT_TEST_CONTAINER.test(javaClass)) {
			engineDescriptor.addChild(new UnitTestClassDescriptor(javaClass, engineDescriptor));
		}
	}

	@Override
	public void execute(ExecutionRequest request) {
		TestDescriptor root = request.getRootTestDescriptor();
		new UnitTestExecutor().execute(request, root);
	}

	/** {@inheritDoc} */
	@Override
	public String getId() {
		return TEST_ENGINE_ID;
	}
}
