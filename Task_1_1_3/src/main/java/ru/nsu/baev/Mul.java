package ru.nsu.baev;


import java.util.Map;

public class Mul extends Expression {

    Expression left_expression;
    Expression right_expression;

    public Mul(Expression left_expression, Expression right_expression) {
        this.left_expression = left_expression;
        this.right_expression = right_expression;
    }

    public Add derivative(String variable) {

        return new Add(
                new Mul(this.left_expression.derivative(variable), this.right_expression),
                new Mul(this.left_expression, this.right_expression.derivative(variable))
        );
    }

    public double normal_eval(Map<String, Double> variables) {
        return this.left_expression.normal_eval(variables) * this.right_expression.normal_eval(variables);
    }

    public void print() {
        System.out.print("(");
        this.left_expression.print();
        System.out.print("*");
        this.right_expression.print();
        System.out.print(")");
    }
}