package school.sorokin.java.backend.minibank.spring.core.console;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

@Component
public class ConsoleInput {
    private final Scanner scanner;

    public ConsoleInput() {
        this.scanner = new Scanner(System.in);
    }

    public Optional<String> readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            return Optional.empty();
        }

        return Optional.of(scanner.nextLine().trim());
    }

    public Optional<Integer> readInt(String prompt) {
        while (true) {
            Optional<String> input = readLine(prompt);
            if (input.isEmpty()) {
                return Optional.empty();
            }
            try {
                return Optional.of(Integer.parseInt(input.get()));
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное целое число.");
            }
        }
    }
}
