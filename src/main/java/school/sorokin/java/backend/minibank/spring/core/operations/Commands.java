package school.sorokin.java.backend.minibank.spring.core.operations;

public enum Commands {
    USER_CREATE("создание нового пользователя"),
    SHOW_ALL_USERS("отображение списка всех пользователей"),
    ACCOUNT_CREATE("создание нового счёта для пользователя"),
    ACCOUNT_DEPOSIT("пополнение счёта"),
    ACCOUNT_WITHDRAW("снятие средств со счёта"),
    ACCOUNT_TRANSFER("перевод средств между счетами"),
    ACCOUNT_CLOSE("закрытие счёта"),
    EXIT("завершение работы программы");

    private final String description;

    Commands(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
