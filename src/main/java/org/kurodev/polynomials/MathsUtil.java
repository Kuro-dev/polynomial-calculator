package org.kurodev.polynomials;

import java.util.*;
import java.util.stream.Collectors;

public class MathsUtil {
    private static final Set<Polynomial> irreducibleCache = new HashSet<>();

    /**
     * Euklidischer algorithmus für GGT
     */
    public static long bcd(long n, long m) {
        if (m > n) {
            return bcd(m, n);
        }
        if (m == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        long x = n % m;
        if (x == 0) {
            return m;
        }
        return bcd(m, x);
    }

    /**
     * Kleinstes gemeinsames vielfaches
     */
    public static long lcm(long n, long m) {
        return (n * m) / bcd(n, m);
    }

    /**
     * sieve of Eratosthenes
     */
    public static List<Integer> findPrimes(int upperLimit) {
        List<Integer> primes = new ArrayList<>();
        primes.add(2);
        for (int i = 3; i <= upperLimit; i += 2) {
            primes.add(i);
        }
        int p = 3;
        int sqrtLimit = (int) Math.sqrt(upperLimit);
        while (p <= sqrtLimit) {
            int temp = p;
            primes.removeIf(prime -> (prime != temp) && (prime % temp == 0));
            p = primes.stream().filter(integer -> integer > temp)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("AHHHH"));
        }
        return primes;
    }

    /**
     * Finding irreducible polynomials using a modified sieve of Eratosthenes
     */
    public static List<Polynomial> findIrreducible(int degree, int mod) {
        if (!findPrimes(mod).contains(mod)) {
            return Collections.emptyList();
        }
        List<Polynomial> polynomials = new ArrayList<>(generatePolynomials(degree, mod));
        polynomials.removeIf(polynomial -> ((polynomial.getLowestCoefficient() == 0))); //alle Polynome entfernen, die keine konstante haben
        polynomials.removeIf(polynomial -> (polynomial.hasZeros(mod))); //alle Polynome entfernen, die nullstellen haben

        Set<Polynomial> irreducibles = irreducibleCache;
        polynomials.removeIf(irreducibles::contains);
        if (polynomials.isEmpty()) {
            Collection<Polynomial> ret = irreducibles.stream().filter(polynomial -> polynomial.getDegree() == degree).collect(Collectors.toCollection(ArrayList::new));
            return new ArrayList<>(ret);
        }
        System.out.println("Checking " + polynomials.size() + " polynomials");
        Polynomial p = polynomials.remove(0);
        irreducibles.add(p);
        int upperBound = Math.round((float) degree / 2);
        while (p.getDegree() <= upperBound) {
            for (Polynomial polynomial : polynomials) {
                if (polynomial.getDegree() >= p.getDegree() * 2) {
                    continue; // Wenn der Faktor größer ist als die Hälfte des Polynoms, brauchen wir nicht zu checken
                }
                if (!polynomial.isDivisibleBy(p)) {
                    irreducibles.add(polynomial);//kann faktorisiert werden, ist nicht irreduzibel
                }
            }
            p = polynomials.remove(0);
            irreducibles.add(p);
        }
        irreducibles.addAll(polynomials);

        System.out.println("Found " + irreducibles.stream().filter(polynomial -> polynomial.getDegree() == degree).count() + " irreducibles in total");
        Collection<Polynomial> ret = irreducibles.stream().filter(polynomial -> polynomial.getDegree() == degree).collect(Collectors.toCollection(ArrayList::new));
        return new ArrayList<>(ret);
    }


    private static Set<Polynomial> generatePolynomials(int degree, int mod) {
        List<int[]> polynomials = new ArrayList<>();
        for (int i = 2; i < Math.pow(mod, degree + 1); i++) {
            polynomials.add(generatePolynomialCoefficients(i, degree, mod));
        }
        return polynomials.stream()
                .map(Polynomial::of)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(HashSet::new));
    }

    private static int[] generatePolynomialCoefficients(int number, int degree, int mod) {
        int[] coefficients = new int[degree + 1];
        for (int i = degree; i >= 0; i--) {
            coefficients[degree - i] = (number / (int) Math.pow(mod, i)) % mod;
        }
        return coefficients;
    }
}

