package com.saucedemo.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage {

	private static final Logger log = LogManager.getLogger(CartPage.class);

	// ── Locators ─────────────────────────────────────
	@FindBy(css = ".title")
	private WebElement pageTitle;

	@FindBy(css = ".cart_item")
	private List<WebElement> cartItems;

	@FindBy(css = ".inventory_item_name")
	private List<WebElement> cartItemNames;

	@FindBy(css = ".inventory_item_price")
	private List<WebElement> cartItemPrices;

	@FindBy(id = "checkout")
	private WebElement checkoutButton;

	@FindBy(id = "continue-shopping")
	private WebElement continueShoppingButton;

	// ── Constructor ──────────────────────────────────
	public CartPage() {
		super();
	}

	// ── Page Verification ────────────────────────────
	public boolean isCartPageDisplayed() {
		return isElementDisplayed(pageTitle) && getText(pageTitle).equals("Your Cart");
	}

	public String getPageHeaderTitle() {
		return getText(pageTitle);
	}

	// ── Cart Item Actions ────────────────────────────
	public int getCartItemCount() {
		return cartItems.size();
	}

	public List<String> getCartItemNames() {
		List<String> names = new ArrayList<>();
		for (WebElement name : cartItemNames) {
			names.add(getText(name));
		}
		log.info("Cart contains {} items", names.size());
		return names;
	}

	public List<String> getCartItemPrices() {
		List<String> prices = new ArrayList<>();
		for (WebElement price : cartItemPrices) {
			prices.add(getText(price));
		}
		return prices;
	}

	public boolean isItemPresentInCart(String productName) {
		List<String> names = getCartItemNames();
		boolean found = names.stream().anyMatch(name -> name.equalsIgnoreCase(productName));
		log.info("Product '{}' present in cart: {}", productName, found);
		return found;
	}

	public void removeItemFromCart(String productName) {
		for (WebElement item : cartItems) {
			String name = item.findElement(By.cssSelector(".inventory_item_name")).getText();

			if (name.equalsIgnoreCase(productName)) {
				WebElement removeBtn = item.findElement(By.cssSelector("button"));
				click(removeBtn);
				log.info("Removed '{}' from cart", productName);
				return;
			}
		}
		throw new RuntimeException("Product not found in cart: " + productName);
	}

	// ── Navigation Actions ───────────────────────────
	public void clickCheckout() {
		click(checkoutButton);
		log.info("Clicked checkout button");
	}

	public void clickContinueShopping() {
		click(continueShoppingButton);
		log.info("Clicked continue shopping");
	}

}
