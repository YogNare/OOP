package ru.nsu.baev;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExpressionUtil {

    private static List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder number = new StringBuilder();

        for (char ch : expression.toCharArray()) {
            if (Character.isDigit(ch) || (ch == '-' && number.isEmpty())) {
                number.append(ch);
            } else {
                if (!number.isEmpty()) {
                    tokens.add(number.toString());
                    number.setLength(0);
                }
                if (!Character.isWhitespace(ch)) {
                    tokens.add(Character.toString(ch));
                }
            }
        }
        if (!number.isEmpty()) {
            tokens.add(number.toString());
        }
        return tokens;
    }

    private static int priority(String operator) {
        return switch (operator) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> 0;
        };
    }

    private static void applyOperator(Stack<Expression> expressions, String operator) {

        Expression right = expressions.pop();
        Expression left = expressions.pop();
        Expression result = switch (operator) {
            case "+" -> new Add(left, right);
            case "-" -> new Sub(left, right);
            case "*" -> new Mul(left, right);
            case "/" -> new Div(left, right);
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
        expressions.push(result);
    }

    public static Expression evaluate(String expression) {
        List<String> tokens = tokenize(expression);
        Stack<Expression> expressions = new Stack<>();
        Stack<String> operators = new Stack<>();

        for (String token : tokens) {
            if (token.matches("-?\\d+")) {
                expressions.push(new Number(Integer.parseInt(token)));
            } else if (token.matches("[a-zA-Z]+")) {
                expressions.push(new Variable(token));
            } else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    applyOperator(expressions, operators.pop());
                }
                if (!operators.isEmpty() && operators.peek().equals("(")) {
                    operators.pop();
                }
            } else {
                while (!operators.isEmpty() && priority(operators.peek()) >= priority(token)) {
                    applyOperator(expressions, operators.pop());
                }
                operators.push(token);
            }
        }

        while (!operators.isEmpty()) {
            applyOperator(expressions, operators.pop());
        }

        if (expressions.size() != 1) {
            throw new IllegalArgumentException("Invalid expression");
        }

        return expressions.pop();
    }
}
