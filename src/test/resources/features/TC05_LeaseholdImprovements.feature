@smoke @leasehold
Feature: TC05 - Leasehold Improvements

  Background:
    Given the FinSys application is launched
    When the user enters valid username "dummycfo"
    And the user enters valid password "passw0rd"
    And the user clicks on Login button
    Then the user should be navigated to the Home Page

  @smoke @leasehold
  Scenario: TC05 - Successfully create a new Leasehold Improvement entry with valid details
    When the user expands the Financial Analysis left accordion pane
    And the user clicks on Leasehold Improvements sub node
    Then the module panel should be displayed
    When the user clicks on the New icon at the top of the module list
    And the user enters leasehold improvement details:
      | leveling      | 10000 |
      | electric_work | 15000 |
      | water_work    | 12000 |
      | other         | 5000  |
    And the user clicks on the Save button in the module form
    Then the leasehold improvement entry should be created successfully
