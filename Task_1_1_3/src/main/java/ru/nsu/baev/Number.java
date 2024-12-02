package ru.nsu.baev;

import java.util.Map;

public class Number extends Expression {
    private final double value;

    public Number(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public Number derivative(String variable) {
        return new Number(0);
    }

    @Override
    public double normalEval(Map<String, Double> variables) {
        return value;
    }

    @Override
    public Number simplification() {
        return new Number(value); // Число уже в упрощённой форме
    }

    @Override
    public void print() {
        System.out.printf(value % 1 == 0 ? "%.0f" : "%s", value);
    }

    public Number negative() {
        return new Number(-value);
    }
}