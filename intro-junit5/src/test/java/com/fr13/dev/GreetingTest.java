package com.fr13.dev;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class GreetingTest {

    private Greeting greeting;

    @BeforeAll
    static void beforeAll() {
        System.out.println("In Before All");
    }

    @BeforeEach
    void setUp() {
        System.out.println("In Before Each");
        greeting = new Greeting();
    }

    @Test
    void helloWorld() {
        System.out.println(greeting.helloWorld());
    }

    @Test
    void testHelloWorld() {
        System.out.println(greeting.helloWorld("John"));
    }

    @AfterEach
    void tearDown() {
        System.out.println("In After Each");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("In After All");
    }
}