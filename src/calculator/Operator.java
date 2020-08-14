package calculator;

import java.util.function.BinaryOperator;

public enum Operator {
    PLUS('+', Integer::sum),
    MINUS('-', (a, b) -> a - b),
    MULTIPLY('*', (a, b) -> a * b),
    DIVIDE('/', (a, b) -> a / b);

    private final char sign;
    private final BinaryOperator<Integer> operation;

    Operator(char sign, BinaryOperator<Integer> operation) {
        this.sign = sign;
        this.operation = operation;
    }

    public int performOperation(int a, int b)
    {
        return operation.apply(a,b);
    }

    public char getSign() {
        return sign;
    }

}
