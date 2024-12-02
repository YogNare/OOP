package ru.nsu.baev;

import java.util.Map;

public class Add extends Expression {

    private final Expression leftExpression;
    private final Expression rightExpression;

    public Add(Expression leftExpression, Expression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    @Override
    public Add derivative(String variable) {
        return new Add(leftExpression.derivative(variable), rightExpression.derivative(variable));
    }

    @Override
    public double normal_eval(Map<String, Double> variables) {
        return leftExpression.normal_eval(variables) + rightExpression.normal_eval(variables);
    }

    @Override
    public void print() {
        System.out.print("(");
        leftExpression.print();
        System.out.print("+");
        rightExpression.print();
        System.out.print(")");
    }
}