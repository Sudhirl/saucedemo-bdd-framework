package com.saucedemo.pages;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.saucedemo.config.ConfigReader;
import com.saucedemo.utils.DriverFactory;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected JavascriptExecutor js;
    protected ConfigReader config;

    private static final Logger log = LogManager.getLogger(BasePage.class);

    // ── Constructor ──────────────────────────────────
    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.config = ConfigReader.getInstance();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(config.getExplicitWait()));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // ══════════════════════════════════════════════════
    // ── NAVIGATION ────────────────────────────────────
    // ══════════════════════════════════════════════════

    public void navigateTo(String url) {
        log.info("Navigating to URL: {}", url);
        driver.get(url);
    }

    public String getPageTitle() {
        String title = driver.getTitle();
        log.info("Page title: {}", title);
        return title;
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // ══════════════════════════════════════════════════
    // ── WAIT METHODS ──────────────────────────────────
    // ══════════════════════════════════════════════════

    public WebElement waitForElementVisible(By locator) {
        log.info("Waiting for element to be visible: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementClickable(By locator) {
        log.info("Waiting for element to be clickable: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForElementVisible(WebElement element) {
        log.info("Waiting for WebElement to be visible");
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForElementClickable(WebElement element) {
        log.info("Waiting for WebElement to be clickable");
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public boolean waitForElementInvisible(By locator) {
        log.info("Waiting for element to be invisible: {}", locator);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // ══════════════════════════════════════════════════
    // ── CLICK METHODS ─────────────────────────────────
    // ══════════════════════════════════════════════════

    public void click(By locator) {
        try {
            WebElement element = waitForElementClickable(locator);
            element.click();
            log.info("Clicked on element: {}", locator);
        } catch (TimeoutException e) {
            log.error("Element not clickable: {}", locator);
            throw new RuntimeException("Element not clickable: " + locator, e);
        }
    }

    public void click(WebElement element) {
        try {
            waitForElementClickable(element).click();
            log.info("Clicked on WebElement");
        } catch (TimeoutException e) {
            log.error("WebElement not clickable");
            throw new RuntimeException("WebElement not clickable", e);
        }
    }

    public void clickWithJS(WebElement element) {
        log.info("Clicking element using JavaScript");
        js.executeScript("arguments[0].click();", element);
    }

    // ══════════════════════════════════════════════════
    // ── TYPE / INPUT METHODS ──────────────────────────
    // ══════════════════════════════════════════════════

    public void type(By locator, String text) {
        try {
            WebElement element = waitForElementVisible(locator);
            element.clear();
            element.sendKeys(text);
            log.info("Typed '{}' into element: {}", text, locator);
        } catch (TimeoutException e) {
            log.error("Element not visible to type into: {}", locator);
            throw new RuntimeException("Element not visible: " + locator, e);
        }
    }

    public void type(WebElement element, String text) {
        waitForElementVisible(element).clear();
        element.sendKeys(text);
        log.info("Typed '{}' into WebElement", text);
    }

    public void clearAndType(WebElement element, String text) {
        waitForElementVisible(element);
        element.clear();
        element.sendKeys(text);
        log.info("Cleared and typed '{}' into WebElement", text);
    }

    // ══════════════════════════════════════════════════
    // ── GET TEXT / ATTRIBUTE ──────────────────────────
    // ══════════════════════════════════════════════════

    public String getText(By locator) {
        String text = waitForElementVisible(locator).getText();
        log.info("Got text '{}' from element: {}", text, locator);
        return text;
    }

    public String getText(WebElement element) {
        String text = waitForElementVisible(element).getText();
        log.info("Got text: {}", text);
        return text;
    }

    public String getAttribute(WebElement element, String attribute) {
        String value = element.getAttribute(attribute);
        log.info("Got attribute '{}' = '{}'", attribute, value);
        return value;
    }

    // ══════════════════════════════════════════════════
    // ── ELEMENT STATE ─────────────────────────────────
    // ══════════════════════════════════════════════════

    public boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            log.warn("Element not found: {}", locator);
            return false;
        }
    }

    public boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            log.warn("WebElement not found on page");
            return false;
        }
    }

    public boolean isElementEnabled(WebElement element) {
        return element.isEnabled();
    }

    public boolean isElementSelected(WebElement element) {
        return element.isSelected();
    }

    // ══════════════════════════════════════════════════
    // ── DROPDOWN ──────────────────────────────────────
    // ══════════════════════════════════════════════════

    public void selectByVisibleText(WebElement element, String text) {
        Select select = new Select(element);
        select.selectByVisibleText(text);
        log.info("Selected '{}' from dropdown", text);
    }

    public void selectByValue(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByValue(value);
        log.info("Selected value '{}' from dropdown", value);
    }

    public void selectByIndex(WebElement element, int index) {
        Select select = new Select(element);
        select.selectByIndex(index);
        log.info("Selected index '{}' from dropdown", index);
    }

    public String getSelectedOption(WebElement element) {
        Select select = new Select(element);
        return select.getFirstSelectedOption().getText();
    }

    // ══════════════════════════════════════════════════
    // ── JAVASCRIPT METHODS ────────────────────────────
    // ══════════════════════════════════════════════════

    public void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        log.info("Scrolled to element");
    }

    public void scrollToTop() {
        js.executeScript("window.scrollTo(0, 0);");
        log.info("Scrolled to top of page");
    }

    public void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        log.info("Scrolled to bottom of page");
    }

    public void highlightElement(WebElement element) {
        js.executeScript("arguments[0].style.border='3px solid red'", element);
    }

    // ══════════════════════════════════════════════════
    // ── SCREENSHOT ────────────────────────────────────
    // ══════════════════════════════════════════════════

    public String takeScreenshot(String testName) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String fileName = testName + "_" + timestamp + ".png";
        String folderPath = "screenshots/";
        String filePath = folderPath + fileName;

        try {
            // Create screenshots folder if it doesn't exist
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File(filePath);
            Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            log.info("Screenshot saved: {}", filePath);

        } catch (IOException e) {
            log.error("Failed to take screenshot: {}", e.getMessage());
        }

        return filePath;
    }

    public byte[] takeScreenshotAsBytes() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    // ══════════════════════════════════════════════════
    // ── ACTIONS (Mouse Interactions) ──────────────────
    // ══════════════════════════════════════════════════

    public void hoverOverElement(WebElement element) {
        actions.moveToElement(element).perform();
        log.info("Hovered over element");
    }

    public void rightClick(WebElement element) {
        actions.contextClick(element).perform();
        log.info("Right clicked on element");
    }

    public void doubleClick(WebElement element) {
        actions.doubleClick(element).perform();
        log.info("Double clicked on element");
    }

    public void dragAndDrop(WebElement source, WebElement target) {
        actions.dragAndDrop(source, target).perform();
        log.info("Drag and drop performed");
    }
}