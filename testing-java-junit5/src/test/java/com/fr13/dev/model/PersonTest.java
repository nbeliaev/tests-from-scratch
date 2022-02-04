package com.fr13.dev.model;

import com.fr13.dev.ModelTests;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest implements ModelTests {

    @Test
    void groupedAssertions() {
        Person person = new Person(1L, "Joe", "Buck");
        assertAll("Test props set",
                () -> assertEquals(person.getFirstName(), "Joe"),
                () -> assertEquals(person.getLastName(), "Buck")
        );
    }

    @RepeatedTest(value = 10, name = "{displayName} : {currentRepetition}\"")
    @DisplayName("My repeated test")
    void repeatedTest() {

    }

    @RepeatedTest(5)
    void myRepeatedTestWithDI(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        System.out.println(testInfo.getDisplayName() + ": " + repetitionInfo.getCurrentRepetition());
    }

    @DisplayName("Value source")
    @ParameterizedTest(name = "{displayName} | [{index}] {arguments}")
    @ValueSource(strings = {"Spring", "Guru", "Framework"})
    void testValueSource(String val) {
        System.out.println(val);
    }

    @DisplayName("CSV source")
    @ParameterizedTest(name = "{displayName} | [{index}] {arguments}")
    @CsvSource({"Test0, 0", "Test1, 1"})
    void testValueSource(String name, int count) {
        System.out.println(name + " - " + count);
    }
}