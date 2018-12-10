package max.com.junit5;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

public class GeneralTestExecutionExceptionHandler implements TestExecutionExceptionHandler {

	@Override
	public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
		System.out.println("Caught exception: " + throwable.getClass() + " " + throwable.getMessage());
	}

}
