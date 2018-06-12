package bank;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Period;
import java.util.*;

public class AccountMapping {
    private HashMap<Customer, Set<Account>> mapping = new HashMap<>();
    private HashMap<Customer, Set<Deposit>> depositMapping = new HashMap<>();

    public Set<Account> getAccounts(Customer customer) {
        return mapping.getOrDefault(customer, new HashSet<>(Collections.emptySet()));
    }

    public Set<Deposit> getDeposits(Customer customer) {
        return depositMapping.getOrDefault(customer, new HashSet<>(Collections.emptySet()));
    }

    public void addAccount(Account account) {
        Set<Account> accounts = getAccounts(account.getOwner());
        accounts.add(account);
        mapping.put(account.getOwner(), accounts);
    }

    public void addDeposit(Deposit deposit) {
        Set<Deposit> deposits = getDeposits(deposit.getOwner());
        deposits.add(deposit);
        depositMapping.put(deposit.getOwner(), deposits);
    }

    public Deposit openNewDeposit(Customer customer, Account account, BigDecimal amount, Clock clock){
        return openNewDeposit(customer, account, amount, Period.ofYears(1), clock);
    }

    public Deposit openNewDeposit(Customer customer, Account account, BigDecimal amount, Period period, Clock clock){
        Deposit deposit = new Deposit(customer, period, clock);
        addDeposit(deposit);
        account.transferTo(deposit, amount, clock);
        deposit.setSourceAccount(account);
        return deposit;
    }

    public Deposit openNewDepositWithInsurance(Customer customer, Account account, BigDecimal amount, Period period, Clock clock){
        // TODO: 11.06.18 implement
        Deposit deposit = new Deposit(customer, period, clock);
        addDeposit(deposit);
        account.transferTo(deposit, amount, clock);
        deposit.setSourceAccount(account);
        return deposit;
    }

    public void terminateDeposit(Deposit deposit, Clock clock){
        deposit.terminate(clock);
    }

    public void addFundsToDeposit(Deposit deposit, BigDecimal amount, Clock clock){
        // TODO: 11.06.18 implement
        deposit.getSourceAccount().transferTo(deposit, amount, clock);
    }
}
