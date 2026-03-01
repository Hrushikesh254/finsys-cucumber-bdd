package com.finsys.utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hooks - Cucumber lifecycle hooks for setup and teardown
 */
public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        logger.info("========================================");
        logger.info("Starting Scenario: " + scenario.getName());
        logger.info("Tags: " + scenario.getSourceTagNames());
        logger.info("========================================");
        DriverManager.initDriver();
        WebDriver driver = DriverManager.getDriver();
        driver.get(ConfigReader.getBaseUrl());
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();

        if (scenario.isFailed()) {
            logger.error("Scenario FAILED: " + scenario.getName());
            // Take screenshot on failure
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fileName = "target/screenshots/FAILED_" + scenario.getName().replaceAll("[^a-zA-Z0-9]", "_")
                        + "_" + timestamp + ".png";
                File destFile = new File(fileName);
                destFile.getParentFile().mkdirs();
                FileUtils.copyFile(screenshot, destFile);
                logger.info("Screenshot saved: " + fileName);
                // Attach screenshot to Cucumber report
                byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshotBytes, "image/png", "Screenshot on Failure");
            } catch (IOException e) {
                logger.error("Failed to save screenshot: " + e.getMessage());
            }
        } else {
            logger.info("Scenario PASSED: " + scenario.getName());
        }

        logger.info("========================================");
        DriverManager.quitDriver();
    }
}
