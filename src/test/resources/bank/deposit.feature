Feature: Deposit

  Scenario: deposit money
    Given balance on the account is 100
    When customer deposits 10 to this account
    Then balance on the account is 110