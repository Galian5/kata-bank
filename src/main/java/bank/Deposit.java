package bank;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Period;

public class Deposit extends Account {
    private BigDecimal interestRate;
    private Account sourceAccount;
    private Period period;
    private LocalDateTime startDateTime;

    public Deposit(Customer owner, Period period, Clock clock) {
        super(owner);
        this.interestRate = new BigDecimal(0);
        this.period = period;
        this.startDateTime = LocalDateTime.now(clock);
    }

    public void setInterestRate(BigDecimal interest_rate) {
        this.interestRate = interest_rate;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public void terminate(Clock clock) throws RuntimeException {
        if(LocalDateTime.now(clock).isAfter(startDateTime.plus(period))){
            BigDecimal multiplier = new BigDecimal(period.toTotalMonths()).divide(BigDecimal.valueOf(12));
            BigDecimal finalBalance = getBalance().multiply(interestRate.multiply(multiplier).add(BigDecimal.ONE));
            sourceAccount.deposit(finalBalance);
            withdraw(getBalance());
        }
        else {
            throw new RuntimeException();
        }
    }
}
