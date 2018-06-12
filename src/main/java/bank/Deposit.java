package bank;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Deposit extends Account {
    private BigDecimal interestRate;
    private Account sourceAccount;
    private Period period;
    private LocalDateTime startDateTime;

    private List<DepositFunds> balances;

    public List<DepositFunds> getBalances() {
        return balances;
    }

    public Deposit(Customer owner, Period period, Clock clock) {
        super(owner);
        this.interestRate = new BigDecimal(0);
        this.period = period;
        this.startDateTime = LocalDateTime.now(clock);
        this.balances = new ArrayList<>();
    }

    public void setInterestRate(BigDecimal interest_rate) {
        this.interestRate = interest_rate;
    }

    public BigDecimal getInterestRate(DepositFunds depositFunds) {
        if(!depositFunds.getStartDateTime().toLocalDate().equals(startDateTime.toLocalDate())){
            return interestRate.add(new BigDecimal("0.05"));
        }
        return interestRate;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public void terminate(Clock clock) throws RuntimeException {
        if(LocalDateTime.now(clock).isAfter(startDateTime.plus(period))){
            for (DepositFunds depositFunds : balances) {

                System.out.println(depositFunds.getBalance());
                System.out.println(depositFunds.getStartDateTime());
                BigDecimal interest = getInterestRate(depositFunds);

                Period p = Period.between(depositFunds.getStartDateTime().toLocalDate(), startDateTime.plus(period).toLocalDate());
                BigDecimal multiplier = new BigDecimal(p.toTotalMonths()).divide(BigDecimal.valueOf(12));
                BigDecimal finalBalance = depositFunds.getBalance().multiply(interest.multiply(multiplier).add(BigDecimal.ONE));
                sourceAccount.deposit(finalBalance, clock);
            }
            balances.clear();
        }
        else {
            throw new RuntimeException();
        }
    }

    @Override
    public void deposit(BigDecimal value, Clock clock) {
        balances.add(new DepositFunds(value, LocalDateTime.now(clock)));
    }

    @Override
    public BigDecimal getBalance() {
        return this.balances.stream().map(DepositFunds::getBalance).reduce(new BigDecimal("0.0"), (a, b) -> a.add(b));
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }
}
