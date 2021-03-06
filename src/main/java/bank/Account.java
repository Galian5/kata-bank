package bank;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.Objects;
import java.util.UUID;

public class Account {
    private final UUID uuid;
    private final Customer owner;

    private BigDecimal balance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(uuid, account.uuid) &&
                Objects.equals(owner, account.owner) &&
                Objects.equals(balance, account.balance);
    }

    public Customer getOwner() {
        return owner;
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, owner, balance);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Account(Customer owner) {

        this.uuid = UUID.randomUUID();
        this.owner = owner;
        this.balance = new BigDecimal(0);

    }

    public void deposit(BigDecimal value, Clock clock){
        this.setBalance(this.getBalance().add(value));

    }

    public void withdraw(BigDecimal value){
        this.setBalance(this.getBalance().subtract(value));

    }

    public <T extends Account> void transferTo(T target, BigDecimal value, Clock clock){
        this.withdraw(value);
        target.deposit(value, clock);

    }
}
