package com.saucedemo.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;


/* What is TestRunner?
 * Think of TestRunner as the control center of your framework.
 * It answers three questions:
 * Where are my feature files?    → features
 * Where are my step definitions? → glue
 * Which tests should I run?      → tags
 */

@CucumberOptions(
        // ── Feature Files Location ───────────────────
        features = "src/test/resources/features",

        // ── Step Definitions + Hooks Location ────────
        glue = {
                "com.saucedemo.stepdefs",
                "com.saucedemo.hooks"
        },

        // ── Tags — controls which scenarios run ───────
        // Use "not @ignore" to skip ignored tests
        tags = "not @ignore",

        // ── Plugins — Reporting ───────────────────────
        plugin = {
                "pretty",                          // readable console output
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },

        // ── Show monochrome output (no color codes) ───
        monochrome = true,

        // ── Publish cucumber report online (optional) ─
        publish = false
)
public class TestRunner extends AbstractTestNGCucumberTests {

    // ── Parallel Execution ───────────────────────────
    // dataProvider runs each scenario as separate TestNG test
    // parallel = true enables running scenarios in parallel
    @Override
    @DataProvider(parallel = false) // change to true for parallel execution
    public Object[][] scenarios() {
        return super.scenarios();
    }
}