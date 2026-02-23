package com.saucedemo.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.saucedemo.config.ConfigReader;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	private static final Logger log = LogManager.getLogger(DriverFactory.class);

//ThreadLocal ensures each thread gets its OWN WebDriver instance
	// This is what makes parallel execution safe
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	private static ConfigReader config = ConfigReader.getInstance();

//── Private constructor — prevent instantiation ──
	private DriverFactory() {
	}

//── Get current thread's WebDriver ──────────────
	public static WebDriver getDriver() {
		return driver.get();
	}

	// ── Initialize WebDriver based on browser config ─

	public static void initDriver() {
		 String browser = config.getBrowser().toLowerCase().trim();
	        boolean isHeadless = config.isHeadless();

	        log.info("Initializing browser: {} | Headless: {}", browser, isHeadless);

	        switch (browser) {
	            case "chrome":
	                driver.set(initChrome(isHeadless));
	                break;
	            case "firefox":
	                driver.set(initFirefox(isHeadless));
	                break;
	            case "edge":
	                driver.set(initEdge(isHeadless));
	                break;
	            default:
	                log.error("Browser '{}' is not supported. Defaulting to Chrome.", browser);
	                driver.set(initChrome(isHeadless));
	        }
		
	}

	// ──────────────── Chrome Setup ────────────────
	private static WebDriver initChrome(boolean headless) {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();

		options.addArguments("--disable-notifications");
		options.addArguments("--disable-popup-blocking");
		options.addArguments("--disable-infobars");
		options.addArguments("--start-maximized");
		options.addArguments("--remote-allow-origins=*");

		if (headless) {
			options.addArguments("--headless=new");
			options.addArguments("--window-size=1920,1080");
			log.info("Running Chrome in headless mode");
		}

		return new ChromeDriver(options);
	}

	// ──────────────── Firefox Setup ────────────────
	private static WebDriver initFirefox(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();

        if (headless) {
            options.addArguments("--headless");
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
            log.info("Running Firefox in headless mode");
        }

        return new FirefoxDriver(options);
    }
	
	// ──────────────── Edge Setup ────────────────
	private static WebDriver initEdge(boolean headless) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();

        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            log.info("Running Edge in headless mode");
        }

        return new EdgeDriver(options);
    }
	
	// ── Quit and Clean Up Driver ─────────────────────
    public static void quitDriver() {
        if (getDriver() != null) {
            log.info("Quitting browser...");
            getDriver().quit();
            driver.remove(); // ⚠️ Critical — removes thread's driver to prevent memory leak
            log.info("Browser closed and driver removed from ThreadLocal.");
        }
    }
	
	
	
}
