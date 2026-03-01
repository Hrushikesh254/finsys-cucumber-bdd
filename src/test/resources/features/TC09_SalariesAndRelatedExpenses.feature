@smoke @salaries
Feature: TC09 - Salaries and Related Expenses

  Background:
    Given the FinSys application is launched
    When the user enters valid username "dummycfo"
    And the user enters valid password "passw0rd"
    And the user clicks on Login button
    Then the user should be navigated to the Home Page

  @smoke @salaries
  Scenario: TC09 - Successfully create a new Salaries and Related Expenses entry with valid details
    When the user expands the Financial Analysis left accordion pane
    And the user clicks on Salaries and Related Expenses sub node
    Then the module panel should be displayed
    When the user clicks on the New icon at the top of the module list
    And the user enters salaries and related expenses details:
      | total_plant_employee            | 50   |
      | plant_employee_monthly_salary   | 2000 |
      | total_marketing_force           | 10   |
      | marketing_force_monthly_salary  | 3000 |
    And the user clicks on the Save button in the module form
    Then the salaries and related expenses entry should be created successfully
