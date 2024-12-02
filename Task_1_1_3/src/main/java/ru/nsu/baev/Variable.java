package ru.nsu.baev;

import java.util.Map;

public class Variable extends Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public Number derivative(String variable) {
        return name.equals(variable) ? new Number(1) : new Number(0);
    }

    @Override
    public double normalEval(Map<String, Double> variables) {
        return variables.getOrDefault(name, 0.0);
    }

    @Override
    public Variable simplification() {
        return new Variable(name); // Переменная уже упрощена
    }

    @Override
    public void print() {
        System.out.print(name);
    }
}