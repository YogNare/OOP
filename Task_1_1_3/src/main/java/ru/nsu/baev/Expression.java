package ru.nsu.baev;

import java.util.*;

interface Expression {

    Expression derivative(String variable);

    void print();

    double normalEval(Map<String, Double> variables);

    Expression simplification();

}