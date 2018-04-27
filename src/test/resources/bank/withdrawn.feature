Feature: Withdrawn

  Scenario: withdrawn money
    Given balance on this account is 100
    When customer withdraws 90 to this account
    Then balance on the account is 10