package calculator;

import calculator.numbers.Number;
import calculator.parsers.ArabicParser;
import calculator.parsers.Parser;
import calculator.parsers.RomeParser;

import java.util.Arrays;
import java.util.List;

public class Calculator {

    private final List<Parser> parsers;

    public Calculator(Parser... parsers) {
        if (parsers == null || parsers.length == 0) {
            throw new IllegalArgumentException("Пустой парсер");
        }
        this.parsers = Arrays.asList(parsers);
    }

    public Calculator() {
        this.parsers = List.of(new ArabicParser(), new RomeParser());
    }

    public String calculate(String input) throws IllegalArgumentException {
        validateInput(input);

        Operator operator = searchOperator(input);
        int index = input.indexOf(operator.getSign());

        if (index == 0 || index == input.length() - 1) {
            throw new IllegalArgumentException("Не правильно введен оператор!");
        }

        String leftNumberStr = input.substring(0, index).trim();
        String rightNumberStr = input.substring(index + 1).trim();

        Number leftNumber = parseNumber(leftNumberStr);
        Number rightNumber = parseNumber(rightNumberStr);

        if (leftNumber.getType() != rightNumber.getType()) {
            throw new IllegalArgumentException("Разные типы данных! " + "Тип числа №1 = " + leftNumber.getType() + ". Тип числа №2 = " + rightNumber.getType());
        }
        int result = operator.performOperation(leftNumber.getValue(), rightNumber.getValue());

        return parseToString(new Number(result, leftNumber.getType()));
    }


    private void validateInput(String input) {
        if (input == null || input.isBlank() || input.length() < 3) {
            throw new IllegalArgumentException("Длина строки меньше 3х символов");
        }
    }

    public Operator searchOperator(String input) {
        Operator result = null;
        int operatorsCount = 0;

        for (char symbol : input.toCharArray()) {
            for (Operator operator : Operator.values()) {
                if (symbol == operator.getSign()) {
                    operatorsCount++;
                    result = operator;
                    break;
                }
            }
        }


        if (operatorsCount ==0) {
            throw  new IllegalArgumentException("Оператор в строке не найден");
        }
        if (operatorsCount > 1){
            throw  new IllegalArgumentException("В строке найден больше чем один оператор");
        }
        if (operatorsCount!=1){
            throw  new IllegalArgumentException("Оператор в строке должен быть один!");
        }


        return result;
    }

    public Number parseNumber(String strNumber) throws IllegalArgumentException {
        if (strNumber == null || strNumber.length() == 0 || strNumber.isBlank()) {
            throw new IllegalArgumentException("Нет нужного количество операндов");
        }

        for (Parser parser : parsers) {
            if (parser.isValid(strNumber)) {
                return parser.parse(strNumber);
            }
        }

        throw new IllegalArgumentException("Не найден подходящий парсер");
    }

    public String parseToString(Number number) {
        for (Parser parser : parsers) {
            if (parser.getType()==number.getType()) {
                return parser.toString(number);
            }
        }

        throw new IllegalArgumentException("Не найден подходящий парсер");
    }
}
