package ru.nsu.baev;

import java.util.Map;

public class Add implements Expression {

    private final Expression left;
    private final Expression right;

    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Add derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public double normalEval(Map<String, Double> variables) {
        return left.normalEval(variables) + right.normalEval(variables);
    }

    @Override
    public Expression simplification() {
        Expression simplifiedLeft = left.simplification();
        Expression simplifiedRight = right.simplification();

        if (simplifiedLeft instanceof Number leftNum) {
            if (simplifiedRight instanceof Number rightNum) {
                return new Number(leftNum.getValue() + rightNum.getValue());
            }
            if (leftNum.getValue() == 0) {
                return simplifiedRight;
            }
        } else if (simplifiedRight instanceof Number rightNum && rightNum.getValue() == 0) {
            return simplifiedLeft;
        }

        return new Add(simplifiedLeft, simplifiedRight);
    }

    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print("+");
        right.print();
        System.out.print(")");
    }
}