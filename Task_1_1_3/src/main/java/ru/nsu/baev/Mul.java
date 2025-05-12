package ru.nsu.baev;

import java.util.Map;

public class Mul extends Expression {

    private final Expression left;
    private final Expression right;

    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Add derivative(String variable) {
        return new Add(
                new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable))
        );
    }

    @Override
    public double normalEval(Map<String, Double> variables) {
        return left.normalEval(variables) * right.normalEval(variables);
    }

    @Override
    public Expression simplification() {
        Expression simplifiedLeft = left.simplification();
        Expression simplifiedRight = right.simplification();

        if (simplifiedLeft instanceof Number leftNum) {
            double leftValue = leftNum.getValue();
            if (simplifiedRight instanceof Number rightNum) {
                return new Number(leftValue * rightNum.getValue());
            }
            if (leftValue == 0) {
                return new Number(0);
            }
            if (leftValue == 1) {
                return simplifiedRight;
            }
        }

        if (simplifiedRight instanceof Number rightNum) {
            double rightValue = rightNum.getValue();
            if (rightValue == 0) {
                return new Number(0);
            }
            if (rightValue == 1) {
                return simplifiedLeft;
            }
        }

        return new Mul(simplifiedLeft, simplifiedRight);
    }

    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print("*");
        right.print();
        System.out.print(")");
    }
}