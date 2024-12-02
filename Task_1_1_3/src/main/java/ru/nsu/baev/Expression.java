package ru.nsu.baev;

import java.util.Map;

public abstract class Expression {

    public abstract Expression derivative(String variable);

    public abstract void print();

    public abstract double normal_eval(Map<String, Double> variables);

    private static Expression take_expression(String expr, int priority) {
        char symbol;
        int num = 0;
        Expression left = null;
        int negative = 1;

        for (int i = 0; i < expr.length(); i++) {
            symbol = expr.charAt(i);

            if (symbol == '-' && i + 1 < expr.length() && Character.isDigit(expr.charAt(i + 1))) {
                negative = -1;
                continue;
            }

            if (Character.isDigit(symbol)) {
                num = num * 10 + (symbol - '0');
            } else {
                if (left == null) {
                    num *= negative;
                    left = new Number(num);
                    num = 0;
                }

                if (symbol == '+') {
                    return new Add(left, take_expression(expr.substring(i + 1), priority));
                }
            }
        }

        if (left == null) {
            num *= negative;
            left = new Number(num);
        }

        return left;
    }
}