package ru.nsu.baev;

import java.util.Map;

public class Div extends Expression {

    private final Expression leftExpression;
    private final Expression rightExpression;

    public Div(Expression leftExpression, Expression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    @Override
    public Div derivative(String variable) {
        return new Div(
                new Sub(
                        new Mul(leftExpression.derivative(variable), rightExpression),
                        new Mul(leftExpression, rightExpression.derivative(variable))
                ),
                new Mul(rightExpression, rightExpression)
        );
    }

    @Override
    public double normalEval(Map<String, Double> variables) {
        return leftExpression.normalEval(variables) / rightExpression.normalEval(variables);
    }

    @Override
    public Expression simplification() {
        Expression simplifiedLeft = leftExpression.simplification();
        Expression simplifiedRight = rightExpression.simplification();

        if (simplifiedLeft instanceof Number leftNum) {
            double leftValue = leftNum.getValue();
            if (simplifiedRight instanceof Number rightNum) {
                double rightValue = rightNum.getValue();
                return new Number(leftValue / rightValue);
            }
            if (leftValue == 0) {
                return new Number(0);
            }
        }

        if (simplifiedRight instanceof Number rightNum) {
            double rightValue = rightNum.getValue();
            if (rightValue == 1) {
                return simplifiedLeft;
            }
            if (rightValue == 0) {
                throw new ArithmeticException("Division by zero");
            }
        }

        return new Div(simplifiedLeft, simplifiedRight);
    }

    @Override
    public void print() {
        System.out.print("(");
        leftExpression.print();
        System.out.print("/");
        rightExpression.print();
        System.out.print(")");
    }
}