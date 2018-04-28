Feature: Account

  Scenario: list accounts
    Given a customer has 2 accounts open
    When he lists his accounts
    Then both of them are on the list
    And no other account is listed