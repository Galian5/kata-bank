package bank;

import java.math.BigDecimal;
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

    public Deposit openNewDeposit(Customer customer, Account account, BigDecimal amount){
        return openNewDeposit(customer, account, amount, Period.ofYears(1));
    }

    public Deposit openNewDeposit(Customer customer, Account account, BigDecimal amount, Period period){
        Deposit deposit = new Deposit(customer, period);
        addDeposit(deposit);
        account.transferTo(deposit, amount);
        return deposit;
    }
}
