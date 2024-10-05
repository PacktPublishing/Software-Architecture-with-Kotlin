Feature: Household creation

  Scenario: Creation of households with non-empty surnames

    Given the household surname is non-empty

    When the user requests to create the household

    Then the household is created