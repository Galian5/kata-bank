Feature: Deposit

  Scenario: deposit insurance cost
    Given there is a customer who is about to open a new deposit of any kind
    When he decided to add the insurance to the deposit
    Then the deposited amount is 0.05% lower than the original amount
