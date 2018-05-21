Feature: Termination

  Scenario: Termination date
    Given customer opened a deposit for a period of one year
    When one year has passed
    Then the money is transferred back to the account the funds were taken from