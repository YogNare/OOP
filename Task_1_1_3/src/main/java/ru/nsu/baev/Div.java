package ru.nsu.baev;


import java.util.Map;

public class Div extends Expression {

    Expression left_expression;
    Expression right_expression;

    public Div(Expression left_expression, Expression right_expression) {
        this.left_expression = left_expression;
        this.right_expression = right_expression;
    }

    public Div derivative(String variable) {

        return new Div(
                new Sub (
                    new Mul(this.left_expression.derivative(variable), this.right_expression),
                    new Mul(this.left_expression, this.right_expression.derivative(variable))
                ),
                new Mul(this.right_expression, this.right_expression)
        );
    }

    public double normal_eval(Map<String, Double> variables) {
        return this.left_expression.normal_eval(variables) / this.right_expression.normal_eval(variables);
    }

    public void print() {
        System.out.print("(");
        this.left_expression.print();
        System.out.print("/");
        this.right_expression.print();
        System.out.print(")");
    }
}