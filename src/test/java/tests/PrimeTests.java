package tests;

import org.junit.jupiter.api.Test;
import org.kurodev.polynomials.MathsUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PrimeTests {

    @Test
    public void testPrime() {
       List<Integer> primes = MathsUtil.findPrimes(100_000);
        assertTrue(primes.contains(5281));
        assertTrue(primes.contains(2791));
        assertTrue(primes.contains(7919));
        assertTrue(primes.contains(11939));
        assertTrue(primes.contains(99371));
    }
}
