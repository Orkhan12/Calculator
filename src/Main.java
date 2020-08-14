import calculator.Calculator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        String input;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            input = reader.readLine();
            input = input.replaceAll("\\s", "");
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка входных данных!", e);
        }

        Calculator calculator = new Calculator();
        String result = calculator.calculate(input);
        System.out.println(result);
    }

}
