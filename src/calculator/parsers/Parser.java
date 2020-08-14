package calculator.parsers;

import calculator.numbers.Number;
import calculator.numbers.NumberType;

public abstract class Parser {
    abstract public boolean isValid(String strNumber);
    abstract public Number parse(String strNumber) throws IllegalArgumentException;
    abstract public String toString(Number number) throws IllegalArgumentException;
    abstract public NumberType getType();

    protected void basicValidation(String strNumber) throws IllegalArgumentException {
      if (strNumber == null || strNumber.length() == 0 || strNumber.isBlank()) {
          throw new IllegalArgumentException("Нет нужного количество операндов");
      }
    }

}