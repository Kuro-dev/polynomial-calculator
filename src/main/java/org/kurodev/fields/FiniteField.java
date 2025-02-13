package org.kurodev.fields;

import org.kurodev.polynomials.MathsUtil;

import java.util.stream.IntStream;

public class FiniteField {
    private final int[] baseValues;
    private final int base;

    public FiniteField(int base, int[] baseValues) {
        this.baseValues = baseValues;
        this.base = base;
    }

    public static FiniteField ofBase(int base) {
        int[] baseValues = new int[base];
        IntStream.range(1, base).filter(value -> MathsUtil.bcd(base, value) == 1).forEachOrdered(i -> baseValues[i] = i);
        return new FiniteField(base, baseValues);
    }

    public Table computeAdditionTable() {
        int[][][] field = new int[baseValues.length][baseValues.length][];
        for (int y = 0; y < baseValues.length; y++) {
            for (int x = 0; x < base; x++) {
                field[y][x] = new int[]{(baseValues[x] + baseValues[y]) % base};
            }
        }
        return new Table("⊕", baseValues, field);
    }

    public Table computeMultiplicationTable() {
        int[][][] field = new int[baseValues.length][baseValues.length][];
        for (int y = 0; y < baseValues.length; y++) {
            for (int x = 0; x < base; x++) {
                field[y][x] = new int[]{(baseValues[x] + baseValues[y]) % base};
            }
        }
        return new Table("⊙", baseValues, field);
    }

}
