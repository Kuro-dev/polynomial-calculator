package org.kurodev.fields;

import org.kurodev.polynomials.MathsUtil;
import org.kurodev.polynomials.Polynomial;

import java.util.Arrays;
import java.util.stream.IntStream;

public class FiniteField {
    private final Polynomial[] baseValues;
    private final Polynomial base;

    /**
     * @param base must be a constant or irreducible polynomial
     */
    public FiniteField(Polynomial base, Polynomial[] baseValues) {
        this.baseValues = baseValues;
        this.base = base;
    }

    public FiniteField(int base, Polynomial[] baseValues) {
        this(new Polynomial(new int[]{base}), baseValues);
    }

    public static FiniteField ofBase(int base) {
        Polynomial[] baseValues = new Polynomial[base];
        baseValues[0] = new Polynomial(new int[]{0});
        IntStream.range(1, base)
                .filter(value -> MathsUtil.bcd(base, value) == 1)
                .forEachOrdered(i -> baseValues[i] = new Polynomial(new int[]{i}));
        return new FiniteField(base, baseValues);
    }

    public static FiniteField ofBase(int mod, String irreducible) {
        return ofBase(mod, Polynomial.of(irreducible));
    }

    public static FiniteField ofBase(int mod, Polynomial irreducible) {
        int degree = irreducible.getDegree();
        var polys = generatePolynomials(degree, mod);
        return new FiniteField(irreducible, polys);
    }

    private static Polynomial[] generatePolynomials(int degree, int mod) {
        int total = (int) Math.pow(mod, degree);
        Polynomial[] result = new Polynomial[total];
        for (int n = 0; n < total; n++) {
            int[] polynom = new int[degree];
            int temp = n;
            for (int i = 0; i < degree; i++) {
                polynom[degree - 1 - i] = temp % degree;
                temp = temp / degree;
            }
            result[n] = new Polynomial(polynom);
        }
        return result;
    }

    public Table computeAdditionTable() {
        Polynomial[][] field = new Polynomial[baseValues.length][baseValues.length];
        for (int y = 0; y < baseValues.length; y++) {
            for (int x = y; x < baseValues.length; x++) {
                field[y][x] = baseValues[x].add(baseValues[y]).modulo(base);
                field[x][y] = field[y][x];
            }
        }
        return new Table("⊕", baseValues, field);
    }

    public Table computeMultiplicationTable() {
        Polynomial[] baseValues = Arrays.copyOfRange(this.baseValues, 1, this.baseValues.length);
        Polynomial[][] field = new Polynomial[baseValues.length][baseValues.length];
        for (int y = 0; y < baseValues.length; y++) {
            for (int x = y; x < baseValues.length; x++) {
                field[y][x] = baseValues[x].multiply(baseValues[y]);
                field[y][x] = field[y][x].truncate();
                field[y][x] = field[y][x].modulo(base);

//                System.out.println("x: " + x + ", y: " + y);
                field[x][y] = field[y][x];
            }
        }
        return new Table("⊙", baseValues, field);
    }

}
