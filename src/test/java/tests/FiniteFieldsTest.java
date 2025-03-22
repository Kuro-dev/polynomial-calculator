package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kurodev.fields.FiniteField;
import org.kurodev.polynomials.MathsUtil;
import org.kurodev.polynomials.Polynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        List<Polynomial> result = MathsUtil.findIrreducible(4, 2);
//        result.removeIf(polynomial -> polynomial.getDegree() != 4);
        for (Polynomial poly : result) {
            FiniteField a = FiniteField.ofBase(2, poly);
            var table = a.computeMultiplicationTable();
            Assertions.assertFalse(table.hasZeros(), poly + " is not irreducible!");
        }
    }

    @Test
    public void findRandomIrreducible() {
        var result = MathsUtil.findIrreducible(7, 2);
        result.stream().filter(polynomial -> polynomial.getDegree() == 5).forEach(System.out::println);
    }
}