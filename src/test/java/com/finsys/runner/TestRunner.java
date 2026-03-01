package com.finsys.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * TestRunner - Entry point for Cucumber test execution
 * Run all @smoke tagged scenarios from the features directory
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = { "com.finsys.steps",
                "com.finsys.utils" }, tags = "@smoke", plugin = {
                                "pretty",
                                "html:target/cucumber-reports/cucumber-html-report.html",
                                "json:target/cucumber-reports/cucumber.json",
                                "junit:target/cucumber-reports/cucumber.xml",
                                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
                }, monochrome = true, dryRun = false)
public class TestRunner {
        // This class is intentionally left empty.
        // Cucumber uses annotations to configure the test run.
}
