@smoke @equipments
Feature: TC08 - Equipments

  Background:
    Given the FinSys application is launched
    When the user enters valid username "dummycfo"
    And the user enters valid password "passw0rd"
    And the user clicks on Login button
    Then the user should be navigated to the Home Page

  @smoke @equipments
  Scenario: TC08 - Successfully create a new Equipment entry with valid details
    When the user expands the Financial Analysis left accordion pane
    And the user clicks on Equipments sub node
    Then the module panel should be displayed
    When the user clicks on the New icon at the top of the module list
    And the user enters equipment details:
      | manufacturing_assembly | 50000 |
      | it_infra               | 80000 |
      | generator_backup       | 30000 |
      | other                  | 10000 |
    And the user clicks on the Save button in the module form
    Then the equipment entry should be created successfully
