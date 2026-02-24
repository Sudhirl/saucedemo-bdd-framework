@inventory
Feature: Inventory Page Functionality
  As a logged in user
  I want to browse and interact with products
  So that I can find and add items to my cart

  Background:
    Given user is on the login page
    And user logs in with valid credentials

  @smoke
  Scenario: Verify inventory page is displayed after login
    Then user should be navigated to inventory page
    And inventory page should display products

  @smoke
  Scenario: Add single product to cart
    When user adds "Sauce Labs Backpack" to cart
    Then cart icon should show "1" item

  @regression
  Scenario: Add multiple products to cart
    When user adds "Sauce Labs Backpack" to cart
    And user adds "Sauce Labs Bike Light" to cart
    Then cart icon should show "2" items

  @regression
  Scenario: Remove product from inventory page
    When user adds "Sauce Labs Backpack" to cart
    Then cart icon should show "1" item
    When user removes "Sauce Labs Backpack" from cart
    Then cart icon should not be displayed

  @regression
  Scenario: Sort products by price low to high
    When user sorts products by "Price (low to high)"
    Then products should be sorted by price low to high

  @regression
  Scenario: Sort products by price high to low
    When user sorts products by "Price (high to low)"
    Then products should be sorted by price high to low

  @regression
  Scenario: Sort products by name A to Z
    When user sorts products by "Name (A to Z)"
    Then products should be sorted alphabetically A to Z

  @regression
  Scenario: Sort products by name Z to A
    When user sorts products by "Name (Z to A)"
    Then products should be sorted alphabetically Z to A

  @regression
  Scenario: Navigate to product detail page
    When user clicks on product "Sauce Labs Backpack"
    Then user should be navigated to product detail page