package tests;

import org.junit.jupiter.api.Test;
import org.kurodev.polynomials.Polynomial;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PolynomialTests {

    @Test
    public void testIrreducible() {
        Polynomial a = Polynomial.of("x^2+x+1");
        assertTrue(a.isIrreducible(2));
    }

    @Test
    public void testNotIrreducible() {
        Polynomial a = Polynomial.of("x^2+x");
        assertFalse(a.isIrreducible(2));
    }



    @Test
    public void p() {
        Polynomial a = Polynomial.of("2x^4+3x^2+4");
        Polynomial b = Polynomial.of("x^2+x+1");
        System.out.println(a.divideBy(b));
    }
}
