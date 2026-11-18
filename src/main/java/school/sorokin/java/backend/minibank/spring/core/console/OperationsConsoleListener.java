package school.sorokin.java.backend.minibank.spring.core.console;

import org.springframework.stereotype.Component;
import school.sorokin.java.backend.minibank.spring.core.BankException;
import school.sorokin.java.backend.minibank.spring.core.BankService;
import school.sorokin.java.backend.minibank.spring.core.operations.Commands;

import java.util.Arrays;
import java.util.Optional;

@Component
public class OperationsConsoleListener {
    private final ConsoleInput consoleInput;
    private final BankService bankService;

    public OperationsConsoleListener(ConsoleInput consoleInput, BankService bankService) {
        this.consoleInput = consoleInput;
        this.bankService = bankService;
    }

    public void listen() {
        System.out.println("МиниБанк запущен. Доступные команды: " + Arrays.toString(Commands.values()));
        System.out.println();

        while (true) {
            Optional<String> commandOpt = consoleInput.readLine("Введите команду: ");

            if (commandOpt.isEmpty()) {
                System.out.println("\nЗавершение работы (EOF).");
                break;
            }

            String rawCommand = commandOpt.get().toUpperCase();
            if (rawCommand.isEmpty()) {
                continue;
            }

            Commands command;
            try {
                command = Commands.valueOf(rawCommand);
            } catch (IllegalArgumentException e) {
                System.out.println("Неизвестная команда: " + rawCommand);
                continue;
            }

            if (command == Commands.EXIT) {
                System.out.println("Работа программы завершена.");
                break;
            }

            try {
                switch (command) {
                    case USER_CREATE -> bankService.handleUserCreate();
                    case SHOW_ALL_USERS -> bankService.handleShowAllUsers();
                    case ACCOUNT_CREATE -> bankService.handleAccountCreate();
                    case ACCOUNT_DEPOSIT -> bankService.handleAccountDeposit();
                    case ACCOUNT_WITHDRAW -> bankService.handleAccountWithdraw();
                    case ACCOUNT_TRANSFER -> bankService.handleAccountTransfer();
                    case ACCOUNT_CLOSE -> bankService.handleAccountClose();
                }
            } catch (BankException e) {
                System.out.println("Ошибка выполнения операции: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Непредвиденная ошибка: " + e.getMessage());
            }
        }
    }
}
