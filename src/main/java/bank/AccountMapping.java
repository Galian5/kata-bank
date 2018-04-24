package bank;

import java.util.*;

public class AccountMapping {
    private HashMap<Customer, Set<Account>> mapping = new HashMap<>();

    public Set<Account> getAccounts(Customer customer) {
        return mapping.getOrDefault(customer, new HashSet<>(Collections.emptySet()));
    }

    public void addAccount(Account account) {
        Set<Account> accounts = getAccounts(account.getOwner());
        accounts.add(account);
        mapping.put(account.getOwner(), accounts);
    }
}
