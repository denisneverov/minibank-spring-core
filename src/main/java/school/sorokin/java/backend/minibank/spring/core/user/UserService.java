package school.sorokin.java.backend.minibank.spring.core.user;

import org.springframework.stereotype.Service;
import school.sorokin.java.backend.minibank.spring.core.BankException;
import school.sorokin.java.backend.minibank.spring.core.account.Account;

import java.util.*;

@Service
public class UserService {

    private int idCounter = 0;

    private final Map<String, User> users = new HashMap<>();

    public User create(String login) {
        return create(login, new ArrayList<>());
    }

    public User create(String login, List<Account> accountList) {

        User currentUser = users.get(login);
        if (currentUser != null) {
            throw new BankException("Такой пользователь уже есть: " + currentUser);
        }

        idCounter++;
        User newUser = new User(idCounter, login, accountList);
        users.put(login, newUser);

        return newUser;
    }

    public User search(int id) {
        return users.values().stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(() -> new BankException("Пользователь не найден, id: " + id));
    }

    public List<Account> getAccounts(int userId) {
        User user = search(userId);

        return user.getAccountList();
    }

    public User addAccount(int userId, Account account) {
        User user = search(userId);

        user.addAccount(account);

        return user;
    }

    public User updateAccounts(int userId, List<Account> accountList) {
        User user = search(userId);

        user.setAccountList(accountList);

        return user;
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }
}
