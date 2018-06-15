package bank;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

public class Stepdefs {
    private AccountMapping mapping;
    private Customer customer;
    private Account a1, a2;
    private Deposit d1;
    private Set<Account> testAccounts;
    private Clock clock;


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
        clock = Clock.fixed(Instant.parse("2018-01-01T00:00:00Z"), ZoneId.of("UTC"));
        a1.transferTo(a2, new BigDecimal("99.91"), clock);
    }

    @Then("^balance on account A is 0.09$")
    public void balance_on_account_A_is_0_09(){
        assert a1.getBalance().equals(new BigDecimal("0.09"));
    }

    @Then("^balance on account B is 1099.91$")
    public void balance_on_account_B_is_1099_91(){
        assert a2.getBalance().equals(new BigDecimal("1099.91"));
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
        clock = Clock.fixed(Instant.parse("2018-01-01T00:00:00Z"), ZoneId.of("UTC"));
        a1.deposit(BigDecimal.valueOf(10), clock);
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
        a1.withdraw(BigDecimal.valueOf(90));
    }

    @Then("^balance on the account is 10$")
    public void balance_on_the_account_is_10(){
        assert a1.getBalance().equals(BigDecimal.valueOf(10));
    }


    // Open new deposit
    @Given("^customer has an account with balance 100$")
    public void customer_has_an_account_with_balance_100(){
        mapping = new AccountMapping();
        customer = new Customer();
        a1 = new Account(customer);
        mapping.addAccount(a1);
        a1.setBalance(BigDecimal.valueOf(100));
    }

    @When("^he opens a deposit with balance 90$")
    public void he_opens_a_deposit_with_balance_90(){
        clock = Clock.fixed(Instant.parse("2018-01-01T00:00:00Z"), ZoneId.of("UTC"));
        d1 = mapping.openNewDeposit(customer, a1, BigDecimal.valueOf(90), clock);
    }

    @Then("^he owns a deposit with balance 90$")
    public void he_owns_a_deposit_with_balance_90(){
        System.out.println(d1.getBalance());
        assert d1.getBalance().equals(new BigDecimal("90.0"));
    }

    @Then("^the account has balance 10$")
    public void the_account_has_balance_10(){
        assert a1.getBalance().equals(BigDecimal.valueOf(10));
    }

    // deposit termination
    @Given("^customer opened a deposit for a period of one year$")
    public void customer_opened_a_deposit_for_a_period_of_one_year(){
        clock = Clock.fixed(Instant.parse("2018-01-01T00:00:00Z"), ZoneId.of("UTC"));
        mapping = new AccountMapping();
        customer = new Customer();
        a1 = new Account(customer);
        a1.setBalance(BigDecimal.valueOf(100));
        d1 = mapping.openNewDeposit(customer, a1, BigDecimal.valueOf(100), clock);
    }

    @When("^one year has passed$")
    public void one_year_has_passed(){
        clock = Clock.fixed(Instant.parse("2019-01-01T00:00:01Z"), ZoneId.of("UTC"));
        mapping.terminateDeposit(d1, clock);
    }

    @Then("^the money is transferred back to the account the funds were taken from$")
    public void the_money_is_transferred_back_to_the_account_the_funds_were_taken_from(){
        assert a1.getBalance().equals(new BigDecimal(100));

    }

    // interest rate
    @Given("^customer has a new deposit for a period of 6 months with funds 100$")
    public void customer_has_a_new_deposit_for_a_period_of_6_months_with_funds_100(){
        clock = Clock.fixed(Instant.parse("2018-01-01T00:00:00Z"), ZoneId.of("UTC"));
        mapping = new AccountMapping();
        customer = new Customer();
        a1 = new Account(customer);
        a1.setBalance(BigDecimal.valueOf(100));
        d1 = mapping.openNewDeposit(customer, a1, BigDecimal.valueOf(100), Period.ofMonths(6), clock);
    }

    @Given("^the deposit yearly interest rate is ten percent$")
    public void the_deposit_yearly_interest_rate_is_ten_percent(){
        d1.setInterestRate(new BigDecimal("0.1"));
    }

    @When("^termination date has passed$")
    public void termination_date_has_passed(){
        clock = Clock.fixed(Instant.parse("2018-07-02T00:00:00Z"), ZoneId.of("UTC"));
        mapping.terminateDeposit(d1, clock);

    }

    @Then("^the 105 is transferred back to his account$")
    public void the_105_is_transferred_back_to_his_account(){
        System.out.println(a1.getBalance());
        assert a1.getBalance().equals(new BigDecimal("105.00"));
    }

    // new deposit funds
    @Given("^there is a customer with a deposit opened$")
    public void there_is_a_customer_with_a_deposit_opened(){
        clock = Clock.fixed(Instant.parse("2018-01-01T00:00:00Z"), ZoneId.of("UTC"));
        mapping = new AccountMapping();
        customer = new Customer();
        a1 = new Account(customer);
        a1.setBalance(BigDecimal.valueOf(200));
        d1 = mapping.openNewDeposit(customer, a1, BigDecimal.valueOf(100), Period.ofMonths(12), clock);
        d1.setInterestRate(new BigDecimal("0.1"));
    }


    @When("^he transfers new funds to the existing deposit$")
    public void he_transfers_new_funds_to_the_existing_deposit(){
        clock = Clock.fixed(Instant.parse("2018-07-01T00:00:00Z"), ZoneId.of("UTC"));

        mapping.addFundsToDeposit(d1, BigDecimal.valueOf(100), clock);

    }

    @Then("^the interest rate for these funds is 0.5% greater than the original interest rate$")
    public void the_interest_rate_for_these_funds_is_05_greater_than_the_original_interest_rate(){
        List<DepositFunds>funds = d1.getBalances();

        BigDecimal rate = d1.getInterestRate(funds.get(funds.size() - 1));
        assert rate.equals(new BigDecimal("0.15"));

    }

    @Then("^the interest for this funds is proportional to the deposit time left$")
    public void the_interest_for_this_funds_is_proportional_to_the_deposit_time_left(){
        clock = Clock.fixed(Instant.parse("2019-01-02T00:00:00Z"), ZoneId.of("UTC"));
        mapping.terminateDeposit(d1, clock);
        System.out.println(a1.getBalance());
        assert a1.getBalance().equals(new BigDecimal("110.000").add(new BigDecimal("107.500")));
    }



    // deposit insurance cost
    @Given("^there is a customer who is about to open a new deposit of any kind$")
    public void there_is_a_customer_who_is_about_to_open_a_new_deposit_of_any_kind(){
        mapping = new AccountMapping();
        customer = new Customer();
        a1 = new Account(customer);
        mapping.addAccount(a1);
        a1.setBalance(BigDecimal.valueOf(100));
    }

    @When("^he decided to add the insurance to the deposit$")
    public void he_decided_to_add_the_insurance_to_the_deposit(){
        clock = Clock.fixed(Instant.parse("2018-01-01T00:00:00Z"), ZoneId.of("UTC"));
        d1 = mapping.openNewDepositWithInsurance(customer, a1, BigDecimal.valueOf(100), Period.ofMonths(12), clock);
    }

    @Then("^the deposited amount is 0.05% lower than the original amount$")
    public void the_deposited_amount_is_005_lower_than_the_original_amount(){
        System.out.println(d1.getBalance());
        assert d1.getBalance().equals(new BigDecimal("99.9500"));
    }


    // deposit insurance early withdrawal
    @Given("^there is a customer who is about to open a new deposit$")
    public void there_is_a_customer_who_is_about_to_open_a_new_deposit(){
        mapping = new AccountMapping();
        customer = new Customer();
        a1 = new Account(customer);
        mapping.addAccount(a1);
        a1.setBalance(BigDecimal.valueOf(100));
    }

    @Given("^he decided to add the insurance$")
    public void he_decided_to_add_the_insurance(){
        clock = Clock.fixed(Instant.parse("2018-01-01T00:00:00Z"), ZoneId.of("UTC"));
        d1 = mapping.openNewDepositWithInsurance(customer, a1, BigDecimal.valueOf(100), Period.ofMonths(12), clock);
    }

    @When("^he decides to do an early withdrawal$")
    public void he_decides_to_do_an_early_withdrawal(){
        clock = Clock.fixed(Instant.parse("2019-01-02T00:00:00Z"), ZoneId.of("UTC"));
        mapping.terminateDeposit(d1, clock);
    }

    @Then("^he does not lose any accumulated interest$")
    public void he_does_not_lose_any_accumulated_interest(){

        System.out.println(a1.getBalance());
        assert a1.getBalance().equals(new BigDecimal("99,9500"));    }

}
