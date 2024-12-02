package ru.nsu.baev;

import java.awt.*;
import java.util.Map;

public class Variable extends Expression {
    String variable;

    public Variable(String variable) {
        this.variable = variable;
    }

    public Number derivative(String variable) {
        if (variable.equals(this.variable)) {
            return new Number(1);
        }
        else {
            return new Number(0);
        }
    }

    public double normal_eval(Map<String, Double> variables) {
        if (variables.get("x") != null) {
            return variables.get("x");
        }
        else {
            return 0;
        }
    }

    public void print() {
        System.out.print(this.variable);
    }
}