package max.com.junit5.runner;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;

import org.junit.platform.engine.FilterResult;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestTag;
import org.junit.platform.launcher.PostDiscoveryFilter;

class TagPostDiscoveryFilter implements PostDiscoveryFilter {

	private Collection<Class<? extends Annotation>> tagClasses;

	TagPostDiscoveryFilter(Class<? extends Annotation> tagClass) {
		this(Collections.singleton(tagClass));
	}

	TagPostDiscoveryFilter(Collection<Class<? extends Annotation>> tagClasses) {
		this.tagClasses = tagClasses;
	}

	@Override
	public FilterResult apply(TestDescriptor object) {
		boolean anyMatch = object.getTags().stream() //
				.map(TestTag::getClass) //
				.anyMatch(this::matchesTag);
		return anyMatch ? FilterResult.included("Unit test") : FilterResult.excluded("No unit test");
	}

	private boolean matchesTag(Class<? extends TestTag> tagClass) {
		return tagClasses.contains(tagClass);
	}

}
