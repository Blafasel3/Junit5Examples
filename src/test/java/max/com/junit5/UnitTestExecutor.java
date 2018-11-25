package max.com.junit5;

import java.util.logging.Logger;

import org.junit.platform.commons.util.ReflectionUtils;
import org.junit.platform.engine.ExecutionRequest;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;

public class UnitTestExecutor {

	public void execute(ExecutionRequest request, TestDescriptor descriptor) {
		if (descriptor instanceof EngineDescriptor)
			executeContainer(request, descriptor);
		if (descriptor instanceof UnitTestClassDescriptor)
			executeContainer(request, descriptor);
		if (descriptor instanceof UnitTestMethodDescriptor)
			executeMethod(request, (UnitTestMethodDescriptor) descriptor);
	}

	private void executeMethod(ExecutionRequest request, UnitTestMethodDescriptor methodTestDescriptor) {
		request.getEngineExecutionListener().executionStarted(methodTestDescriptor);
		TestExecutionResult executionResult = executeTestMethod(methodTestDescriptor);
		request.getEngineExecutionListener().executionFinished(methodTestDescriptor, executionResult);
	}

	private TestExecutionResult executeTestMethod(UnitTestMethodDescriptor methodTestDescriptor) {
		Object testInstance;
		try {
			testInstance = ReflectionUtils.newInstance(methodTestDescriptor.getTestClass());
		} catch (Throwable throwable) {
			String message = String.format( //
					"Cannot create instance of class '%s'. Maybe it has no default constructor?", //
					methodTestDescriptor.getTestClass() //
			);
			return TestExecutionResult.failed(new RuntimeException(message, throwable));
		}
		return invokeTestMethod(methodTestDescriptor, testInstance);
	}

	private TestExecutionResult invokeTestMethod(UnitTestMethodDescriptor methodTestDescriptor, Object testInstance) {
		try {

			ReflectionUtils.invokeMethod(methodTestDescriptor.getTestMethod(), testInstance);

			return TestExecutionResult.successful();
		} catch (Throwable throwable) {
			Logger.getLogger(getClass().getSimpleName()).warning(() -> String.format( //
					"Test '%s' failed for instance '%s'", //
					methodTestDescriptor.getDisplayName(), //
					testInstance.toString())); //
			return TestExecutionResult.failed(throwable);
		}
	}

	private void executeContainer(ExecutionRequest request, TestDescriptor containerDescriptor) {
		request.getEngineExecutionListener().executionStarted(containerDescriptor);
		for (TestDescriptor descriptor : containerDescriptor.getChildren()) {
			execute(request, descriptor);
		}
		request.getEngineExecutionListener().executionFinished(containerDescriptor, TestExecutionResult.successful());
	}
}