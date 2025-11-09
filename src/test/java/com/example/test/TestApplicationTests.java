package com.example.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TestApplicationTests {
    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void testAddition() {
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    void testSubtraction() {
        int result = calculator.subtract(5, 2);
        assertEquals(3, result);
    }

    @Test
    @DisplayName("Testing multiplication")
    void testMultiplication() {
        assertEquals(10, calculator.multiply(5, 2));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, 2",
            "2, 3, 6",
            "10, 5, 50"
    })
    void testMulti(int a, int b, int expected) {
        assertEquals(expected, calculator.multiply(a, b));
    }
}
