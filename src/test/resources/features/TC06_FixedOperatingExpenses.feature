@smoke @fixed-operating
Feature: TC06 - Fixed Operating Expenses

  Background:
    Given the FinSys application is launched
    When the user enters valid username "dummycfo"
    And the user enters valid password "passw0rd"
    And the user clicks on Login button
    Then the user should be navigated to the Home Page

  @smoke @fixed-operating
  Scenario: TC06 - Successfully create a new Fixed Operating Expense entry with valid details
    When the user expands the Financial Analysis left accordion pane
    And the user clicks on Fixed Operating Expenses sub node
    Then the module panel should be displayed
    When the user clicks on the New icon at the top of the module list
    And the user enters fixed operating expense details:
      | rent                    | 10000 |
      | utilities               | 12000 |
      | telephone_communication | 8000  |
      | office_expenses         | 5000  |
    And the user clicks on the Save button in the module form
    Then the fixed operating expense entry should be created successfully
