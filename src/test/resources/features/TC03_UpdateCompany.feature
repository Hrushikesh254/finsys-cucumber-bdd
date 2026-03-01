@smoke @company @update-company
Feature: TC03 - Update Company Details
  As a CFO or FM user of FinSys application
  I want to update an existing company's details
  So that company information stays accurate and up-to-date

  # US-SBDC-1001: Verify updated details of the Company are properly reflecting on View Company Page

  Background:
    Given the FinSys application is launched
    When the user enters valid username "dummycfo"
    And the user enters valid password "passw0rd"
    And the user clicks on Login button
    Then the user should be navigated to the Home Page

  Scenario: TC03 - Successfully update an existing company's details
    When the user expands the Financial Analysis left accordion pane
    And the user clicks on Manage Company sub node
    Then the Manage Company panel should be displayed
    When the user clicks on the Plus icon to view a company
    And the user modifies the company details:
      | companyName   | UpdatedCompany      |
      | address       | 456 Updated Street  |
      | phone         | 9123456780          |
      | email         | updated@company.com |
      | totalEmployee | 200000              |
    And the user clicks on the Save button in the company form
    And the user clicks on the Plus icon to view a company
    Then the updated company details should be reflected on the View Company page
