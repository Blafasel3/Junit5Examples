package max.com.junit5.base.extensioninterface;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;

import max.com.junit5.extensions.callback.TimingExtension;

@Tag("timed")
@ExtendWith(TimingExtension.class)
public interface TimeExecutionLogger {
	// Nothing to do
}