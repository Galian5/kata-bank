Feature: NewDeposit

  Scenario: Opening deposit
    Given customer has an account with balance 100
    When he opens a deposit with balance 90
    Then he owns a deposit with balance 90
    And the account has balance 10