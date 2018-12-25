package max.com.junit5.examples;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;

import max.com.junit5.extensions.parameterresolver.PersonUtilsParameterResolver;
import max.com.junit5.objects.Person;
import max.com.junit5.objects.PersonUtils;

class TestFactoryExample {

	@TestFactory
	@DisplayName("This is a basic example using a TestFactory in JUnit5")
	Stream<DynamicTest> test_dynamicTestExampleNumberOne() {
		return getStringValues().map( //
				string -> DynamicTest.dynamicTest( //
						"Value " + string + " should not be blank", //
						() -> assertFalse(StringUtils.isBlank(string))));
	}

	static Stream<String> getStringValues() {
		return Arrays.asList("a", "b", "c", null).stream();
	}

	@TestFactory
	@ExtendWith(PersonUtilsParameterResolver.class)
	@DisplayName("This is an example using Person objects and a TestFactory in JUnit5")
	Stream<DynamicTest> test_dynamicTestExampleUsingPersons(PersonUtils personUtils) {
		Collection<Person> persons = getPersons();
		return persons.stream().map( //
				person -> DynamicTest.dynamicTest(//
						person.toString(), //
						() -> {
							Person returnedPerson = assertTimeout(ofSeconds(1),
									() -> personUtils.writeToDatabase(person),
									"This should not take more than a second");
							assertTrue(persons.contains(returnedPerson));
						}));
	}

	static Collection<Person> getPersons() {
		return Arrays.asList(new Person("Looping", "Louie", LocalDate.now()));
	}

}
