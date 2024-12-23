package ru.nsu.baev;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static ru.nsu.baev.Expression.evaluate;

import org.junit.jupiter.api.Test;

public class TestExpression {

    @Test
    public void printTest1() {
        Expression e = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x"))); // (3+(2*x))

        OutputCaptor captor = new OutputCaptor();

        captor.start();

        e.print();

        assertEquals("(3+(2*x))", captor.getCapturedOutput());

        captor.stop();
    }

    @Test
    public void printTest2() {
        Expression e =
                new Add(
                        new Div(
                                new Mul(
                                        new Variable("a"),
                                        new Variable("b")
                                ),
                                new Variable("c")
                        ),
                        new Sub(
                                new Mul(
                                        new Variable("d"),
                                        new Variable("e")
                                ),
                                new Div(
                                        new Variable("f"),
                                        new Variable("g"))
                        )
                ); //(((a * b) / c) + ((d * e) - (f / g)))

        OutputCaptor captor = new OutputCaptor();

        captor.start();

        e.print();

        assertEquals("(((a*b)/c)+((d*e)-(f/g)))", captor.getCapturedOutput());

        captor.stop();
    }

    @Test
    public void test1() {
        Expression e = ExpressionUtil.evaluate("3+2*x");
        e.print();
    }
}
