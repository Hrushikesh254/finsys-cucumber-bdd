@smoke @user-management
Feature: TC10 - User Management

  Background:
    Given the FinSys application is launched
    When the user enters valid username "dummycfo"
    And the user enters valid password "passw0rd"
    And the user clicks on Login button
    Then the user should be navigated to the Home Page

  @smoke @user-management
  Scenario: TC10 - Successfully create a new user with all valid details
    When the user expands the Financial Analysis left accordion pane
    And the user clicks on User Management sub node
    Then the User Management panel should be displayed
    When the user clicks on the New icon at the top of the user list
    And the user enters new user details:
      | uid       | uidejagruti        |
      | firstname | Jagruti            |
      | lastname  | TestUser           |
      | email     | jagruti@finsys.com |
      | phone     | 9876543210         |
      | photoid   | PHOTO123456        |
      | rolename  | FM                 |
    And the user clicks on the Save button in the user form
    Then the newly created user "uidejagruti" should appear in the user list
