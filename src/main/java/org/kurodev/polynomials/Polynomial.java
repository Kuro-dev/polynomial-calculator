package org.kurodev.polynomials;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;


public class Polynomial {
    public static final Polynomial EMPTY = Polynomial.of(new int[0]);
    public static final String[] SUPERSCRIPT_DIGITS = {"⁰", "¹", "²", "³", "⁴", "⁵", "⁶", "⁷", "⁸", "⁹"};
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

    public Polynomial truncate() {
        if (getDegree() == 0) return this;

        int firstNonZero = 0;
        while (firstNonZero < polynomial.length && polynomial[firstNonZero] == 0) {
            firstNonZero++;
        }
        if (firstNonZero == polynomial.length) return new Polynomial(new int[]{0});
        return new Polynomial(Arrays.copyOfRange(polynomial, firstNonZero, polynomial.length));
    }

    public Polynomial inverse() {
        int[] inv = new int[polynomial.length];
        for (int i = 0; i < polynomial.length; i++) {
            inv[i] = -polynomial[i];
        }
        return new Polynomial(inv);
    }

    public Polynomial add(Polynomial other) {
        int[] result = new int[Math.max(polynomial.length, other.polynomial.length)];
        int[] x = new int[result.length];
        int[] y = new int[result.length];
        System.arraycopy(polynomial, 0, x, result.length - polynomial.length, polynomial.length);
        System.arraycopy(other.polynomial, 0, y, result.length - other.polynomial.length, other.polynomial.length);
        for (int i = 0; i < result.length; i++) {
            result[i] = x[i] + y[i];
        }
        return new Polynomial(result).truncate();
    }

    public Polynomial multiply(Polynomial other) {
        int[] result = new int[getDegree() + other.getDegree() + 1];
        for (int a = 0; a < polynomial.length; a++) {
            for (int b = 0; b < other.polynomial.length; b++) {
                result[a + b] += polynomial[a] * other.polynomial[b];
            }
        }
        return new Polynomial(result);
    }

    public boolean isDivisibleBy(Polynomial other) {
        var result = divideBy(other);
        return result != null && result.rest().isZero();
    }

    public boolean isZero() {
        return Arrays.stream(polynomial).allMatch(num -> num == 0);
    }

    public DivisionResult divideBy(Polynomial other) {
        if (polynomial.length < other.polynomial.length) {
            return new DivisionResult(EMPTY, this); //a ist kleiner, ist also direkt der Rest
        }
        int[] result = new int[(polynomial.length - other.polynomial.length) + 1];
        return divideBy(other, result);
    }

    private DivisionResult divideBy(Polynomial divisor, int[] result) {
        if (getDegree() == 0 && getDegree() == divisor.getDegree()) {
            return new DivisionResult(polynomial[0] / divisor.polynomial[0], polynomial[0] % divisor.polynomial[0]);
        }

        if (this.polynomial.length < divisor.polynomial.length) {
            return new DivisionResult(new Polynomial(result), this); //a ist kleiner, ist also direkt der Rest
        }
        int x = polynomial[0];
        int y = divisor.polynomial[0];
        int degreeDelta = polynomial.length - divisor.polynomial.length;
        double factor = (double) x / y;
        result[(result.length - 1) - degreeDelta] = (int) factor;

        Polynomial multiplied = divisor.multiply(factor, degreeDelta);
        var subtracted = subtract(multiplied);
        if (subtracted.getDegree() >= getDegree()) {
            // If the degree doesn't decrease, there is an issue in the division
            return null;
        }
        return subtracted.divideBy(divisor, result);
    }

    private boolean isBiggerThan(Polynomial other) {
        if (this.polynomial.length > other.polynomial.length)
            return true;
        if (this.polynomial.length < other.polynomial.length)
            return false;

        for (int i = 0; i < this.polynomial.length; i++) {
            if (this.polynomial[i] > other.polynomial[i])
                return true;
            if (this.polynomial[i] < other.polynomial[i])
                return false;
        }
        return false;
    }

    private Polynomial multiply(double b, int delta) {
        int[] result = new int[polynomial.length + delta];
        for (int i = 0; i < polynomial.length; i++) {
            result[i] = (int) (polynomial[i] * b);
        }
        return new Polynomial(result);
    }

    public Polynomial subtract(Polynomial other) {
        return this.add(other.inverse());
    }

    public Polynomial modulo(int n) {
        int[] result = new int[polynomial.length];
        for (int i = 0; i < polynomial.length; i++) {
            result[i] = modulo(polynomial[i], n);
        }
        return new Polynomial(result);
    }

    public Polynomial modulo(Polynomial other) {
        return divideBy(other).rest();
    }

    private int modulo(int a, int b) {
        return ((a % b) + b) % b;
    }

    @Override
    public String toString() {
        return toString(Polynomial::toSuperScript);
    }

    public String toNonSuperscriptString() {
        return toString(deg -> "^" + deg);
    }

    private String toString(Function<Integer, String> degreeConverter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < polynomial.length; i++) {
            int grad = getDegree() - i;
            if (polynomial[i] != 0 || this.getDegree() == 0) {
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
                    sb.append("x").append(degreeConverter.apply(grad));
            }
        }
        return sb.toString();
    }

    public int solve(int x) {
        int sum = 0;
        for (int i = 0; i < polynomial.length; i++) {
            int exponent = getDegree() - i;
            sum += (int) (polynomial[i] * Math.pow(x, exponent));
        }
        return sum;
    }

    /**
     * Has zero values within the specified range
     *
     * @param range the Modulo
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
     * grad des polynomials. GGF falsch, wenn der höchste exponent 0 ist.
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

    private boolean isPositive(int index) {
        return polynomial[index] >= 0;
    }

    /**
     * @return true, wenn die Koeffizienten des Polynoms keine gemeinsamen teiler haben.
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
