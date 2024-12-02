package ru.nsu.baev;

import java.util.Map;

public class Variable extends Expression {
    private final String variable;

    public Variable(String variable) {
        this.variable = variable;
    }

    @Override
    public Number derivative(String variable) {
        return this.variable.equals(variable) ? new Number(1) : new Number(0);
    }

    @Override
    public double normal_eval(Map<String, Double> variables) {
        return variables.getOrDefault(this.variable, 0.0);
    }

    @Override
    public void print() {
        System.out.print(this.variable);
    }
}