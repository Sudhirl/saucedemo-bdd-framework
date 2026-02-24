@checkout
Feature: Checkout Functionality
  As a logged in user
  I want to complete the checkout process
  So that I can place my order successfully

  Background:
    Given user is on the login page
    And user logs in with valid credentials
    And user adds "Sauce Labs Backpack" to cart
    And user clicks on cart icon
    And user clicks checkout button

  @smoke @end-to-end
  Scenario: Complete checkout with valid details
    When user enters first name "John" last name "Doe" and postal code "12345"
    And user clicks continue on checkout
    Then user should be navigated to checkout step two page
    And order summary should be displayed
    When user clicks finish button
    Then order should be confirmed
    And confirmation message should be "Thank you for your order!"

  @regression
  Scenario: Checkout with empty first name
    When user enters first name "" last name "Doe" and postal code "12345"
    And user clicks continue on checkout
    Then checkout error message should be "Error: First Name is required"

  @regression
  Scenario: Checkout with empty last name
    When user enters first name "John" last name "" and postal code "12345"
    And user clicks continue on checkout
    Then checkout error message should be "Error: Last Name is required"

  @regression
  Scenario: Checkout with empty postal code
    When user enters first name "John" last name "Doe" and postal code ""
    And user clicks continue on checkout
    Then checkout error message should be "Error: Postal Code is required"

  @regression
  Scenario: Cancel checkout and return to cart
    When user enters first name "John" last name "Doe" and postal code "12345"
    And user clicks cancel on checkout
    Then user should be navigated to cart page

  @regression
  Scenario Outline: Checkout with multiple user details
    When user enters first name "<firstName>" last name "<lastName>" and postal code "<postalCode>"
    And user clicks continue on checkout
    Then user should be navigated to checkout step two page
    When user clicks finish button
    Then order should be confirmed

    Examples:
      | firstName | lastName | postalCode |
      | John      | Doe      | 12345      |
      | Jane      | Smith    | 67890      |
      | Bob       | Johnson  | 11111      |

  @smoke @end-to-end
  Scenario: Complete end to end purchase flow
    When user enters first name "John" last name "Doe" and postal code "12345"
    And user clicks continue on checkout
    And user verifies order total is displayed
    And user clicks finish button
    Then order should be confirmed
    And confirmation message should be "Thank you for your order!"
    When user clicks back to products button
    Then user should be navigated to inventory page