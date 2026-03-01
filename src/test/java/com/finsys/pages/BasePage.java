package com.finsys.pages;

import com.finsys.utils.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BasePage - Parent class for all Page Objects providing common WebDriver
 * utilities.
 *
 * IMPORTANT: Many pages in FinSys load inside an <iframe id="actionid">.
 * Use switchToActionFrame() before interacting with those pages,
 * and switchToDefaultContent() when returning to the main page.
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // ---- Frame Switching ----

    /**
     * Switches Selenium context into the <iframe id="actionid"> used by all module
     * pages.
     */
    public void switchToActionFrame() {
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("actionid")));
        } catch (Exception e) {
            try {
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe[src]")));
            } catch (Exception ex) {
                driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
            }
        }
    }

    /** Returns Selenium context back to the top-level page. */
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    // ---- Safe Interaction Helpers ----

    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    protected void jsClick(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void type(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    protected void jsType(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", element, text);
    }

    protected void selectByVisibleText(WebElement dropdown, String text) {
        wait.until(ExpectedConditions.visibilityOf(dropdown));
        new Select(dropdown).selectByVisibleText(text);
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText().trim();
    }

    protected void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForClickability(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
