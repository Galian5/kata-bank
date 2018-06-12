package bank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DepositFunds {
    private BigDecimal balance;
    private LocalDateTime startDateTime;

    public DepositFunds(BigDecimal balance, LocalDateTime startDateTime) {
        this.balance = balance;
        this.startDateTime = startDateTime;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
}

