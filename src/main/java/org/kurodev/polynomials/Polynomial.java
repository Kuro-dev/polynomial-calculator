package org.kurodev.polynomials;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class Polynomial {
    private static final String[] SUPERSCRIPT_DIGITS = {"⁰", "¹", "²", "³", "⁴", "⁵", "⁶", "⁷", "⁸", "⁹"};
    private static final char SUPERSCRIPT_MINUS = '⁻';
    private final int[] polynomial;

    public Polynomial(int[] polynomial) {
        this.polynomial = polynomial;
    }

    public static Polynomial of(String polynomial) {
        int[] parsed = PolynomialParser.parsePolynomial(polynomial);
        return new Polynomial(parsed);
    }

    public static Polynomial of(int[] polynomial) {
        int nonZeroPos = -1;
        for (int i = 0; i < polynomial.length; i++) {
            if (polynomial[i] != 0) {
                nonZeroPos = i;
                break;
            }
        }
        if (nonZeroPos == -1) return null; //no polynomial given
        int[] out = new int[polynomial.length - nonZeroPos];
        System.arraycopy(polynomial, nonZeroPos, out, 0, out.length);
        return new Polynomial(out);
    }

    public static String toSuperScript(int num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(SUPERSCRIPT_DIGITS[num % 10]);
            num /= 10;
        }
        sb.reverse();
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polynomial that = (Polynomial) o;
        return Objects.deepEquals(polynomial, that.polynomial);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(polynomial);
    }

    public boolean isDivisibleBy(Polynomial other) {
        var result = divideBy(other);
        return result != null && result.rest().isZero();
    }

    private Polynomial multiplyByScalarAndShift(int scalar, int shift) {
        // Get the degree of the current polynomial
        int degree = this.getDegree();

        // Create a new array to hold the shifted and multiplied polynomial
        int[] result = new int[degree + shift + 1];

        // Multiply each coefficient by the scalar and shift it
        for (int i = 0; i <= degree; i++) {
            result[i + shift] = this.polynomial[i] * scalar;
        }

        return new Polynomial(result);
    }


    private boolean isZero() {
        return Arrays.stream(polynomial).allMatch(num -> num == 0);
    }

    public DivisionResult divideBy(Polynomial other) {
        int[] result = new int[(polynomial.length - other.polynomial.length) + 1];
        return divideBy(other, result);
    }

    private DivisionResult divideBy(Polynomial divisor, int[] result) {
        if (polynomial.length < divisor.polynomial.length) {
            return new DivisionResult(new Polynomial(result), this); //a ist kleiner, ist also direkt der Rest
        }

        int x = polynomial[0];
        int y = divisor.polynomial[0];
        int gradUnterschied = polynomial.length - divisor.polynomial.length;
        int rs = x / y;
        result[(result.length - 1) - gradUnterschied] = rs;

        Polynomial multiplied = divisor.multiply(rs, gradUnterschied);
        var subtracted = subtract(multiplied);
        if (subtracted.getDegree() >= getDegree()) {
            // If the degree doesn't decrease, there is an issue in the division
            return null;
        }
        return subtracted.divideBy(divisor, result);
    }

    private Polynomial multiply(int b, int delta) {
        int[] result = new int[polynomial.length + delta];
        for (int i = 0; i < polynomial.length; i++) {
            result[i] = polynomial[i] * b;
        }
        return new Polynomial(result);
    }

    Polynomial subtract(Polynomial other) {
        int[] result = new int[Math.max(polynomial.length, other.polynomial.length)];
        int[] x = new int[result.length];
        int[] y = new int[result.length];
        System.arraycopy(polynomial, 0, x, result.length - polynomial.length, polynomial.length);
        System.arraycopy(other.polynomial, 0, y, result.length - other.polynomial.length, other.polynomial.length);
        for (int i = 0; i < result.length; i++) {
            result[i] = x[i] - y[i];
        }
        List<Integer> tmp = new ArrayList<>();
        boolean nonZeroFound = false;
        for (int j : result) {
            if (j != 0 || nonZeroFound) {
                nonZeroFound = true;
                tmp.add(j);
            }
        }
        return new Polynomial(tmp.stream().mapToInt(i -> i).toArray());
    }

    public Polynomial modulo(int n) {
        int[] result = new int[polynomial.length];
        for (int i = 0; i < polynomial.length; i++) {
            result[i] = modulo(polynomial[i], n);
        }
        return new Polynomial(result);
    }

    private int modulo(int a, int b) {
        return ((a % b) + b) % b;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < polynomial.length; i++) {
            int grad = getDegree() - i;
            if (polynomial[i] != 0 || grad == 0) {
                boolean positive = isPositive(i);
                if (!sb.isEmpty() || !positive) {
                    if (positive) {
                        sb.append(" + ");
                    } else {
                        sb.append(" - ");
                    }
                }
                if (grad == 0) {
                    sb.append(Math.abs(polynomial[i]));
                } else if (polynomial[i] != 1) {
                    sb.append(Math.abs(polynomial[i]));
                }

                if (grad == 1)
                    sb.append("x");
                else if (grad != 0)
                    sb.append("x").append(toSuperScript(grad));
            }
        }
        return sb.toString();
    }

    public int solve(int x) {
        int sum = 0;
        for (int i = 0; i < polynomial.length; i++) {
            int exponent = (getDegree()) - i;
            sum += (int) (polynomial[i] * Math.pow(x, exponent));
        }
        return sum;
    }

    /**
     * Has zero values within the specified range
     *
     * @param range
     */
    public boolean hasZeros(int range) {
        for (int i = 0; i < range; i++) {
            if ((solve(i) % range) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * grad des polynomials
     */
    public int getDegree() {
        return polynomial.length - 1;
    }

    public boolean isIrreducible(int mod) {
        if (hasZeros(mod)) return false;

        if (getDegree() < 4) {
            return true; //grad kleiner als 4
        }
        return MathsUtil.findIrreducible(getDegree(), mod).contains(this);
    }

    /**
     * true if the polynomial is of mod n
     */
    public boolean isMod(int n) {
        for (int i : polynomial) {
            if (i < 0 || i >= n) {
                return false;
            }
        }
        return true;
    }

    private boolean isPositive(int index) {
        return polynomial[index] >= 0;
    }

    private boolean eisensteinCriterion(int p) {
        if (polynomial[0] % p == 0) { //leitkoeffizient
            return false;
        }
        for (int i = 1; i < polynomial.length; i++) {
            if (polynomial[i] % p != 0) {
                return false;
            }
        }
        return polynomial[getDegree()] % (int) Math.pow(p, 2) != 0;
    }

    /**
     * @return true wenn die Koeffizienten des Polynoms keine gemeinsamen teiler haben.
     */
    public boolean isPrimitive() {
        for (int i = 0; i < polynomial.length; i++) {
            for (int j = 0; j < polynomial.length; j++) {
                if (i == j) continue;
                int a = polynomial[i];
                int b = polynomial[j];
                if (a != 0 && b != 0) {
                    if (MathsUtil.bcd(a, b) != 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public int getCoefficient(int n) {
        return polynomial[n];
    }

    public int getHighestCoefficient() {
        return polynomial[0];
    }

    public int getLowestCoefficient() {
        return polynomial[getDegree()];
    }

    public int[] toArray() {
        return polynomial;
    }
}
