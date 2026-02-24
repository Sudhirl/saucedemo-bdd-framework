package com.saucedemo.stepdefs;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import com.saucedemo.pages.InventoryPage;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class InventorySteps {

    private static final Logger log = LogManager.getLogger(InventorySteps.class);

    private InventoryPage inventoryPage;

    // ── Step Definitions ─────────────────────────────

    @Then("inventory page should display products")
    public void inventoryPageShouldDisplayProducts() {
        inventoryPage = new InventoryPage();
        Assert.assertTrue(inventoryPage.getInventoryItemCount() > 0,
                "No products displayed on inventory page");
        log.info("Inventory page displays {} products",
                inventoryPage.getInventoryItemCount());
    }

    @When("user adds {string} to cart")
    public void userAddsProductToCart(String productName) {
        inventoryPage = new InventoryPage();
        inventoryPage.addItemToCartByName(productName);
        log.info("Added '{}' to cart", productName);
    }

    @When("user removes {string} from cart")
    public void userRemovesProductFromCart(String productName) {
        inventoryPage = new InventoryPage();
        inventoryPage.removeItemFromCartByName(productName);
        log.info("Removed '{}' from cart", productName);
    }

    @Then("cart icon should show {string} item")
    public void cartIconShouldShowItem(String expectedCount) {
        inventoryPage = new InventoryPage();
        Assert.assertEquals(
                String.valueOf(inventoryPage.getCartItemCount()),
                expectedCount,
                "Cart item count does not match"
        );
        log.info("Cart shows {} item(s)", expectedCount);
    }

    @Then("cart icon should show {string} items")
    public void cartIconShouldShowItems(String expectedCount) {
        cartIconShouldShowItem(expectedCount);
    }

    @Then("cart icon should not be displayed")
    public void cartIconShouldNotBeDisplayed() {
        inventoryPage = new InventoryPage();
        Assert.assertFalse(inventoryPage.isCartBadgeDisplayed(),
                "Cart badge is still displayed after removing item");
        log.info("Cart badge is not displayed — cart is empty");
    }

    @When("user sorts products by {string}")
    public void userSortsProductsBy(String sortOption) {
        inventoryPage = new InventoryPage();
        inventoryPage.sortProductsBy(sortOption);
        log.info("Sorted products by: {}", sortOption);
    }

    @Then("products should be sorted by price low to high")
    public void productsShouldBeSortedByPriceLowToHigh() {
        inventoryPage = new InventoryPage();
        List<String> prices = inventoryPage.getAllProductPrices();

        for (int i = 0; i < prices.size() - 1; i++) {
            double current = parsePrice(prices.get(i));
            double next = parsePrice(prices.get(i + 1));
            Assert.assertTrue(current <= next,
                    "Products are not sorted by price low to high");
        }
        log.info("Products verified sorted by price low to high");
    }

    @Then("products should be sorted by price high to low")
    public void productsShouldBeSortedByPriceHighToLow() {
        inventoryPage = new InventoryPage();
        List<String> prices = inventoryPage.getAllProductPrices();

        for (int i = 0; i < prices.size() - 1; i++) {
            double current = parsePrice(prices.get(i));
            double next = parsePrice(prices.get(i + 1));
            Assert.assertTrue(current >= next,
                    "Products are not sorted by price high to low");
        }
        log.info("Products verified sorted by price high to low");
    }

    @Then("products should be sorted alphabetically A to Z")
    public void productsShouldBeSortedAlphabeticallyAToZ() {
        inventoryPage = new InventoryPage();
        List<String> names = inventoryPage.getAllProductNames();

        for (int i = 0; i < names.size() - 1; i++) {
            Assert.assertTrue(
                    names.get(i).compareToIgnoreCase(names.get(i + 1)) <= 0,
                    "Products are not sorted A to Z"
            );
        }
        log.info("Products verified sorted A to Z");
    }

    @Then("products should be sorted alphabetically Z to A")
    public void productsShouldBeSortedAlphabeticallyZToA() {
        inventoryPage = new InventoryPage();
        List<String> names = inventoryPage.getAllProductNames();

        for (int i = 0; i < names.size() - 1; i++) {
            Assert.assertTrue(
                    names.get(i).compareToIgnoreCase(names.get(i + 1)) >= 0,
                    "Products are not sorted Z to A"
            );
        }
        log.info("Products verified sorted Z to A");
    }

    @When("user clicks on product {string}")
    public void userClicksOnProduct(String productName) {
        inventoryPage = new InventoryPage();
        inventoryPage.clickOnProductByName(productName);
        log.info("Clicked on product: {}", productName);
    }

    @Then("user should be navigated to product detail page")
    public void userShouldBeNavigatedToProductDetailPage() {
        inventoryPage = new InventoryPage();
        Assert.assertTrue(
                inventoryPage.getCurrentUrl().contains("inventory-item"),
                "User is not on product detail page"
        );
        log.info("User is on product detail page");
    }

    @When("user clicks on cart icon")
    public void userClicksOnCartIcon() {
        inventoryPage = new InventoryPage();
        inventoryPage.clickCartIcon();
        log.info("Clicked cart icon");
    }

    // ── Helper ───────────────────────────────────────
    private double parsePrice(String price) {
        // Removes "$" and converts to double
        return Double.parseDouble(price.replace("$", "").trim());
    }
}