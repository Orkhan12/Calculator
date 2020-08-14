package calculator.parsers;

import calculator.numbers.Number;
import calculator.numbers.NumberType;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class RomeParser extends Parser {

    private enum RomanNumeral {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100);

        private int value;

        RomanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static List<RomanNumeral> getReverseSortedValues() {
            return Arrays.stream(values())
                         .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                         .collect(Collectors.toList());
        }
    }

    public RomeParser() { }

    @Override
    public boolean isValid(String strNumber) {
        basicValidation(strNumber);
        try {
            Number number = parse(strNumber);
            if (number.getValue() > 10 || number.getValue() < 0) {
                throw new IllegalArgumentException("Число не должно выходить за пределы диапозона от I до X!");
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Из римского в арабский
    @Override
    public Number parse(String strNumber) throws IllegalArgumentException {
        basicValidation(strNumber);

        String romanNumeral = strNumber.toUpperCase();
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(strNumber + " Не является числом!");
        }

        return new Number(result, getType());
    }

    //Из арабского в римский
    @Override
    public String toString(Number number) throws IllegalArgumentException {

        if ((number.getValue() <= 0) || (number.getValue()  > 100)) {
            throw new IllegalArgumentException(number + " вне диапазона (0, 100]");
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        int j = number.getValue();
        StringBuilder sb = new StringBuilder();

        while ((j  > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= j) {
                sb.append(currentSymbol.name());
                j -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }

    @Override
    public NumberType getType() {
        return NumberType.ROMAN;
    }

}