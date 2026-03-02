package com.finsys.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * DriverManager - Manages WebDriver lifecycle using ThreadLocal for parallel
 * execution support
 */
public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverManager() {
        // Utility class - prevent instantiation
    }

    public static void initDriver() {
        String browser = ConfigReader.getBrowser().toLowerCase();
        WebDriver driver;

        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "chrome":
            default:
                // Force WebDriverManager to specifically use the driver for Chrome 145
                WebDriverManager.chromedriver().browserVersion("145").setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--no-first-run");
                chromeOptions.addArguments("--disable-default-apps");
                chromeOptions.addArguments("--disable-extensions");
                // Disable Chrome's password manager and breach detection popup
                chromeOptions.addArguments("--password-store=basic");
                chromeOptions.setExperimentalOption("prefs", new java.util.HashMap<String, Object>() {
                    {
                        put("credentials_enable_service", false);
                        put("profile.password_manager_enabled", false);
                        put("profile.password_manager_leak_detection", false);
                        put("safebrowsing.enabled", false);
                    }
                });
                // Exclude switches that trigger Google sign-in and infobars
                chromeOptions.setExperimentalOption("excludeSwitches",
                        new java.util.ArrayList<>(java.util.Arrays.asList(
                                "enable-automation", "enable-logging")));
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
        // Remove explicit maximize() here, as ChromeOptions --start-maximized handles
        // it
        // and window().maximize() can cause TimeoutExceptions in Chrome 145+
        // driver.manage().window().maximize();
        driverThreadLocal.set(driver);
    }

    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new RuntimeException("WebDriver not initialized. Call initDriver() first.");
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}
