package bank;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.PendingException;

public class Stepdefs {
    @Given("^a customer has (\\d+) accounts open$")
    public void CustomerHasAccountOpen(int accounts) throws Throwable {
        throw new PendingException();
    }
    @When("^he lists his accounts$")
    public void he_lists_his_accounts() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^both of them are on the list$")
    public void both_of_them_are_on_the_list() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^no other account is listed$")
    public void no_other_account_is_listed() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}
