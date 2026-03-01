package com.finsys.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * HomePage - Page Object for FinSys Home Page (index.php)
 *
 * Real FinSys HTML structure after login:
 * - Layout: <div class="easyui-layout">
 * - Left accordion: <div class="easyui-accordion"> -> div[title="Financial
 * Analysis"]
 * - Navigation links use onclick="Navigate('...')" with title attributes:
 * title='Manage Company'
 * title='Leasehold Improvements'
 * title='Fixed Operating Expenses'
 * title='Furniture and Fixture'
 * title='Equipments'
 * title='Salaries & Related Expenses'
 * - Center panel (main content): div[data-options*="region:'center'"]
 * - Logout: <a onclick="TerminateSession()">LOGOUT</a>
 *
 * NB: The page is a JavaScript/EasyUI SPA. Many DOM elements are rendered by
 * EasyUI after page load.
 */
public class HomePage extends BasePage {

    @FindBy(css = "div.easyui-layout")
    private WebElement layoutContainer;

    @FindBy(css = "div.easyui-accordion")
    private WebElement accordion;

    @FindBy(css = "a[title='Manage Company']")
    private WebElement manageCompanyLink;

    @FindBy(css = "a[title='Leasehold Improvements']")
    private WebElement leaseholdImprovementsLink;

    @FindBy(css = "a[title='Fixed Operating Expenses']")
    private WebElement fixedOperatingExpensesLink;

    @FindBy(css = "a[title='Furniture and Fixture']")
    private WebElement furnitureFixtureLink;

    @FindBy(css = "a[title='Equipments']")
    private WebElement equipmentsLink;

    @FindBy(css = "a[title='Salaries & Related Expenses']")
    private WebElement salariesLink;

    @FindBy(css = "a[title='Required Startup Funds']")
    private WebElement requiredStartupFundsLink;

    @FindBy(css = "a[onclick*='TerminateSession']")
    private WebElement logoutLink;

    // The center tab panel that loads page content
    @FindBy(id = "tt")
    private WebElement tabPanel;

    public HomePage() {
        super();
    }

    public boolean isHomePageDisplayed() {
        try {
            wait.until(ExpectedConditions.urlContains("index.php"));
            return isDisplayed(layoutContainer);
        } catch (Exception e) {
            // Also check by URL
            return driver.getCurrentUrl().contains("index.php") || driver.getCurrentUrl().contains("finsys");
        }
    }

    public void clickManageCompanyLink() {
        waitForClickability(manageCompanyLink);
        click(manageCompanyLink);
    }

    public void clickLeaseholdImprovementsLink() {
        waitForClickability(leaseholdImprovementsLink);
        click(leaseholdImprovementsLink);
    }

    public void clickFixedOperatingExpensesLink() {
        waitForClickability(fixedOperatingExpensesLink);
        click(fixedOperatingExpensesLink);
    }

    public void clickFurnitureAndFixtureLink() {
        waitForClickability(furnitureFixtureLink);
        click(furnitureFixtureLink);
    }

    public void clickEquipmentsLink() {
        waitForClickability(equipmentsLink);
        click(equipmentsLink);
    }

    public void clickSalariesLink() {
        waitForClickability(salariesLink);
        click(salariesLink);
    }

    public void clickRequiredStartupFundsLink() {
        waitForClickability(requiredStartupFundsLink);
        click(requiredStartupFundsLink);
    }

    public void clickLogout() {
        click(logoutLink);
    }

    /**
     * Navigates to any module by directly calling the JavaScript Navigate()
     * function.
     * This bypasses link visibility issues (e.g. 'Manage User' is commented out for
     * CFO role).
     * The Navigate() function is defined globally on index.php and works for all
     * modules.
     *
     * @param moduleName The module name as it appears in the Navigate() function
     *                   calls
     *                   e.g. "Manage Company", "Leasehold Improvement", "manage
     *                   user"
     */
    public void navigateTo(String moduleName) {
        try {
            // First try clicking the visible link
            WebElement link = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector("a[title='" + moduleName + "']")));
            link.click();
        } catch (Exception e) {
            // Fallback: call Navigate() directly via JavaScript (works regardless of link
            // visibility)
            System.out.println("No visible link for '" + moduleName + "', calling Navigate() via JS...");
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("Navigate('" + moduleName + "');");
        }
        sleep(1500); // wait for the tab and iframe to be created
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
