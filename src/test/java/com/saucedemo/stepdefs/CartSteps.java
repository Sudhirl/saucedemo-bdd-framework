package com.saucedemo.stepdefs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import com.saucedemo.pages.CartPage;
//import com.saucedemo.pages.InventoryPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CartSteps {

    private static final Logger log = LogManager.getLogger(CartSteps.class);

    private CartPage cartPage;
//    private InventoryPage inventoryPage;

    // ── Step Definitions ─────────────────────────────

    @Then("user should be navigated to cart page")
    public void userShouldBeNavigatedToCartPage() {
        cartPage = new CartPage();
        Assert.assertTrue(cartPage.isCartPageDisplayed(),
                "Cart page is not displayed");
        log.info("User is on cart page");
    }

    @Then("cart should contain product {string}")
    public void cartShouldContainProduct(String productName) {
        cartPage = new CartPage();
        Assert.assertTrue(cartPage.isItemPresentInCart(productName),
                "Product '" + productName + "' is not present in cart");
        log.info("Cart contains product: {}", productName);
    }

    @Then("cart should have {string} items")
    public void cartShouldHaveItems(String expectedCount) {
        cartPage = new CartPage();
        Assert.assertEquals(
                String.valueOf(cartPage.getCartItemCount()),
                expectedCount,
                "Cart item count does not match"
        );
        log.info("Cart has {} items", expectedCount);
    }

    @Then("cart should be empty")
    public void cartShouldBeEmpty() {
        cartPage = new CartPage();
        Assert.assertEquals(cartPage.getCartItemCount(), 0,
                "Cart is not empty");
        log.info("Cart is empty");
    }

    @When("user removes {string} from cart page")
    public void userRemovesProductFromCartPage(String productName) {
        cartPage = new CartPage();
        cartPage.removeItemFromCart(productName);
        log.info("Removed '{}' from cart page", productName);
    }

    @And("user clicks continue shopping")
    public void userClicksContinueShopping() {
        cartPage = new CartPage();
        cartPage.clickContinueShopping();
        log.info("Clicked continue shopping");
    }

    @And("user clicks checkout button")
    public void userClicksCheckoutButton() {
        cartPage = new CartPage();
        cartPage.clickCheckout();
        log.info("Clicked checkout button");
    }
}