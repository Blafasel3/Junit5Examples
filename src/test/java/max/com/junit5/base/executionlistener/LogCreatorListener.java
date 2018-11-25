package max.com.junit5.base.executionlistener;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Objects;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;

/**
 * registered on launcher
 * 
 * @author Max
 */
public class LogCreatorListener implements TestExecutionListener {

	private static final String BASE_PATH = "C:\\Users\\maxim\\Eclipseworkspaces\\";

	/**
	 * Constructor
	 */
	public LogCreatorListener() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
		TestExecutionListener.super.executionFinished(testIdentifier, testExecutionResult);

		if (!TestExecutionResult.Status.SUCCESSFUL.equals(testExecutionResult.getStatus())) {

			String logName = buildLogName(testIdentifier);

			PrintWriter printWriter = null;
			try {
				printWriter = new PrintWriter(logName);
				printWriter.print(testIdentifier.toString());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (Objects.nonNull(printWriter)) {
					printWriter.close();
				}
			}
		}
	}

	private String buildLogName(TestIdentifier testIdentifier) {
		return BASE_PATH + testIdentifier.getDisplayName().trim() + ".txt";
	}

}
