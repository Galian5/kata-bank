Feature: Withdrawn

  Scenario: withdrawn money
    Given balance on the account is 100
    When customer withdrawns 90 to this account
    Then balance on the account is 10