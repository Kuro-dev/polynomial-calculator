package org.kurodev.ui.components.userinput;

import org.kurodev.fields.FiniteField;
import org.kurodev.polynomials.Polynomial;

public record CalculationRequest(int base, int exponent, Polynomial irreducible) {

    public FiniteField getField() {
        return FiniteField.ofBase(base, irreducible);
    }
}
