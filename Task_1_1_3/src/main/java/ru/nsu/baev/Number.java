package ru.nsu.baev;

import java.util.Map;

public class Number extends Expression {
    private final double number;

    public Number(double number) {
        this.number = number;
    }

    @Override
    public Number derivative(String variable) {
        return new Number(0);
    }

    @Override
    public double normal_eval(Map<String, Double> variables) {
        return this.number;
    }

    @Override
    public void print() {
        System.out.printf(this.number % 1 == 0 ? "%.0f" : "%s", this.number);
    }
}