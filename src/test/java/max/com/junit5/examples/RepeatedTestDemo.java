package max.com.junit5.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import max.com.junit5.base.annotation.UnitTest;

@UnitTest
class RepeatedTestDemo {

	@DisplayName("This is a repeated test")
	@RepeatedTest(value = 5, name = RepeatedTest.LONG_DISPLAY_NAME)
	void repeatedTest() {
		assertEquals(2 * 2, 4);
	}

}
