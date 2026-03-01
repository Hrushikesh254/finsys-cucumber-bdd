package com.finsys.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * LoginPage - Page Object for FinSys Login Page
 *
 * Key facts about the real FinSys HTML:
 * - Username: <input id="username" class="easyui-textbox"> -- EasyUI widget
 * wraps it in a span,
 * so we must use JavaScript to set value.
 * - Password: <input id="password" class="easyui-textbox" type="password"> --
 * same EasyUI issue.
 * - Login button:
 * <a class="easyui-linkbutton" onclick="return ValidateLogin()"> -- JS click
 * needed.
 * - Container: <div id="mainwindow">
 * - Error display: <div id="error">
 */
public class LoginPage extends BasePage {

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(css = "a.easyui-linkbutton")
    private WebElement loginButton;

    @FindBy(id = "mainwindow")
    private WebElement mainWindow;

    @FindBy(id = "error")
    private WebElement errorDiv;

    public LoginPage() {
        super();
    }

    /**
     * Checks if the login page is displayed by looking for the main window
     * container.
     */
    public boolean isLoginPageDisplayed() {
        return isDisplayed(mainWindow);
    }

    /**
     * Types into the EasyUI-wrapped username field using JavaScript.
     * EasyUI hides the real <input> with display:none, so we MUST use
     * presenceOfElementLocated (not visibilityOf) + JS executor.
     */
    public void enterUsername(String username) {
        // Use presenceOfElementLocated — EasyUI hides the input, visibility wait always
        // fails
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1];", el, username);
    }

    /**
     * Types into the EasyUI-wrapped password field using JavaScript.
     * Same EasyUI hidden-input issue as username.
     */
    public void enterPassword(String password) {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1];", el, password);
    }

    /**
     * Clicks the EasyUI linkbutton that triggers ValidateLogin().
     * Use JS click since EasyUI may intercept normal click.
     */
    public void clickLoginButton() {
        // Find the <a> element containing the Login text - use presence not visibility
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("a.easyui-linkbutton")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", el);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isErrorDisplayed() {
        try {
            String errText = errorDiv.getText();
            return errText != null && !errText.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorText() {
        return errorDiv.getText();
    }
}
