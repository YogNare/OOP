package ru.nsu.baev;

import java.util.Map;

public class Sub implements Expression {

    private final Expression left;
    private final Expression right;

    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Sub derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public double normalEval(Map<String, Double> variables) {
        return left.normalEval(variables) - right.normalEval(variables);
    }

    @Override
    public Expression simplification() {
        Expression simplifiedLeft = left.simplification();
        Expression simplifiedRight = right.simplification();

        if (simplifiedLeft instanceof Number leftNum) {
            if (simplifiedRight instanceof Number rightNum) {
                return new Number(leftNum.getValue() - rightNum.getValue());
            }
            if (leftNum.getValue() == 0) {
                return new Number(-simplifiedRight.normalEval(Map.of())); // Negative of right expression
            }
        } else if (simplifiedRight instanceof Number rightNum && rightNum.getValue() == 0) {
            return simplifiedLeft;
        }

        return new Sub(simplifiedLeft, simplifiedRight);
    }

    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print("-");
        right.print();
        System.out.print(")");
    }
}