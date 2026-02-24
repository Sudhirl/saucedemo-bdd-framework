package com.saucedemo.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage {

	private static final Logger log = LogManager.getLogger(CheckoutPage.class);

	// ── Step One Locators ─────────────────────────────
	@FindBy(css = ".title")
	private WebElement pageTitle;

	@FindBy(id = "first-name")
	private WebElement firstNameField;

	@FindBy(id = "last-name")
	private WebElement lastNameField;

	@FindBy(id = "postal-code")
	private WebElement postalCodeField;

	@FindBy(id = "continue")
	private WebElement continueButton;

	@FindBy(id = "cancel")
	private WebElement cancelButton;

	@FindBy(css = "[data-test='error']")
	private WebElement errorMessage;

	// ── Step Two Locators ─────────────────────────────
	@FindBy(css = ".summary_subtotal_label")
	private WebElement subtotalLabel;

	@FindBy(css = ".summary_tax_label")
	private WebElement taxLabel;

	@FindBy(css = ".summary_total_label")
	private WebElement totalLabel;

	@FindBy(id = "finish")
	private WebElement finishButton;

	// ── Confirmation Locators ─────────────────────────
	@FindBy(css = ".complete-header")
	private WebElement confirmationHeader;

	@FindBy(css = ".complete-text")
	private WebElement confirmationText;

	@FindBy(id = "back-to-products")
	private WebElement backToProductsButton;

	// ── Constructor ──────────────────────────────────
	public CheckoutPage() {
		super();
	}

	// ── Page Verification ────────────────────────────
	public String getPageHeaderTitle() {
		return getText(pageTitle);
	}

	public boolean isCheckoutStepOneDisplayed() {
		return isElementDisplayed(firstNameField);
	}

	public boolean isCheckoutStepTwoDisplayed() {
		return isElementDisplayed(finishButton);
	}

	public boolean isOrderConfirmed() {
		return isElementDisplayed(confirmationHeader);
	}

	// ── Step One Actions ─────────────────────────────
	public void enterFirstName(String firstName) {
		type(firstNameField, firstName);
		log.info("Entered first name: {}", firstName);
	}

	public void enterLastName(String lastName) {
		type(lastNameField, lastName);
		log.info("Entered last name: {}", lastName);
	}

	public void enterPostalCode(String postalCode) {
		type(postalCodeField, postalCode);
		log.info("Entered postal code: {}", postalCode);
	}

	public void fillShippingDetails(String firstName, String lastName, String postalCode) {
		enterFirstName(firstName);
		enterLastName(lastName);
		enterPostalCode(postalCode);
		log.info("Filled shipping details");
	}

	public void clickContinue() {
		click(continueButton);
		log.info("Clicked continue button");
	}

	public void clickCancel() {
		click(cancelButton);
		log.info("Clicked cancel button");
	}

	// ── Step Two Actions ─────────────────────────────
	public String getSubtotal() {
		return getText(subtotalLabel);
	}

	public String getTax() {
		return getText(taxLabel);
	}

	public String getTotal() {
		return getText(totalLabel);
	}

	public void clickFinish() {
		click(finishButton);
		log.info("Clicked finish button");
	}

	// ── Confirmation Actions ─────────────────────────
	public String getConfirmationHeader() {
		return getText(confirmationHeader);
	}

	public String getConfirmationText() {
		return getText(confirmationText);
	}

	public void clickBackToProducts() {
		click(backToProductsButton);
		log.info("Clicked back to products");
	}

	// ── Error Handling ───────────────────────────────
	public boolean isErrorMessageDisplayed() {
		return isElementDisplayed(errorMessage);
	}

	public String getErrorMessage() {
		return getText(errorMessage);
	}
}