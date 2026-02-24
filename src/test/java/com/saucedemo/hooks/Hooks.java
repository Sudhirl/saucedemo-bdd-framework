package com.saucedemo.hooks;

import java.io.ByteArrayInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.saucedemo.config.ConfigReader;
import com.saucedemo.utils.DriverFactory;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

public class Hooks {

	private static final Logger log = LogManager.getLogger(Hooks.class);
	private ConfigReader config = ConfigReader.getInstance();

	// ── Runs BEFORE every scenario ───────────────────
	@Before(order = 1)
	public void setUp(Scenario scenario) {
		log.info("════════════════════════════════════════");
		log.info("Starting Scenario: {}", scenario.getName());
		log.info("Tags: {}", scenario.getSourceTagNames());
		log.info("════════════════════════════════════════");

		// Initialize browser
		DriverFactory.initDriver();
		log.info("Browser initialized for scenario: {}", scenario.getName());
	}

	// ── Runs AFTER every scenario ────────────────────
	@After(order = 1)
	public void tearDown(Scenario scenario) {
		log.info("════════════════════════════════════════");
		log.info("Finishing Scenario: {}", scenario.getName());
		log.info("Status: {}", scenario.getStatus());

		try {
			// Always take screenshot at end of scenario
			if (scenario.isFailed()) {
				log.error("Scenario FAILED: {}", scenario.getName());
				attachScreenshotToAllure(scenario, "Final Failure Screenshot");
			} else {
				log.info("Scenario PASSED: {}", scenario.getName());
				if (config.isScreenshotOnFailure()) {
					// only on failure — skip for passed scenarios
					log.info("Screenshot skipped — scenario passed");
				}
			}
		} catch (Exception e) {
			log.error("Error in tearDown: {}", e.getMessage());
		} finally {
			// Always quit driver — even if screenshot fails
			DriverFactory.quitDriver();
			log.info("Browser closed for scenario: {}", scenario.getName());
			log.info("════════════════════════════════════════");
		}
	}

	// ── Runs AFTER every step ────────────────────────
	@AfterStep
	public void afterStep(Scenario scenario) {
		// Attach screenshot to Allure after every FAILED step
		if (scenario.isFailed()) {
			attachScreenshotToAllure(scenario, "Failed Step Screenshot");
		}
	}

	// ── Helper — Attach Screenshot to Allure Report ──
	private void attachScreenshotToAllure(Scenario scenario, String name) {
		try {
			byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
			Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
			log.info("Screenshot attached to Allure: {}", name);
		} catch (Exception e) {
			log.error("Failed to attach screenshot: {}", e.getMessage());
		}
	}
}