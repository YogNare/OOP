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
    public double normal_eval(Map<String, Double> variables) {
        return leftExpression.normal_eval(variables) / rightExpression.normal_eval(variables);
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