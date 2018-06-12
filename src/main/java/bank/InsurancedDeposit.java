package bank;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Period;

public class InsurancedDeposit extends Deposit {
    public InsurancedDeposit(Customer owner, Period period, Clock clock) {
        super(owner, period, clock);
    }

    @Override
    public void deposit(BigDecimal value, Clock clock) {
        super.deposit(value.multiply(new BigDecimal("0.9995")), clock);
    }
}
