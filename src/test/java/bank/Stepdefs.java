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
    private BigDecimal amount; //change to local variable

    // list accs
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
    // new acc
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
        assert a1.getBalance().equals(BigDecimal.ZERO);
    }

    //transfer
    @Given("^balance on account A is 100$")
    public void balance_on_the_account_A_is_100(){
        customer = new Customer();
        a1 = new Account(customer);
        a1.setBalance(BigDecimal.valueOf(100));

    }

    @Given("^balance on account B is 1000$")
    public void balance_on_account_B_is_1000(){
        a2 = new Account(customer);
        a2.setBalance(BigDecimal.valueOf(1000));
    }




    @When("^transferred 99.91 from account A to B$")
    public void transferred_99_91_from_account_A_to_B(){
        amount = new BigDecimal(99.91);
        a1.setBalance(a1.getBalance().subtract(amount));
        a2.setBalance(a2.getBalance().add(amount));
    }

    @Then("^balance on account A is 0.09$")
    public void balance_on_account_A_is_0_09(){
        assert a1.getBalance().equals(BigDecimal.valueOf(0.09));
    }

    @Then("^balance on account B is 1099.91$")
    public void balance_on_account_B_is_1099_91(){
        assert a2.getBalance().equals(BigDecimal.valueOf(1099.91));
    }

    // deposit
    @Given("^balance on the account is 100$")
    public void balance_on_the_account_is_100(){

        customer = new Customer();
        a1 = new Account(customer);
        a1.setBalance(BigDecimal.valueOf(100));
    }

    @When("^customer deposits 10 to this account$")
    public void customer_deposits_10_to_this_account(){
        a1.setBalance(a1.getBalance().add(BigDecimal.valueOf(10)));
        // add deposit
    }

    @Then("^balance on the account is 110$")
    public void balance_on_the_account_is_110(){
        assert a1.getBalance().equals(BigDecimal.valueOf(110));
    }

    // withdrawn
    @Given("^balance on this account is 100$")
    public void balance_on_this_account_is_100(){
        customer = new Customer();
        a1 = new Account(customer);
        a1.setBalance(BigDecimal.valueOf(100));
    }

    @When("^customer withdraws 90 to this account$")
    public void customer_withdraws_90_to_this_account(){
        a1.setBalance(a1.getBalance().subtract(BigDecimal.valueOf(90)));
    }

    @Then("^balance on the account is 10$")
    public void balance_on_the_account_is_10(){
        assert a1.getBalance().equals(BigDecimal.valueOf(10));
    }

}
