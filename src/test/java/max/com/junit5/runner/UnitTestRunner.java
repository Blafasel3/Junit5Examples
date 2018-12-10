package max.com.junit5.runner;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.junit.platform.engine.Filter;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import max.com.junit5.base.annotation.IntegrationTest;
import max.com.junit5.base.annotation.UnitTest;
import max.com.junit5.base.executionlistener.LogCreatorListener;

public class UnitTestRunner {

	public static void main(String[] args) throws FileNotFoundException {
		LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request() //
				.selectors(selectPackage("max.com.junit5.objects")) //
				.filters(includeClassNamePatterns("^(Test.*|.+[.$]Test.*|.*Tests?)$")) //
				.filters(provideFilter().toArray(new Filter<?>[] {})) //
				.build();

		Launcher launcher = LauncherFactory.create();

		// Register a listener of your choice
		SummaryGeneratingListener listener = new SummaryGeneratingListener();
		TestExecutionListener logCreatorListener = new LogCreatorListener();
		launcher.registerTestExecutionListeners(listener, logCreatorListener);

		launcher.execute(request);
		TestExecutionSummary summary = listener.getSummary();
		summary.printTo(new PrintWriter("C:\\Users\\maxim\\Eclipseworkspaces\\succesful.txt"));
		summary.printFailuresTo(new PrintWriter("C:\\Users\\maxim\\Eclipseworkspaces\\failures.txt"));
	}

	protected static Collection<Filter<?>> provideFilter() {
		Collection<Class<? extends Annotation>> clazzes = new HashSet<>();
		clazzes.add(UnitTest.class);
		clazzes.add(IntegrationTest.class);
		return Arrays.asList(new TagPostDiscoveryFilter(clazzes));
	}
}
