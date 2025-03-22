package tests;

import org.junit.jupiter.api.Test;
import org.kurodev.polynomials.MathsUtil;
import org.kurodev.polynomials.Polynomial;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Source: <a href="https://www.partow.net/programming/polynomials/index.html">List of Polynomials</a>
 */
public class IrreduciblePolynomialsTest {
    @Test
    public void testDegree2() {
        List<Polynomial> result = MathsUtil.findIrreducible(2, 2);
        assertTrue(result.contains(Polynomial.of("x^2 + x^1 + 1")));
    }

    @Test
    public void testDegree3() {
        List<Polynomial> result = MathsUtil.findIrreducible(3, 2);
        assertTrue(result.contains(Polynomial.of("x^3 + x^1 + 1")));
    }

    @Test
    public void testDegree4() {
        List<Polynomial> result = MathsUtil.findIrreducible(4, 2);
        assertTrue(result.contains(Polynomial.of("x^4 + x^1 + 1")));
    }

    @Test
    public void testDegree5() {
        List<Polynomial> result = MathsUtil.findIrreducible(5, 2);
        assertTrue(result.contains(Polynomial.of("x^5 + x^2 + 1")));
        assertTrue(result.contains(Polynomial.of("x^5 + x^4 + x^2 + x^1 + 1")));
        assertTrue(result.contains(Polynomial.of("x^5 + x^4 + x^3 + x^2 + 1")));
    }

    @Test
    public void testDegree6() {
        List<Polynomial> result = MathsUtil.findIrreducible(6, 2);
        assertTrue(result.contains(Polynomial.of("x^6 + x^1 + 1")));
        assertTrue(result.contains(Polynomial.of("x^6 + x^5 + x^2 + x^1 + 1")));
        assertTrue(result.contains(Polynomial.of("x^6 + x^5 + x^3 + x^2 + 1")));
    }

    @Test
    public void testDegree7() {
        var polynomials = """
                x^7 + x^1 + 1
                x^7 + x^3 + 1
                x^7 + x^3 + x^2 + x^1 + 1
                x^7 + x^4 + x^3 + x^2 + 1
                x^7 + x^5 + x^4 + x^3 + x^2 + x^1 + 1
                x^7 + x^6 + x^3 + x^1 + 1
                x^7 + x^6 + x^4 + x^2 + 1
                x^7 + x^6 + x^5 + x^2 + 1
                x^7 + x^6 + x^5 + x^4 + x^2 + x^1 + 1
                """;
        List<Polynomial> result = MathsUtil.findIrreducible(7, 2);
        List<Polynomial> expected = Arrays.stream(polynomials.split("\n")).map(Polynomial::of).toList();
        for (Polynomial polynomial : expected) {
            assertTrue(result.contains(polynomial), "List should contain " + polynomial + ", but doesn't");
        }
    }

    @Test
    public void testDegree8() {
        var polynomials = """
                x^8 + x^4 + x^3 + x^2 + 1
                x^8 + x^5 + x^3 + x^1 + 1
                x^8 + x^6 + x^4 + x^3 + x^2 + x^1 + 1
                x^8 + x^6 + x^5 + x^1 + 1
                x^8 + x^6 + x^5 + x^2 + 1
                x^8 + x^6 + x^5 + x^3 + 1
                x^8 + x^7 + x^6 + x^1 + 1
                x^8 + x^7 + x^6 + x^5 + x^2 + x^1 + 1
                """;
        List<Polynomial> result = MathsUtil.findIrreducible(8, 2);
        List<Polynomial> expected = Arrays.stream(polynomials.split("\n")).map(Polynomial::of).toList();
        for (Polynomial polynomial : expected) {
            assertTrue(result.contains(polynomial), "List should contain " + polynomial + ", but doesn't");
        }
    }

    @Test
    public void testDegree9() {
        var polynomials = """
                x^9 + x^4 + 1
                x^9 + x^5 + x^3 + x^2 + 1
                x^9 + x^6 + x^4 + x^3 + 1
                x^9 + x^6 + x^5 + x^3 + x^2 + x^1 + 1
                x^9 + x^6 + x^5 + x^4 + x^2 + x^1 + 1
                x^9 + x^7 + x^6 + x^4 + x^3 + x^1 + 1
                x^9 + x^8 + x^4 + x^1 + 1
                x^9 + x^8 + x^5 + x^4 + 1
                x^9 + x^8 + x^6 + x^5 + 1
                x^9 + x^8 + x^6 + x^5 + x^3 + x^1 + 1
                x^9 + x^8 + x^7 + x^2 + 1
                x^9 + x^8 + x^7 + x^3 + x^2 + x^1 + 1
                x^9 + x^8 + x^7 + x^6 + x^5 + x^1 + 1
                x^9 + x^8 + x^7 + x^6 + x^5 + x^3 + 1
                """;
        List<Polynomial> result = MathsUtil.findIrreducible(9, 2);
        List<Polynomial> expected = Arrays.stream(polynomials.split("\n")).map(Polynomial::of).toList();
        for (Polynomial polynomial : expected) {
            assertTrue(result.contains(polynomial), "List should contain " + polynomial + ", but doesn't");
        }
    }

    @Test
    public void testDegree10() {
        var polynomials = """
                x^10 + x^3 + 1
                x^10 + x^4 + x^3 + x^1 + 1
                x^10 + x^6 + x^5 + x^3 + x^2 + x^1 + 1
                x^10 + x^8 + x^3 + x^2 + 1
                x^10 + x^8 + x^4 + x^3 + 1
                x^10 + x^8 + x^5 + x^1 + 1
                x^10 + x^8 + x^5 + x^4 + 1
                x^10 + x^8 + x^7 + x^6 + x^5 + x^2 + 1
                x^10 + x^8 + x^7 + x^6 + x^5 + x^4 + x^3 + x^1 + 1
                x^10 + x^9 + x^4 + x^1 + 1
                x^10 + x^9 + x^6 + x^5 + x^4 + x^3 + x^2 + x^1 + 1
                x^10 + x^9 + x^8 + x^6 + x^3 + x^2 + 1
                x^10 + x^9 + x^8 + x^6 + x^5 + x^1 + 1
                x^10 + x^9 + x^8 + x^7 + x^6 + x^5 + x^4 + x^3 + 1
                """;
        List<Polynomial> result = MathsUtil.findIrreducible(10, 2);
        List<Polynomial> expected = Arrays.stream(polynomials.split("\n")).map(Polynomial::of).toList();
        for (Polynomial polynomial : expected) {
            assertTrue(result.contains(polynomial), "List should contain " + polynomial + ", but doesn't");
        }
    }

    @Test
    public void testDegree11() {
        var polynomials = """
                x^11 + x^2 + 1
                x^11 + x^5 + x^3 + x^1 + 1
                x^11 + x^5 + x^3 + x^2 + 1
                x^11 + x^6 + x^5 + x^1 + 1
                x^11 + x^7 + x^3 + x^2 + 1
                x^11 + x^8 + x^5 + x^2 + 1
                x^11 + x^8 + x^6 + x^5 + x^4 + x^1 + 1
                x^11 + x^8 + x^6 + x^5 + x^4 + x^3 + x^2 + x^1 + 1
                x^11 + x^9 + x^4 + x^1 + 1
                x^11 + x^9 + x^8 + x^7 + x^4 + x^1 + 1
                x^11 + x^10 + x^3 + x^2 + 1
                x^11 + x^10 + x^7 + x^4 + x^3 + x^1 + 1
                x^11 + x^10 + x^8 + x^7 + x^5 + x^4 + x^3 + x^1 + 1
                x^11 + x^10 + x^9 + x^8 + x^3 + x^1 + 1
                """;
        List<Polynomial> result = MathsUtil.findIrreducible(11, 2);
        List<Polynomial> expected = Arrays.stream(polynomials.split("\n")).map(Polynomial::of).toList();
        for (Polynomial polynomial : expected) {
            assertTrue(result.contains(polynomial), "List should contain " + polynomial + ", but doesn't");
        }
    }

    @Test
    public void testDegree12() {
        var polynomials = """
                x^12 + x^6 + x^4 + x^1 + 1
                x^12 + x^9 + x^3 + x^2 + 1
                x^12 + x^9 + x^8 + x^3 + x^2 + x^1 + 1
                x^12 + x^10 + x^9 + x^8 + x^6 + x^2 + 1
                x^12 + x^10 + x^9 + x^8 + x^6 + x^5 + x^4 + x^2 + 1
                x^12 + x^11 + x^6 + x^4 + x^2 + x^1 + 1
                x^12 + x^11 + x^9 + x^5 + x^3 + x^1 + 1
                x^12 + x^11 + x^9 + x^7 + x^6 + x^4 + 1
                x^12 + x^11 + x^9 + x^7 + x^6 + x^5 + 1
                x^12 + x^11 + x^9 + x^8 + x^7 + x^4 + 1
                x^12 + x^11 + x^9 + x^8 + x^7 + x^5 + x^2 + x^1 + 1
                x^12 + x^11 + x^10 + x^5 + x^2 + x^1 + 1
                x^12 + x^11 + x^10 + x^8 + x^6 + x^4 + x^3 + x^1 + 1
                x^12 + x^11 + x^10 + x^9 + x^8 + x^7 + x^5 + x^4 + x^3 + x^1 + 1
                """;
        List<Polynomial> result = MathsUtil.findIrreducible(12, 2);
        List<Polynomial> expected = Arrays.stream(polynomials.split("\n")).map(Polynomial::of).toList();
        for (Polynomial polynomial : expected) {
            assertTrue(result.contains(polynomial), "List should contain " + polynomial + ", but doesn't");
        }
    }

    @Test
    public void testDegree13() {
        var polynomials = """
                x^13 + x^4 + x^3 + x^1 + 1
                x^13 + x^9 + x^7 + x^5 + x^4 + x^3 + x^2 + x^1 + 1
                x^13 + x^9 + x^8 + x^7 + x^5 + x^1 + 1
                x^13 + x^10 + x^9 + x^7 + x^5 + x^4 + 1
                x^13 + x^10 + x^9 + x^8 + x^6 + x^3 + x^2 + x^1 + 1
                x^13 + x^11 + x^8 + x^7 + x^4 + x^1 + 1
                x^13 + x^11 + x^10 + x^9 + x^8 + x^7 + x^6 + x^5 + x^4 + x^3 + x^2 + x^1 + 1
                x^13 + x^12 + x^6 + x^5 + x^4 + x^3 + 1
                x^13 + x^12 + x^8 + x^7 + x^6 + x^5 + 1
                x^13 + x^12 + x^9 + x^8 + x^4 + x^2 + 1
                x^13 + x^12 + x^10 + x^8 + x^6 + x^4 + x^3 + x^2 + 1
                x^13 + x^12 + x^11 + x^5 + x^2 + x^1 + 1
                x^13 + x^12 + x^11 + x^8 + x^7 + x^6 + x^4 + x^1 + 1
                x^13 + x^12 + x^11 + x^9 + x^5 + x^3 + 1
                """;
        List<Polynomial> result = MathsUtil.findIrreducible(13, 2);
        List<Polynomial> expected = Arrays.stream(polynomials.split("\n")).map(Polynomial::of).toList();
        for (Polynomial polynomial : expected) {
            assertTrue(result.contains(polynomial), "List should contain " + polynomial + ", but doesn't");
        }
    }

    @Test
    public void testDegree14() {
        var polynomials = """
                x^14 + x^8 + x^6 + x^1 + 1
                x^14 + x^10 + x^6 + x^1 + 1
                x^14 + x^10 + x^9 + x^7 + x^6 + x^4 + x^3 + x^1 + 1
                x^14 + x^11 + x^6 + x^1 + 1
                x^14 + x^11 + x^9 + x^6 + x^5 + x^2 + 1
                x^14 + x^12 + x^9 + x^8 + x^7 + x^6 + x^5 + x^4 + 1
                x^14 + x^12 + x^11 + x^9 + x^8 + x^7 + x^6 + x^5 + x^3 + x^1 + 1
                x^14 + x^12 + x^11 + x^10 + x^9 + x^7 + x^4 + x^3 + 1
                x^14 + x^13 + x^6 + x^5 + x^3 + x^1 + 1
                x^14 + x^13 + x^10 + x^8 + x^7 + x^5 + x^4 + x^3 + x^2 + x^1 + 1
                x^14 + x^13 + x^11 + x^6 + x^5 + x^4 + x^2 + x^1 + 1
                x^14 + x^13 + x^11 + x^8 + x^5 + x^3 + x^2 + x^1 + 1
                x^14 + x^13 + x^12 + x^11 + x^10 + x^7 + x^6 + x^1 + 1
                x^14 + x^13 + x^12 + x^11 + x^10 + x^9 + x^6 + x^5 + 1
                """;
        List<Polynomial> result = MathsUtil.findIrreducible(14, 2);
        List<Polynomial> expected = Arrays.stream(polynomials.split("\n")).map(Polynomial::of).toList();
        for (Polynomial polynomial : expected) {
            assertTrue(result.contains(polynomial), "List should contain " + polynomial + ", but doesn't");
        }
    }

    @Test
    public void testDegree15() {
        var polynomials = """
                x^15 + x^1 + 1
                x^15 + x^4 + 1
                x^15 + x^7 + 1
                x^15 + x^7 + x^6 + x^3 + x^2 + x^1 + 1
                x^15 + x^10 + x^5 + x^1 + 1
                x^15 + x^10 + x^5 + x^4 + 1
                x^15 + x^10 + x^5 + x^4 + x^2 + x^1 + 1
                x^15 + x^10 + x^9 + x^7 + x^5 + x^3 + 1
                x^15 + x^10 + x^9 + x^8 + x^5 + x^3 + 1
                x^15 + x^11 + x^7 + x^6 + x^2 + x^1 + 1
                x^15 + x^12 + x^3 + x^1 + 1
                x^15 + x^12 + x^5 + x^4 + x^3 + x^2 + 1
                x^15 + x^12 + x^11 + x^8 + x^7 + x^6 + x^4 + x^2 + 1
                x^15 + x^14 + x^13 + x^12 + x^11 + x^10 + x^9 + x^8 + x^7 + x^6 + x^5 + x^4 + x^3 + x^2 + 1
                """;
        List<Polynomial> result = MathsUtil.findIrreducible(15, 2);
        List<Polynomial> expected = Arrays.stream(polynomials.split("\n")).map(Polynomial::of).toList();
        for (Polynomial polynomial : expected) {
            assertTrue(result.contains(polynomial), "List should contain " + polynomial + ", but doesn't");
        }
    }

    @Test
    public void testDegree16() {
        var polynomials = """
                x^16 + x^9 + x^8 + x^7 + x^6 + x^4 + x^3 + x^2 + 1
                x^16 + x^12 + x^3 + x^1 + 1
                x^16 + x^12 + x^7 + x^2 + 1
                x^16 + x^13 + x^12 + x^10 + x^9 + x^7 + x^6 + x^1 + 1
                x^16 + x^13 + x^12 + x^11 + x^7 + x^6 + x^3 + x^1 + 1
                x^16 + x^13 + x^12 + x^11 + x^10 + x^6 + x^2 + x^1 + 1
                x^16 + x^14 + x^10 + x^8 + x^3 + x^1 + 1
                x^16 + x^14 + x^13 + x^12 + x^6 + x^5 + x^3 + x^2 + 1
                x^16 + x^14 + x^13 + x^12 + x^10 + x^7 + 1
                x^16 + x^15 + x^10 + x^6 + x^5 + x^3 + x^2 + x^1 + 1
                x^16 + x^15 + x^11 + x^9 + x^8 + x^7 + x^5 + x^4 + x^2 + x^1 + 1
                x^16 + x^15 + x^11 + x^10 + x^7 + x^6 + x^5 + x^3 + x^2 + x^1 + 1
                x^16 + x^15 + x^11 + x^10 + x^9 + x^6 + x^2 + x^1 + 1
                x^16 + x^15 + x^11 + x^10 + x^9 + x^8 + x^6 + x^4 + x^2 + x^1 + 1
                """;
        List<Polynomial> result = MathsUtil.findIrreducible(16, 2);
        List<Polynomial> expected = Arrays.stream(polynomials.split("\n")).map(Polynomial::of).toList();
        for (Polynomial polynomial : expected) {
            assertTrue(result.contains(polynomial), "List should contain " + polynomial + ", but doesn't");
        }
    }

    @Test
    public void testDegree17() {
        var polynomials = """
                x^17 + x^3 + 1
                x^17 + x^3 + x^2 + x^1 + 1
                x^17 + x^5 + 1
                x^17 + x^6 + 1
                x^17 + x^8 + x^4 + x^3 + 1
                x^17 + x^8 + x^7 + x^6 + x^4 + x^3 + 1
                x^17 + x^10 + x^9 + x^8 + x^6 + x^5 + x^3 + x^2 + 1
                x^17 + x^12 + x^6 + x^3 + x^2 + x^1 + 1
                x^17 + x^12 + x^9 + x^5 + x^4 + x^3 + x^2 + x^1 + 1
                x^17 + x^12 + x^9 + x^7 + x^6 + x^4 + x^3 + x^2 + 1
                x^17 + x^14 + x^11 + x^7 + x^5 + x^3 + x^2 + x^1 + 1
                x^17 + x^15 + x^13 + x^11 + x^9 + x^7 + x^5 + x^3 + 1
                x^17 + x^15 + x^13 + x^11 + x^9 + x^7 + x^6 + x^4 + x^2 + x^1 + 1
                x^17 + x^16 + x^3 + x^1 + 1
                """;
        List<Polynomial> result = MathsUtil.findIrreducible(17, 2);
        List<Polynomial> expected = Arrays.stream(polynomials.split("\n")).map(Polynomial::of).toList();
        for (Polynomial polynomial : expected) {
            assertTrue(result.contains(polynomial), "List should contain " + polynomial + ", but doesn't");
        }
    }

    @Test
    public void testDegree18() {
        var polynomials = """
                x^18 + x^5 + x^4 + x^3 + x^2 + x^1 + 1
                x^18 + x^7 + 1
                x^18 + x^8 + x^2 + x^1 + 1
                x^18 + x^9 + x^7 + x^6 + x^5 + x^4 + 1
                x^18 + x^9 + x^8 + x^6 + x^5 + x^4 + x^2 + x^1 + 1
                x^18 + x^9 + x^8 + x^7 + x^6 + x^4 + x^2 + x^1 + 1
                x^18 + x^10 + x^7 + x^5 + 1
                x^18 + x^10 + x^8 + x^5 + 1
                x^18 + x^10 + x^8 + x^7 + x^6 + x^5 + x^4 + x^3 + x^2 + x^1 + 1
                x^18 + x^10 + x^9 + x^3 + 1
                x^18 + x^13 + x^6 + x^4 + 1
                x^18 + x^15 + x^5 + x^2 + 1
                x^18 + x^15 + x^9 + x^2 + 1
                """;
        List<Polynomial> result = MathsUtil.findIrreducible(18, 2);
        List<Polynomial> expected = Arrays.stream(polynomials.split("\n")).map(Polynomial::of).toList();
        for (Polynomial polynomial : expected) {
            assertTrue(result.contains(polynomial), "List should contain " + polynomial + ", but doesn't");
        }
    }

    @Test
    public void testDegree19() {
        var polynomials = """
                x^19 + x^5 + x^2 + x^1 + 1
                x^19 + x^5 + x^4 + x^3 + x^2 + x^1 + 1
                x^19 + x^6 + x^2 + x^1 + 1
                x^19 + x^6 + x^5 + x^3 + x^2 + x^1 + 1
                x^19 + x^6 + x^5 + x^4 + x^3 + x^2 + 1
                x^19 + x^7 + x^5 + x^3 + x^2 + x^1 + 1
                x^19 + x^8 + x^7 + x^5 + 1
                x^19 + x^8 + x^7 + x^5 + x^4 + x^3 + x^2 + x^1 + 1
                x^19 + x^8 + x^7 + x^6 + x^4 + x^3 + x^2 + x^1 + 1
                x^19 + x^9 + x^8 + x^5 + 1
                x^19 + x^9 + x^8 + x^6 + x^5 + x^3 + x^2 + x^1 + 1
                x^19 + x^9 + x^8 + x^7 + x^4 + x^3 + x^2 + x^1 + 1
                x^19 + x^11 + x^9 + x^8 + x^7 + x^6 + x^5 + x^4 + x^3 + x^2 + 1
                x^19 + x^11 + x^10 + x^8 + x^7 + x^5 + x^4 + x^3 + x^2 + x^1 + 1
                """;
        List<Polynomial> result = MathsUtil.findIrreducible(19, 2);
        List<Polynomial> expected = Arrays.stream(polynomials.split("\n")).map(Polynomial::of).toList();
        for (Polynomial polynomial : expected) {
            assertTrue(result.contains(polynomial), "List should contain " + polynomial + ", but doesn't");
        }
    }

}
