Feature: Interest

  Scenario: Interest rate
    Given customer has a new deposit for a period of 6 months with funds 100
    And the deposit yearly interest rate is ten percent
    When termination date has passed
    Then the 105 is transferred back to his account