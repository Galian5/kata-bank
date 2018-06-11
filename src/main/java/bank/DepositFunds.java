package bank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DepositFunds {
    private BigDecimal balance;
    private BigDecimal interestRate;
    private LocalDateTime startDateTime;

    public DepositFunds(BigDecimal balance, BigDecimal interestRate, LocalDateTime startDateTime) {
        this.balance = balance;
        this.startDateTime = startDateTime;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
}

