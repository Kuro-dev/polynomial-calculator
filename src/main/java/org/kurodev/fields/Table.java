package org.kurodev.fields;

import org.kurodev.polynomials.Polynomial;

import java.util.Arrays;
import java.util.Objects;

public class Table {
    private final int[][][] table;
    private final int[] baseValues;
    private final String symbol;

    public Table(String symbol, int[] baseValues, int[][][] table) {
        this.symbol = symbol;
        this.table = table;
        this.baseValues = baseValues;
    }

    public Table(int[] baseValues, int[][][] table) {
        this(" ", baseValues, table);
    }

    //TODO change equals, such that it checks if 2 tables are not the same, but rather isomorphic
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table1 = (Table) o;
        return Objects.deepEquals(table, table1.table);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(table);
    }

    @Override
    public String toString() {
        String lineV = "┃";
        String lineH = "━";
        String lineCross = "╋";
        StringBuilder builder = new StringBuilder();

        // Add the top left corner symbol
        builder.append(symbol).append(lineV).append("\t");

        // Add the base values as headers for the columns
        for (int baseValue : baseValues) {
            builder.append(baseValue).append("\t");
        }
        builder.append("\n");
        builder.append(lineH).append(lineCross);
        // Add a separator line between headers and the table values
        builder.append(lineH.repeat((baseValues.length * 4 + 4) - 3));
        builder.append("\n");

        // Iterate through the table and print the rows
        for (int i = 0; i < table.length; i++) {
            // Add the base value for the current row
            builder.append(baseValues[i]).append(lineV).append("\t");

            // Add the polynomial values for this row
            for (int x = 0; x < table[i].length; x++) {
                Polynomial p = new Polynomial(table[i][x]);
                builder.append(p).append("\t"); // Use tab for better spacing
            }
            builder.append("\n");
        }

        return builder.toString();
    }


}
