package max.com.junit5;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.opentest4j.TestAbortedException;

public class AssumptionExceptionHandler implements TestExecutionExceptionHandler {

	@Override
	public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {

		if (throwable instanceof TestAbortedException) {
			System.out.println("Caught exception: " + throwable.getClass() + " " + throwable.getMessage());
			throwable.printStackTrace();
			return;
		}

		// pass it on
		throw throwable;
	}

}
