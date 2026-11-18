package school.sorokin.java.backend.minibank.spring.core.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sorokin.java.backend.minibank.spring.core.BankException;
import school.sorokin.java.backend.minibank.spring.core.validator.AccountValidator;

import java.util.*;

@Service
public class AccountService {
    private final AccountValidator accountValidator;
    private final AccountProperties accountProperties;
    private final Map<Integer, List<Account>> accounts = new HashMap<>();

    private int idCounter = 0;

    @Autowired
    public AccountService(AccountProperties accountProperties, AccountValidator accountValidator) {
        this.accountProperties = accountProperties;
        this.accountValidator = accountValidator;
    }

    public Account create(int userId) {
        return create(userId, accountProperties.getDefaultAmount());
    }

    public Account create(int userId, double amount) {

        accountValidator.validateAmount(amount);
        idCounter++;

        Account newAccount = new Account(idCounter, userId, amount);
        accounts.computeIfAbsent(userId, k -> new ArrayList<>()).add(newAccount);

        return newAccount;
    }

    public void deposit(int accountId, int amount) {
        accountValidator.validateAmount(amount);

        Account account = search(accountId);

        double currentAmount = account.getMoneyAmount();
        double newAmount = currentAmount + amount;

        account.setMoneyAmount(newAmount);
    }

    private Account search(int accountId) {
        return accounts.values().stream()
                .flatMap(Collection::stream)
                .filter(account -> account.getId() == accountId)
                .findFirst()
                .orElseThrow(() -> new BankException("Счет не найден, id: " + accountId));
    }

    public void withdraw(int accountId, int amount) {
        accountValidator.validateAmount(amount);

        Account account = search(accountId);

        double currentAmount = account.getMoneyAmount();

        double newAmount = currentAmount - amount;

        if (newAmount < 0) {
            throw new BankException("Сумма снятия (" + amount + ") превышет текущий баланс: " + currentAmount);
        }

        account.setMoneyAmount(newAmount);
    }

    public void transfer(int fromAccountId, int toAccountId, int amount) {
        Account fromAccount = search(fromAccountId);
        Account toAccount = search(toAccountId);

        double fromCurrentBalance = fromAccount.getMoneyAmount();
        double toCurrentBalance = toAccount.getMoneyAmount();

        int fromUserId = fromAccount.getUserId();
        int toUserId = toAccount.getUserId();

        double comission = accountProperties.getTransferCommission();
        if (fromUserId == toUserId) {
            comission = 0;
        }

        double newFromBalance = fromCurrentBalance - amount;
        if (newFromBalance < 0) {
            throw new BankException("Сумма перевода (" + amount + ") превышет текущий баланс: " + fromCurrentBalance);
        }

        double newToBalance = toCurrentBalance + (amount * (1 - comission));

        fromAccount.setMoneyAmount(newFromBalance);
        toAccount.setMoneyAmount(newToBalance);
    }

    public Account close(int accountId) {
        Account account = search(accountId);

        int userId = account.getUserId();

        List<Account> allUserAccounts = accounts.get(userId);
        allUserAccounts.remove(account);

        return account;
    }
}
