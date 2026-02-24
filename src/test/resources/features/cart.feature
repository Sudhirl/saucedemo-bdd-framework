@cart
Feature: Shopping Cart Functionality
  As a logged in user
  I want to manage items in my cart
  So that I can review before checkout

  Background:
    Given user is on the login page
    And user logs in with valid credentials

  @smoke
  Scenario: Verify cart page is displayed
    When user adds "Sauce Labs Backpack" to cart
    And user clicks on cart icon
    Then user should be navigated to cart page

  @smoke
  Scenario: Verify added product appears in cart
    When user adds "Sauce Labs Backpack" to cart
    And user clicks on cart icon
    Then cart should contain product "Sauce Labs Backpack"

  @regression
  Scenario: Verify multiple products in cart
    When user adds "Sauce Labs Backpack" to cart
    And user adds "Sauce Labs Bike Light" to cart
    And user clicks on cart icon
    Then cart should contain product "Sauce Labs Backpack"
    And cart should contain product "Sauce Labs Bike Light"
    And cart should have "2" items

  @regression
  Scenario: Remove product from cart page
    When user adds "Sauce Labs Backpack" to cart
    And user clicks on cart icon
    Then cart should contain product "Sauce Labs Backpack"
    When user removes "Sauce Labs Backpack" from cart page
    Then cart should be empty

  @regression
  Scenario: Continue shopping from cart page
    When user adds "Sauce Labs Backpack" to cart
    And user clicks on cart icon
    And user clicks continue shopping
    Then user should be navigated to inventory page

  @regression
  Scenario: Proceed to checkout from cart
    When user adds "Sauce Labs Backpack" to cart
    And user clicks on cart icon
    And user clicks checkout button
    Then user should be navigated to checkout step one page