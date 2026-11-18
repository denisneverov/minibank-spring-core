package school.sorokin.java.backend.minibank.spring.core;

import org.springframework.stereotype.Service;
import school.sorokin.java.backend.minibank.spring.core.account.Account;
import school.sorokin.java.backend.minibank.spring.core.account.AccountService;
import school.sorokin.java.backend.minibank.spring.core.console.ConsoleInput;
import school.sorokin.java.backend.minibank.spring.core.user.User;
import school.sorokin.java.backend.minibank.spring.core.user.UserService;

import java.util.List;

@Service
public class BankService {
    private final UserService userService;
    private final AccountService accountService;
    private final ConsoleInput consoleInput;

    public BankService(UserService userService, AccountService accountService, ConsoleInput consoleInput) {
        this.userService = userService;
        this.accountService = accountService;
        this.consoleInput = consoleInput;
    }

    public void handleUserCreate() {
        String login = consoleInput.readLine("Введите логин пользователя: ")
                .orElseThrow(() -> new BankException("Ввод прерван"));

        User user = userService.create(login);
        Account account = accountService.create(user.getId());

        user = userService.addAccount(user.getId(), account);

        System.out.println("Пользователь создан: " + user);
    }

    public void handleShowAllUsers() {
        System.out.println("Список всех пользователей:");
        userService.getAllUsers().forEach(System.out::println);
    }

    public void handleAccountCreate() {
        int userId = consoleInput.readInt("Введите ID пользователя: ")
                .orElseThrow(() -> new BankException("Ввод прерван"));

        User user = userService.search(userId);
        Account account = accountService.create(user.getId());
        userService.addAccount(user.getId(), account);

        System.out.println("Создан новый счет: " + account);
    }

    public void handleAccountDeposit() {
        int accountId = consoleInput.readInt("Введите ID счета: ")
                .orElseThrow(() -> new BankException("Ввод прерван"));
        int amount = consoleInput.readInt("Введите сумму пополнения: ")
                .orElseThrow(() -> new BankException("Ввод прерван"));

        accountService.deposit(accountId, amount);
        System.out.println("Счет успешно пополнен.");
    }

    public void handleAccountWithdraw() {
        int accountId = consoleInput.readInt("Введите ID счета: ")
                .orElseThrow(() -> new BankException("Ввод прерван"));
        int amount = consoleInput.readInt("Введите сумму снятия: ")
                .orElseThrow(() -> new BankException("Ввод прерван"));

        accountService.withdraw(accountId, amount);
        System.out.println("Средства успешно сняты.");
    }

    public void handleAccountTransfer() {
        int fromAccountId = consoleInput.readInt("Введите ID счета отправителя: ")
                .orElseThrow(() -> new BankException("Ввод прерван"));
        int toAccountId = consoleInput.readInt("Введите ID счета получателя: ")
                .orElseThrow(() -> new BankException("Ввод прерван"));
        int amount = consoleInput.readInt("Введите сумму перевода: ")
                .orElseThrow(() -> new BankException("Ввод прерван"));

        accountService.transfer(fromAccountId, toAccountId, amount);
        System.out.println("Перевод выполнен успешно.");
    }

    public void handleAccountClose() {
        int accountId = consoleInput.readInt("Введите ID счета для закрытия: ")
                .orElseThrow(() -> new BankException("Ввод прерван"));

        Account account = accountService.close(accountId);

        int userId = account.getUserId();

        List<Account> accounts = userService.getAccounts(userId);
        accounts.remove(account);

        userService.updateAccounts(userId, accounts);

        System.out.println("Счет закрыт.");
    }
}
