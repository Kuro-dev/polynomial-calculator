package tests;

import org.junit.jupiter.api.Test;
import org.kurodev.fields.FiniteField;
import org.kurodev.polynomials.MathsUtil;
import org.kurodev.polynomials.Polynomial;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class FiniteFieldsTest {

    @Test
    public void testFiniteFieldsAddition() {
        FiniteField a = FiniteField.ofBase(5);
        System.out.println(a.computeAdditionTable());
    }

    @Test
    public void testFiniteFieldsMultiplication() {
        FiniteField a = FiniteField.ofBase(5);
        System.out.println(a.computeMultiplicationTable());
    }

    @Test
    public void testIsIrreducible() {
        List<Polynomial> result = MathsUtil.findIrreducible(12, 2);
        while (!result.isEmpty()) {
            var poly = result.removeFirst();
            FiniteField a = FiniteField.ofBase(2, poly);
            var table = a.computeMultiplicationTable();
            assertFalse(table.hasZeros(), "Polynomial should be irreducible, but isn't");
        }
    }

    @Test
    public void findRandomIrreducible() {
        var result = MathsUtil.findIrreducible(7, 2);
        result.stream().filter(polynomial -> polynomial.getDegree() == 5).forEach(System.out::println);
    }
}