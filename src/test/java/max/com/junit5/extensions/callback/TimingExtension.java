package max.com.junit5.extensions.callback;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.logging.Logger;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

public class TimingExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback, BeforeEachCallback {

	private static final Logger logger = Logger.getLogger(TimingExtension.class.getName());

	private static final String START_TIME = "test start time";

	@Override
	public void beforeTestExecution(ExtensionContext context) throws Exception {
		logger.info(() -> context.getRequiredTestMethod().getName());
		getStore(context).put(START_TIME, System.currentTimeMillis());
	}

	@Override
	public void afterTestExecution(ExtensionContext context) throws Exception {
		Method testMethod = context.getRequiredTestMethod();
		long startTime = getStore(context).remove(START_TIME, long.class);
		long duration = System.currentTimeMillis() - startTime;
		logger.info(() -> String.format("Method [%s] took %s ms.", testMethod.getName(), duration));
	}

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		Set<String> tags = context.getTags();
		logger.info("beforeEach");
		logger.info("Tags: " + tags.toString());
	}

	private Store getStore(ExtensionContext context) {
		return context.getStore(Namespace.create(getClass(), context.getRequiredTestMethod()));
	}

}
