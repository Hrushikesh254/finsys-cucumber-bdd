@smoke @company @create-company
Feature: TC02 - Create Company

  Background:
    Given the FinSys application is launched
    When the user enters valid username "dummycfo"
    And the user enters valid password "passw0rd"
    And the user clicks on Login button
    Then the user should be navigated to the Home Page

  @smoke @company @create-company
  Scenario: TC02 - Successfully create a new company with all valid details
    When the user expands the Financial Analysis left accordion pane
    And the user clicks on Manage Company sub node
    Then the Manage Company panel should be displayed
    When the user clicks on the New icon at the top of the company list
    And the user enters company details with unique name
    And the user clicks on the Save button in the company form
    Then the newly created company should appear at the top of the company list
