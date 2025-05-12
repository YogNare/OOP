package ru.nsu.baev;

import ru.nsu.baev.Expression;

import java.util.Map;

public class Number extends Expression {
    private final double number;

    public Number(double number) {
        this.number = number;
    }

    public Number derivative(String variable) {
        return new Number(0);
    }

//    public double evaluate(java.util.Map<String, Double> variables) {
//        return 0;
//    }

    public double normal_eval(Map<String, Double> variables) {
        return this.number;
    }

    // Метод для печати выражения
    public void print() {
        System.out.printf(this.number % 1 == 0 ? "%.0f" : "%s", this.number);
    }
}