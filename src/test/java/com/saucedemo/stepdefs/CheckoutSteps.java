package com.saucedemo.stepdefs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import com.saucedemo.pages.CheckoutPage;
//import com.saucedemo.pages.InventoryPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CheckoutSteps {

    private static final Logger log = LogManager.getLogger(CheckoutSteps.class);

    private CheckoutPage checkoutPage;
//    private InventoryPage inventoryPage;

    // ── Step Definitions ─────────────────────────────

    @Then("user should be navigated to checkout step one page")
    public void userShouldBeNavigatedToCheckoutStepOnePage() {
        checkoutPage = new CheckoutPage();
        Assert.assertTrue(checkoutPage.isCheckoutStepOneDisplayed(),
                "Checkout step one page is not displayed");
        log.info("User is on checkout step one page");
    }

    @Then("user should be navigated to checkout step two page")
    public void userShouldBeNavigatedToCheckoutStepTwoPage() {
        checkoutPage = new CheckoutPage();
        Assert.assertTrue(checkoutPage.isCheckoutStepTwoDisplayed(),
                "Checkout step two page is not displayed");
        log.info("User is on checkout step two page");
    }

    @When("user enters first name {string} last name {string} and postal code {string}")
    public void userEntersShippingDetails(String firstName,
                                          String lastName,
                                          String postalCode) {
        checkoutPage = new CheckoutPage();
        checkoutPage.fillShippingDetails(firstName, lastName, postalCode);
        log.info("Entered shipping details: {} {} {}",
                firstName, lastName, postalCode);
    }

    @And("user clicks continue on checkout")
    public void userClicksContinueOnCheckout() {
        checkoutPage = new CheckoutPage();
        checkoutPage.clickContinue();
        log.info("Clicked continue on checkout");
    }

    @And("user clicks cancel on checkout")
    public void userClicksCancelOnCheckout() {
        checkoutPage = new CheckoutPage();
        checkoutPage.clickCancel();
        log.info("Clicked cancel on checkout");
    }

    @Then("order summary should be displayed")
    public void orderSummaryDisplayed() {
        checkoutPage = new CheckoutPage();
        Assert.assertFalse(checkoutPage.getSubtotal().isEmpty(),
                "Subtotal is not displayed");
        Assert.assertFalse(checkoutPage.getTax().isEmpty(),
                "Tax is not displayed");
        Assert.assertFalse(checkoutPage.getTotal().isEmpty(),
                "Total is not displayed");
        log.info("Order summary verified — Subtotal: {} Tax: {} Total: {}",
                checkoutPage.getSubtotal(),
                checkoutPage.getTax(),
                checkoutPage.getTotal());
    }

    @And("user verifies order total is displayed")
    public void userVerifiesOrderTotalIsDisplayed() {
        orderSummaryDisplayed();
    }

    @And("user clicks finish button")
    public void userClicksFinishButton() {
        checkoutPage = new CheckoutPage();
        checkoutPage.clickFinish();
        log.info("Clicked finish button");
    }

    @Then("order should be confirmed")
    public void orderShouldBeConfirmed() {
        checkoutPage = new CheckoutPage();
        Assert.assertTrue(checkoutPage.isOrderConfirmed(),
                "Order confirmation page is not displayed");
        log.info("Order is confirmed");
    }

    @Then("confirmation message should be {string}")
    public void confirmationMessageShouldBe(String expectedMessage) {
        checkoutPage = new CheckoutPage();
        Assert.assertEquals(checkoutPage.getConfirmationHeader(),
                expectedMessage,
                "Confirmation message does not match");
        log.info("Confirmation message verified: {}", expectedMessage);
    }

    @Then("checkout error message should be {string}")
    public void checkoutErrorMessageShouldBe(String expectedError) {
        checkoutPage = new CheckoutPage();
        Assert.assertTrue(checkoutPage.isErrorMessageDisplayed(),
                "Error message is not displayed");
        Assert.assertEquals(checkoutPage.getErrorMessage(),
                expectedError,
                "Error message does not match");
        log.info("Checkout error message verified: {}", expectedError);
    }

    @And("user clicks back to products button")
    public void userClicksBackToProductsButton() {
        checkoutPage = new CheckoutPage();
        checkoutPage.clickBackToProducts();
        log.info("Clicked back to products");
    }
}