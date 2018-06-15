Feature: DepositWithdrawal

  Scenario: Deposit insurance - early withdrawal
    Given there is a customer who is about to open a new deposit
    And he decided to add the insurance
    When he decides to do an early withdrawal
    Then he does not lose any accumulated interest