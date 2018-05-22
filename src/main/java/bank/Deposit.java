package bank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;

public class Deposit extends Account {
    private BigDecimal interestRate;
    private Period period;
    private LocalDateTime startDateTime;

    public Deposit(Customer owner, Period period) {
        super(owner);
        this.interestRate = new BigDecimal(0);
        this.period = period;
        this.startDateTime = LocalDateTime.now();
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interest_rate) {
        this.interestRate = interest_rate;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
