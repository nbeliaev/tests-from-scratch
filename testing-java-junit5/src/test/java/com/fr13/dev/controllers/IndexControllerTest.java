package com.fr13.dev.controllers;

import com.fr13.dev.ControllerTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class IndexControllerTest implements ControllerTests {

    IndexController indexController;

    @BeforeEach
    void setUp() {
        indexController = new IndexController();
    }

    @Test
    @DisplayName("Test proper view name")
    void index() {
        // JUnit
        assertEquals("index", indexController.index());
        // AssertJ
        assertThat(indexController.index()).isEqualTo("index");
    }

    @Test
    @DisplayName("Test exception")
    void oopsHandler() {
        assertThrows(ValueNotFoundException.class, () -> indexController.oopsHandler());
    }

    @Test
    void testTimeout() {
        assertTimeout(Duration.ofMillis(1100), () -> Thread.sleep(1000));
    }

    @Test
    void testAssumptionTrue() {
        assumeTrue("guru".equals(System.getenv("GURU")));
    }

    @Test
    @EnabledOnOs(OS.MAC)
    void testMeOnMac() {

    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testMeOnWindows() {

    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void testMeOnJava8() {

    }

    @Test
    @EnabledIfEnvironmentVariable(named = "USER", matches = "fred")
    void testIfUserFred() {

    }
}