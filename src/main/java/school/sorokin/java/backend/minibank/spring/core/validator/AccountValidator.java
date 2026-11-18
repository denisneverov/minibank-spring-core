package school.sorokin.java.backend.minibank.spring.core.validator;

import org.springframework.stereotype.Component;
import school.sorokin.java.backend.minibank.spring.core.BankException;

@Component
public class AccountValidator {

    public void validateAmount(double amount) {
        if (!Double.isFinite(amount) || amount < 0) {
            throw new BankException("Сумма не может быть меньше нуля. Передано: " + amount);
        }
    }

    public void validateCommission(double commission) {
        if (!Double.isFinite(commission) || commission < 0 || commission > 1) {
            throw new BankException("Комиссия должна быть от 0 до 1. Передано: " + commission);
        }
    }
}

