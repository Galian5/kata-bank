Feature: DepositNewFunds

  Scenario: Deposit new funds
    Given there is a customer with a deposit opened
    When he transfers new funds to the existing deposit
    Then the interest rate for these funds is 0.5% greater than the original interest rate
    And the interest for this funds is proportional to the deposit time left
