package bank;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AccountMapping {
    private Map<Customer, Set<Account>> mapping = new HashMap<>();

    public Set<Account> getAccounts(Customer customer){
        return mapping.getOrDefault(customer, Collections.emptySet());
    }

    public void addAccount(Account account){
        Set<Account> accounts = getAccounts(account.getOwner());
        accounts.add(account);
        mapping.put(account.getOwner(), accounts);
    }
}
