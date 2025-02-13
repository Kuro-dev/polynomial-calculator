package org.kurodev.polynomials;

public record DivisionResult(Polynomial result, Polynomial rest) {

    public DivisionResult(int result, int rest) {
        this(new Polynomial(new int[]{result}), new Polynomial(new int[]{rest}));
    }

    @Override
    public String toString() {
        return result.toString() + " rest: " + rest.toString();
    }

    public DivisionResult modulo(int n) {
        return new DivisionResult(result.modulo(n), rest.modulo(n));
    }
}
