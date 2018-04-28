Feature: NewAccount


  Scenario: open account
    Given a customer wants to open an account
    When his account is created
    Then there is a new account on his account list
    And the balance on this account is 0