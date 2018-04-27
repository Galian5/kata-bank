Feature: Transfer

  Scenario: transfer money
    Given balance on account A is 100
    And balance on account B is 1000
    When transferred 99.91 from account A to B
    Then balance on account A is 0.09
    And balance on account B is 1099.91