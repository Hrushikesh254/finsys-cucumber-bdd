@smoke @company @delete-company
Feature: TC04 - Delete Company (No Revenue/Expense Data)
  As a CFO or FM user of FinSys application
  I want to delete a company that has no revenue or expense data
  So that unused companies can be removed from the system

  # US-SBDC-1001: Verify of Deletion of a Recently added Company whose revenue and expense related information is not available

  Background:
    Given the FinSys application is launched
    When the user enters valid username "dummycfo"
    And the user enters valid password "passw0rd"
    And the user clicks on Login button
    Then the user should be navigated to the Home Page

  Scenario: TC04 - Successfully delete a company with no associated revenue or expense data
    When the user expands the Financial Analysis left accordion pane
    And the user clicks on Manage Company sub node
    Then the Manage Company panel should be displayed
    When the user selects a company from the list
    And the user clicks the Destroy button
    And the user confirms the deletion by clicking OK
    Then the company "DeleteTestCo" should be deleted from the list
