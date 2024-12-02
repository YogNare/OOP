package ru.nsu.baev;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class Expression {

    public abstract Expression derivative(String variable);

    public abstract void print();

    public double eval(String variables) {
        Map<String, Double> map = new HashMap<>();

        String[] pairs = variables.split(";");
        for (String pair : pairs) {
            String[] keyValue = pair.trim().split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                Double value = Double.parseDouble(keyValue[1].trim());
                map.put(key, value);
            }
        }

        return normal_eval(map);
    }

    public abstract double normal_eval(Map<String, Double> variables);

    private static Expression take_expression(String expr, int priority) {
        char symbol;
        int num = 0;
        Integer number;
        Expression left;
        int negative = 1;
        for (int i = 0; i < expr.length(); i ++) {
            symbol = expr.charAt(i);

            if (symbol == '-' && expr.charAt(i + 1) > 47 && expr.charAt(i + 1) < 58) {
                negative = -1;
            }
            else if (symbol > 47 && symbol < 58) {
                num *= 10;
                num += symbol - 48;
            }
            else {
                num *= negative;
                left = new Number(num);

                if (symbol == '+') {
                    return new Add(left, take_expression(expr, 0));
                }
            }
        }

        return new Number(0);
    }

    public static Expression take_from_input() {
        Scanner scanner = new Scanner(System.in);

        String expr = scanner.nextLine();

        char symbol;
        int num = 0;
        Expression left;
        for (int i = 0; i < expr.length(); i ++) {
            symbol = expr.charAt(i);

            if (symbol > 47 && symbol < 58) {
                num *= 10;
                num += symbol - 48;
            }
            else {
                left = new Number(num);
            }
        }

        return new Number(0);
    }
}