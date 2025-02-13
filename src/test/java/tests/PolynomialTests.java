package tests;

import org.junit.jupiter.api.Test;
import org.kurodev.polynomials.Polynomial;

import static org.junit.jupiter.api.Assertions.*;

public class PolynomialTests {

    @Test
    public void multiplyTest() {
        Polynomial a = Polynomial.of("x^4 + 3x^3 + 2x^2 + 6x");
        Polynomial b = Polynomial.of("x^2");
        Polynomial expected = Polynomial.of("x^6 + 3x^5 + 2x^4 + 6x^3");
        assertEquals(expected, a.multiply(b));
    }

    @Test
    public void additionTest() {
        Polynomial a = Polynomial.of("x^4");
        Polynomial b = Polynomial.of("2x^4 + x^2");
        Polynomial expected = Polynomial.of("3x^4 + x^2");
        assertEquals(expected, a.add(b));

        a = Polynomial.of("2");
        b = Polynomial.of("4");
        expected = Polynomial.of("6");
        assertEquals(expected, a.add(b));
    }

    @Test
    public void subtractionTest() {
        Polynomial a = Polynomial.of("x^4 + 3x^3 + 2x^2 + 6x");
        Polynomial b = Polynomial.of("x^4 - 2x^3 + x^2 - 6");
        Polynomial expected = Polynomial.of("5x^3 + x^2 + 6x + 6");
        assertEquals(expected, a.subtract(b));
    }


}
