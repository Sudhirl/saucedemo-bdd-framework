package com.saucedemo.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	private static final Logger log = LogManager.getLogger(LoginPage.class);

	// ── Locators ─────────────────────────────────────

	@FindBy(id = "user-name") 
	private WebElement usernameField; 

	@FindBy(id = "password")
	private WebElement passwordField;

	@FindBy(id = "login-button")
	private WebElement loginButton;

	@FindBy(css = "[data-test='error']")
	private WebElement errorMessage;

	@FindBy(css = ".login_logo")
	private WebElement loginLogo;

/*
 * PageFactory initializes elements lazily — only when accessed
 * Q) Why PageFactory?
 * Ans => Without @FindBy and PageFactory, we generally use driver.findElement method. 
 * driver.findElement() is called every single time, Every method call hits the DOM fresh. More verbose and repetitive code. 
 * PageFactory.initElements(driver, this); => 
 * (i) just sets up proxies  (ii) NO actual DOM search happens here ✅ 
 * Actual DOM search happens as below
 * 
 * public void clickLoginButton() {
 * click(loginButton); // DOM search happens HERE — only when needed ✅
 * }
 * 
 * PageFactory wraps each `@FindBy` element in a **proxy object** — a placeholder. 
 * The actual `driver.findElement()` call only happens at the moment you **interact** with the element.
 * 
 * ##  What is a Proxy Object?
 * Think of it like a **lazy employee**:
 * ```
 * You hire an employee (PageFactory.initElements)
 * Employee says "I'll find that document when you ask for it"
 * Later...
 * You ask "Give me the login button" (loginButton.click())
 * Employee NOW goes and finds it ✅
 * ```
 * 
 * vs an eager employee:
 * ```
 * You hire an employee
 * Employee immediately searches for ALL documents
 * Even ones you may never need ❌
 * ```
 * 
 * 🔄 Another Big Benefit — StaleElementException Prevention 
 * Without @FindBy:
 * WebElement btn = driver.findElement(By.id("login-button"));
 * // page refreshes or DOM changes here...
 * btn.click(); // 💥 StaleElementException — reference is dead
 * 
 * With @FindBy:
 * // proxy re-fetches element fresh from DOM every time you use it
 * loginButton.click(); // ✅ always finds fresh element
 */
	
	
	// ── Constructor ──────────────────────────────────
	public LoginPage() {
		super();
	}

	// ── Actions ──────────────────────────────────────
	public void navigateToLoginPage() {
		navigateTo(config.getUrl());
		log.info("Navigated to SauceDemo login page");
	}

	public void enterUsername(String username) {
		type(usernameField, username);
		log.info("Entered username: {}", username);
	}

	public void enterPassword(String password) {
		type(passwordField, password);
		log.info("Entered password");
	}

	public void clickLoginButton() {
		click(loginButton);
		log.info("Clicked login button");
	}

	public void login(String username, String password) {
		enterUsername(username);
		enterPassword(password);
		clickLoginButton();
		log.info("Login attempted with username: {}", username);
	}

	public void loginWithValidCredentials() {
		login(config.getValidUsername(), config.getValidPassword());
	}

	// ── Getters ──────────────────────────────────────
	public String getErrorMessage() {
		return getText(errorMessage);
	}

	public boolean isErrorMessageDisplayed() {
		return isElementDisplayed(errorMessage);
	}

	public boolean isLoginPageDisplayed() {
		return isElementDisplayed(loginLogo);
	}

}
