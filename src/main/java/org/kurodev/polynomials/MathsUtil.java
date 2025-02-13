package org.kurodev.polynomials;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MathsUtil {
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
        List<Polynomial> polynomials = generatePolynomials(degree, mod);
        System.out.println("Checking " + polynomials.size() + " polynomials");
        polynomials.removeIf(polynomial -> (polynomial.getDegree() <= 0)); //alle Polynome grad 0 entfernen
        polynomials.removeIf(polynomial -> ((polynomial.getLowestCoefficient() == 0))); //alle Polynome entfernen, die keine konstante haben
        polynomials.removeIf(polynomial -> (polynomial.hasZeros(mod))); //alle Polynome entfernen, die nullstellen haben

        //alle die grad kleiner 4 sind, sind automatisch irreduzibel
        List<Polynomial> irreducibles = new ArrayList<>();
        System.out.println("Found " + irreducibles.size() + " irreducibles right away");

        polynomials.removeIf(polynomial -> (polynomial.getDegree() < 4));

        for (Polynomial polynomial : polynomials) {
            boolean factorFound = false;
            for (int i = 1; i <= polynomial.getDegree() / 2; i++) {
                for (Polynomial factor : irreducibles) {
                    if (polynomial.getDegree() / 2 < factor.getDegree()) {
                        break; // Wenn der Faktor größer ist als die Hälfte des Polynoms, brauchen wir nicht checken
                    }
                    if (polynomial.isDivisibleBy(factor)) {
                        factorFound = true; //kann faktorisiert werden, ist nicht irreduzibel
                        break;
                    }
                }
                if (factorFound) break;
            }
            if (!factorFound) {
//                System.out.println("Found: " + polynomial);
                irreducibles.add(polynomial);
            }
        }
        System.out.println("Found " + irreducibles.size() + " irreducibles in total");
        return irreducibles;
    }


    public static List<Polynomial> generatePolynomials(int degree, int mod) {
        List<int[]> polynomials = new ArrayList<>();
        for (int i = 2; i < Math.pow(mod, degree + 1); i++) {
            polynomials.add(generatePolynomialCoefficients(i, degree, mod));
        }
        return polynomials.stream()
                .map(Polynomial::of)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static int[] generatePolynomialCoefficients(int number, int degree, int mod) {
        int[] coefficients = new int[degree + 1];
        for (int i = degree; i >= 0; i--) {
            coefficients[degree - i] = (number / (int) Math.pow(mod, i)) % mod;
        }
        return coefficients;
    }
}

