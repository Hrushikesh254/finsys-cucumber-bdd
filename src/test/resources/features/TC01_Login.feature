@smoke @login
Feature: TC01 - Login Functionality
  As a user of FinSys application
  I want to login with valid credentials
  So that I can access the application features

  # US-SBDC-1000: Verify login functionality by passing valid username and password

  Scenario: TC01 - Successful login with valid username and password
    Given the FinSys application is launched
    When the user enters valid username "dummycfo"
    And the user enters valid password "passw0rd"
    And the user clicks on Login button
    Then the user should be navigated to the Home Page
    And the application should display the Home Page URL
