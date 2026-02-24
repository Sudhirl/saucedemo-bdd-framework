package com.saucedemo.stepdefs;

import org.testng.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.saucedemo.config.ConfigReader;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

    private static final Logger log = LogManager.getLogger(LoginSteps.class);

    // ── Page Objects ─────────────────────────────────
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private ConfigReader config = ConfigReader.getInstance();

    // ── Step Definitions ─────────────────────────────

    @Given("user is on the login page")
    public void userIsOnTheLoginPage() {
        loginPage = new LoginPage();
        loginPage.navigateToLoginPage();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Login page is not displayed");
        log.info("User is on the login page");
    }

    @When("user enters valid username and password")
    public void userEntersValidUsernameAndPassword() {
        loginPage = new LoginPage();
        loginPage.enterUsername(config.getValidUsername());
        loginPage.enterPassword(config.getValidPassword());
        log.info("Entered valid credentials");
    }

    @When("user enters {string} as username and {string} as password")
    public void userEntersCredentials(String username, String password) {
        loginPage = new LoginPage();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        log.info("Entered username: {} and password", username);
    }

    @When("user enters locked out username and password")
    public void userEntersLockedOutCredentials() {
        loginPage = new LoginPage();
        loginPage.enterUsername(config.getLockedUsername());
        loginPage.enterPassword(config.getLockedPassword());
        log.info("Entered locked out user credentials");
    }

    @And("user clicks login button")
    public void userClicksLoginButton() {
        loginPage = new LoginPage();
        loginPage.clickLoginButton();
        log.info("Clicked login button");
    }

    @And("user logs in with valid credentials")
    public void userLogsInWithValidCredentials() {
        loginPage = new LoginPage();
        loginPage.loginWithValidCredentials();
        log.info("Logged in with valid credentials");
    }

    @Then("user should be navigated to inventory page")
    public void userShouldBeNavigatedToInventoryPage() {
        inventoryPage = new InventoryPage();
        Assert.assertTrue(inventoryPage.isInventoryPageDisplayed(),
                "Inventory page is not displayed after login");
        log.info("User is on the inventory page");
    }

    @Then("user should see error message {string}")
    public void userShouldSeeErrorMessage(String expectedError) {
        loginPage = new LoginPage();
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message is not displayed");
        Assert.assertEquals(loginPage.getErrorMessage(), expectedError,
                "Error message does not match");
        log.info("Error message verified: {}", expectedError);
    }

    @When("user clicks on hamburger menu")
    public void userClicksOnHamburgerMenu() {
        inventoryPage = new InventoryPage();
        inventoryPage.openHamburgerMenu();
        log.info("Clicked hamburger menu");
    }

    @And("user clicks logout")
    public void userClicksLogout() {
        inventoryPage = new InventoryPage();
        inventoryPage.logout();
        log.info("Clicked logout");
    }

    @Then("user should be navigated back to login page")
    public void userShouldBeNavigatedBackToLoginPage() {
        loginPage = new LoginPage();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "User is not navigated back to login page");
        log.info("User is back on login page");
    }
}