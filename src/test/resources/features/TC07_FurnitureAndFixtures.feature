@smoke @furniture
Feature: TC07 - Furniture and Fixtures

  Background:
    Given the FinSys application is launched
    When the user enters valid username "dummycfo"
    And the user enters valid password "passw0rd"
    And the user clicks on Login button
    Then the user should be navigated to the Home Page

  @smoke @furniture
  Scenario: TC07 - Successfully create a new Furniture and Fixtures entry with valid details
    When the user expands the Financial Analysis left accordion pane
    And the user clicks on Furniture and Fixtures sub node
    Then the module panel should be displayed
    When the user clicks on the New icon at the top of the module list
    And the user enters furniture and fixtures details:
      | manufacturing_assembly | 15000 |
      | it_infra               | 20000 |
      | storage_raw_material   | 10000 |
      | other                  | 5000  |
    And the user clicks on the Save button in the module form
    Then the furniture and fixtures entry should be created successfully
