package calculator.parsers;

import calculator.numbers.Number;
import calculator.numbers.NumberType;

public class ArabicParser extends Parser {

    @Override
    public boolean isValid(String strNumber) {
        basicValidation(strNumber);

        try {
            Integer.parseInt(strNumber);
            if (Integer.parseInt(strNumber) > 10 || Integer.parseInt(strNumber) <=0)
                throw new IllegalArgumentException("Число не должно выходить за пределы диапозона от 1 до 10!");
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public Number parse(String strNumber) throws NumberFormatException {
        basicValidation(strNumber);

        try {
            int number = Integer.parseInt(strNumber);
            return new Number(number, getType());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Не правильно введеное число!", e);
        }
    }

    @Override
    public String toString(Number number) throws IllegalArgumentException {
        return String.valueOf(number.getValue());
    }

    @Override
    public NumberType getType() {
        return NumberType.ARABIC;
    }

}
