@login
Feature: Login Functionality
  As a user of SauceDemo
  I want to be able to login with valid credentials
  So that I can access the inventory page

  Background:
    Given user is on the login page

  @smoke @valid-login
  Scenario: Successful login with valid credentials
    When user enters valid username and password
    And user clicks login button
    Then user should be navigated to inventory page

  @smoke @invalid-login
  Scenario: Login with invalid username
    When user enters "invalid_user" as username and "secret_sauce" as password
    And user clicks login button
    Then user should see error message "Epic sadface: Username and password do not match any user in this service"

  @regression @invalid-login
  Scenario: Login with invalid password
    When user enters "standard_user" as username and "wrong_password" as password
    And user clicks login button
    Then user should see error message "Epic sadface: Username and password do not match any user in this service"

  @regression @invalid-login
  Scenario: Login with empty username
    When user enters "" as username and "secret_sauce" as password
    And user clicks login button
    Then user should see error message "Epic sadface: Username is required"

  @regression @invalid-login
  Scenario: Login with empty password
    When user enters "standard_user" as username and "" as password
    And user clicks login button
    Then user should see error message "Epic sadface: Password is required"

  @regression @locked-user
  Scenario: Login with locked out user
    When user enters locked out username and password
    And user clicks login button
    Then user should see error message "Epic sadface: Sorry, this user has been locked out."

  @regression
  Scenario: Successful logout after login
    When user enters valid username and password
    And user clicks login button
    Then user should be navigated to inventory page
    When user clicks on hamburger menu
    And user clicks logout
    Then user should be navigated back to login page

  @regression
  Scenario Outline: Login with multiple invalid credentials
    When user enters "<username>" as username and "<password>" as password
    And user clicks login button
    Then user should see error message "<errorMessage>"

    Examples:
      | username       | password       | errorMessage                                                                    |
      | invalid_user   | invalid_pass   | Epic sadface: Username and password do not match any user in this service       |
      | standard_user  | wrong_pass     | Epic sadface: Username and password do not match any user in this service       |
      |                |                | Epic sadface: Username is required                                              |