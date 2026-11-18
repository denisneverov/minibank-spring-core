package school.sorokin.java.backend.minibank.spring.core.account;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import school.sorokin.java.backend.minibank.spring.core.validator.AccountValidator;

@Component
public class AccountProperties {

    private final AccountValidator validator;

    @Autowired
    public AccountProperties(AccountValidator validator) {
        this.validator = validator;
    }

    @Value("${account.default-amount}")
    private double defaultAmount;

    @Value("${account.transfer-commission}")
    private double transferCommission;

    @PostConstruct
    public void validate() {
        validator.validateAmount(defaultAmount);
        validator.validateCommission(transferCommission);
    }

    public void setDefaultAmount(double defaultAmount) {
        this.defaultAmount = defaultAmount;
    }

    public void setTransferCommission(double transferCommission) {
        this.transferCommission = transferCommission;
    }

    public double getDefaultAmount() {
        return defaultAmount;
    }

    public double getTransferCommission() {
        return transferCommission;
    }

    @Override
    public String toString() {
        return "MiniBankProperties{" +
                ", defaultAmount=" + defaultAmount +
                ", transferCommission=" + transferCommission +
                '}';
    }
}
