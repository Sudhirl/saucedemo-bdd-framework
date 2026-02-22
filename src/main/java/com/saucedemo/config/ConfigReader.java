package com.saucedemo.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigReader {

	private static Properties properties;
	private static final Logger log = LogManager.getLogger(ConfigReader.class);
	private static ConfigReader instance;

	// Private constructor — Singleton pattern
	private ConfigReader() {
		loadProperties();
	}

	// Thread-safe singleton
	// Double-Checked Locking Singleton

	public static ConfigReader getInstance() {
		if (instance == null) {
			synchronized (ConfigReader.class) {
				if (instance == null) {
					instance = new ConfigReader();
				}
			}
			/*
			 * Meaning of synchronized (ConfigReader.class) => Imagine two threads (Thread A
			 * and Thread B) both reach at the first if at the exact same time Both see
			 * instance == null and both try to create a new ConfigReader synchronized puts
			 * a lock on the class — only one thread can enter this block at a time Thread A
			 * gets in, Thread B waits outside
			 */
		}

		return instance;
	}

	private void loadProperties() {
		try {
			String configPath = ".//src//test//resources//config.properties";
			FileInputStream fis = new FileInputStream(configPath);
			properties = new Properties();
			properties.load(fis);
			log.info("config.properties loaded successfully");

		} catch (IOException e) {
			log.error("Failed to load config.properties" + e.getMessage());
			throw new RuntimeException("config.properties not found. Check the path");
		}
	}

// ─────────────────────────── Generic Getter ───────────────────────────

	public String getProperty(String key) {
		String value = properties.getProperty(key);
		if (value == null) {
			log.warn("Property '{}' not found in config.properties", key);
			throw new RuntimeException("Property '" + key + "' not found in config.properties");
		}
		return value.trim();
	}

// ─────────────────────────── Specific getters ───────────────────────────
	public String getBrowser() {
		return getProperty("browser");
	}

	public String getUrl() {
		return getProperty("url");
	}

	public String getValidUsername() {
		return getProperty("valid.username");
	}

	public String getValidPassword() {
		return getProperty("valid.password");
	}

	public String getLockedUsername() {
		return getProperty("locked.username");
	}

	public String getLockedPassword() {
		return getProperty("locked.password");
	}

	public String getPerformanceUsername() {
		return getProperty("performance.username");
	}

	public String getPerformancePassword() {
		return getProperty("performance.password");
	}

	public int getImplicitWait() {
		return Integer.parseInt(getProperty("implicit.wait"));
	}

	public int getExplicitWait() {
		return Integer.parseInt(getProperty("explicit.wait"));
	}

	public int getPageLoadTimeout() {
		return Integer.parseInt(getProperty("page.load.timeout"));
	}

	public boolean isHeadless() {
		return Boolean.parseBoolean(getProperty("headless"));
	}

	public boolean isScreenshotOnFailure() {
		return Boolean.parseBoolean(getProperty("screenshot.on.failure"));
	}

}
