package com.saucedemo.pages;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InventoryPage extends BasePage {

	private static final Logger log = LogManager.getLogger(InventoryPage.class);

	// ── Locators ─────────────────────────────────────
	@FindBy(css = ".title")
	private WebElement pageTitle;

	@FindBy(css = ".inventory_item")
	private List<WebElement> inventoryItems;

	@FindBy(css = ".inventory_item_name")
	private List<WebElement> itemNames;

	@FindBy(css = ".inventory_item_price")
	private List<WebElement> itemPrices;

	@FindBy(css = "[data-test='product-sort-container']")
	private WebElement sortDropdown;

	@FindBy(css = ".shopping_cart_link")
	private WebElement cartIcon;

	@FindBy(css = ".shopping_cart_badge")
	private WebElement cartBadge;

	@FindBy(id = "react-burger-menu-btn")
	private WebElement hamburgerMenu;

	@FindBy(id = "logout_sidebar_link")
	private WebElement logoutLink;

	@FindBy(id = "reset_sidebar_link")
	private WebElement resetAppLink;

	// ── Constructor ──────────────────────────────────
	public InventoryPage() {
		super();
	}

	// ── Page Verification ────────────────────────────
	public boolean isInventoryPageDisplayed() {
		return isElementDisplayed(pageTitle) && getPageTitle().equals("Swag Labs");
	}

	public String getPageHeaderTitle() {
		return getText(pageTitle);
	}

	// ── Product Actions ──────────────────────────────
	public List<String> getAllProductNames() {
		List<String> names = new ArrayList<>();
		for (WebElement name : itemNames) {
			names.add(getText(name));
		}
		log.info("Retrieved {} product names", names.size());
		return names;
	}

	public List<String> getAllProductPrices() {
		List<String> prices = new ArrayList<>();
		for (WebElement price : itemPrices) {
			prices.add(getText(price));
		}
		return prices;
	}

	public int getInventoryItemCount() {
		return inventoryItems.size();
	}

	public void addItemToCartByName(String productName) {
		for (WebElement item : inventoryItems) {
			String name = item.findElement(org.openqa.selenium.By.cssSelector(".inventory_item_name")).getText();

			if (name.equalsIgnoreCase(productName)) {
				WebElement addToCartBtn = item.findElement(org.openqa.selenium.By.cssSelector("button"));
				click(addToCartBtn);
				log.info("Added '{}' to cart", productName);
				return;
			}
		}
		throw new RuntimeException("Product not found: " + productName);
	}

	public void removeItemFromCartByName(String productName) {
		for (WebElement item : inventoryItems) {
			String name = item.findElement(org.openqa.selenium.By.cssSelector(".inventory_item_name")).getText();

			if (name.equalsIgnoreCase(productName)) {
				WebElement removeBtn = item.findElement(org.openqa.selenium.By.cssSelector("button"));
				click(removeBtn);
				log.info("Removed '{}' from cart", productName);
				return;
			}
		}
		throw new RuntimeException("Product not found: " + productName);
	}

	public void clickOnProductByName(String productName) {
		for (WebElement name : itemNames) {
			if (name.getText().equalsIgnoreCase(productName)) {
				click(name);
				log.info("Clicked on product: {}", productName);
				return;
			}
		}
		throw new RuntimeException("Product not found: " + productName);
	}

	// ── Sort Actions ─────────────────────────────────
	public void sortProductsBy(String sortOption) {
		selectByVisibleText(sortDropdown, sortOption);
		log.info("Sorted products by: {}", sortOption);
	}

	// ── Cart Actions ─────────────────────────────────
	public void clickCartIcon() {
		click(cartIcon);
		log.info("Clicked cart icon");
	}

	public int getCartItemCount() {
		try {
			return Integer.parseInt(getText(cartBadge));
		} catch (Exception e) {
			log.warn("Cart badge not visible — cart may be empty");
			return 0;
		}
	}

	public boolean isCartBadgeDisplayed() {
		return isElementDisplayed(cartBadge);
	}

	// ── Menu Actions ─────────────────────────────────
	public void openHamburgerMenu() {
		click(hamburgerMenu);
		log.info("Opened hamburger menu");
	}

	public void logout() {
		openHamburgerMenu();
		waitForElementClickable(logoutLink);
		click(logoutLink);
		log.info("Logged out successfully");
	}

	public void resetAppState() {
		openHamburgerMenu();
		click(resetAppLink);
		log.info("Reset app state");
	}

}
