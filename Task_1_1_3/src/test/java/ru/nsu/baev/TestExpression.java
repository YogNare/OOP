package ru.nsu.baev;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static ru.nsu.baev.Expression.evaluate;

import org.junit.jupiter.api.Test;

public class TestExpression {

    @Test
    public void printTest() {
        Expression e = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x"))); // (3+(2*x))
        e.print();
    }

    @Test
    public void test1() {

        Expression e = evaluate("(3+(-2*x))");
        e.print();
    }
}
