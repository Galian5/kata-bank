package bank;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

import java.math.BigDecimal;
import java.util.Set;

public class Stepdefs {
    private AccountMapping mapping;
    private Customer customer;
    private Account a1, a2;
    private Set<Account> testAccounts;

    @Given("^a customer has 2 accounts open$")
    public void CustomerHasAccountOpen() throws Throwable {
        mapping = new AccountMapping();
        customer = new Customer();
        a1 = new Account(customer);
        a2 = new Account(customer);
        mapping.addAccount(a1);
        mapping.addAccount(a2);

    }

    @When("^he lists his accounts$")
    public void he_lists_his_accounts() {
        testAccounts = mapping.getAccounts(customer);
    }

    @Then("^both of them are on the list$")
    public void both_of_them_are_on_the_list() {
        assert testAccounts.contains(a1);
        assert testAccounts.contains(a2);
    }

    @Then("^no other account is listed$")
    public void no_other_account_is_listed() {
        assert testAccounts.stream().allMatch(account -> account == a1 || account == a2);
    }

    @Given("^a customer wants to open an account$")
    public void a_customer_wants_to_open_an_account() {
        customer = new Customer();
        mapping = new AccountMapping();

    }

    @When("^his account is created$")
    public void his_account_is_created() {
        a1 = new Account(customer);
        mapping.addAccount(a1);

    }

    @Then("^there is a new account on his account list$")
    public void there_is_a_new_account_on_his_account_list() {
        testAccounts = mapping.getAccounts(customer);
        assert testAccounts.contains(a1);
    }

    @Then("^the balance on this account is 0$")
    public void the_balance_on_this_account_is_0() {
        assert a1.getBalance() == BigDecimal.ZERO;
    }

}
