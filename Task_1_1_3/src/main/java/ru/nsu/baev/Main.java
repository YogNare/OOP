package ru.nsu.baev;

import com.sun.source.tree.ExpressionTree;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        Expression e = new Add(
//                new Number(3),
//                new Mul(
//                        new Number(2),
//                        new Variable("x"))
//        ); // (3+(2*x))

        Expression e = Expression.take_from_input();

        e.print();
    }
}