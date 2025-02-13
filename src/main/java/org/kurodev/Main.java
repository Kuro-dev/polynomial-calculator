package org.kurodev;

import org.kurodev.polynomials.MathsUtil;
import org.kurodev.polynomials.Polynomial;

public class Main {
    public static void main(String[] args) {
        Polynomial a = Polynomial.of("2x^4+x^2+2");
        Polynomial b = Polynomial.of("x^3+2x+1");
        MathsUtil.findIrreducible(3, 7);
    }
}