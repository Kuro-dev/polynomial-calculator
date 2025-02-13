package org.kurodev.polynomials;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//(?=.)([+-])?(\d+)?(?:x(?:\^(\d+))?)?
public class PolynomialParser {
    private static final String SIGN_ID = "sign";
    private static final String BASE_ID = "base";
    private static final String X_ID = "x";
    private static final String EXPONENT_ID = "exponent";
    private static final Pattern pattern = Pattern.compile("(?=.*(?:\\d|x))(?<sign>[+-])?(?<base>\\d+)?(?:(?<x>x)(?:\\^(?<exponent>\\d+))?)?",
            Pattern.CASE_INSENSITIVE | Pattern.COMMENTS);

    public static int[] parsePolynomial(String polynom) {

        polynom = normalise(polynom);
        Map<Integer, Integer> map = polynomialToMap(polynom);
        assert !map.isEmpty() : "Map should not be empty";

        int biggestExponent = map.keySet().stream().max(Integer::compareTo).get();
        int[] result = new int[biggestExponent + 1];
        for (int i = 0; i < result.length; i++) {
            int exponent = (result.length - 1) - i;
            result[i] = map.getOrDefault(exponent, 0);
        }
        return result;
    }

    private static String normalise(String polynome) {
        polynome = polynome.replaceAll("\\s+", "");
        for (int i = 0; i < Polynomial.SUPERSCRIPT_DIGITS.length; i++) {
            polynome = polynome.replaceAll(Polynomial.SUPERSCRIPT_DIGITS[i], "^" + i);
        }
        return polynome;
    }

    private static Map<Integer, Integer> polynomialToMap(String polynomial) {
        Matcher matcher = pattern.matcher(polynomial);
        Map<Integer, Integer> map = new HashMap<>();
        while (matcher.find()) {
            boolean negative = "-".equals(matcher.group(SIGN_ID));
            String baseString = matcher.group(BASE_ID);
            int base = 1;
            if (baseString != null) {
                base = Integer.parseInt(baseString);
            }
            base = negative ? -base : base;

            String xString = matcher.group(X_ID);
            int exponent = 0;
            String exponentString = matcher.group(EXPONENT_ID);
            if (exponentString != null) {
                exponent = Integer.parseInt(exponentString);
            } else if (xString != null) {
                exponent = 1;
            }
            map.put(exponent, map.getOrDefault(exponent, 0) + base);
        }
        return map;
    }
}
