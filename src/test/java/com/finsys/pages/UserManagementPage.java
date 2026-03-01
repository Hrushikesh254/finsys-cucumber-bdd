package com.finsys.pages;

import com.finsys.utils.JsonReader;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;

/*
 * UserManagementPage - Page Object for FinSys User Management module.
 * Loaded inside <iframe id="actionid"> via Navigate('manage user').
 * Same EasyUI datagrid pattern — all inputs are hidden by EasyUI, use JS.
 */
public class UserManagementPage extends BasePage {

    @FindBy(id = "toolbar")
    private WebElement toolbar;

    @FindBy(id = "result")
    private WebElement resultDiv;

    public UserManagementPage() {
        super();
    }

    /**
     * Returns true when the User Management toolbar is visible.
     * EasyUI hides the original #dg table — check #toolbar instead.
     */
    public boolean isUserPanelDisplayed() {
        try {
            sleep(2000);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("toolbar")));
            return isDisplayed(toolbar);
        } catch (Exception e) {
            try {
                driver.findElement(By.cssSelector(".datagrid-wrap, .panel-body"));
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
    }

    public void clickNew() {
        WebElement newBtn = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath(JsonReader.getXpath("toolbar.btn.new"))));
        jsClick(newBtn);
        sleep(2000);
    }

    public void clickSave() {
        try {
            WebElement saveBtn = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath(JsonReader.getXpath("btn.save1"))));
            jsClick(saveBtn);
        } catch (Exception e) {
            WebElement saveBtn = driver.findElement(
                    By.xpath(JsonReader.getXpath("btn.save")));
            jsClick(saveBtn);
        }
        sleep(2500);
    }

    public void fillField(String nameAttr, String value) {
        try {
            // Use JS querySelector to find hidden EasyUI inputs by name attribute
            Object result = ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                    "return document.querySelector('[name=\"" + nameAttr + "\"]');");
            if (result instanceof WebElement) {
                WebElement field = (WebElement) result;
                String tag = field.getTagName().toLowerCase();
                if (tag.equals("select")) {
                    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                            "var sel=arguments[0]; for(var i=0;i<sel.options.length;i++){" +
                                    "if(sel.options[i].text===arguments[1]||sel.options[i].value===arguments[1])" +
                                    "{sel.selectedIndex=i; $(sel).trigger('change'); break;}}",
                            field, value);
                } else {
                    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                            "arguments[0].value=arguments[1]; " +
                                    "$(arguments[0]).val(arguments[1]).trigger('change').trigger('blur');",
                            field, value);
                }
                System.out.println("fillField [" + nameAttr + "=" + value + "]: JS querySelector");
            } else {
                // Fallback: Selenium wait for visible fields
                WebElement field = wait.until(
                        ExpectedConditions.presenceOfElementLocated(By.name(nameAttr)));
                String tag = field.getTagName().toLowerCase();
                if (tag.equals("select")) {
                    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                            "var sel=arguments[0]; for(var i=0;i<sel.options.length;i++){" +
                                    "if(sel.options[i].text===arguments[1]||sel.options[i].value===arguments[1])" +
                                    "{sel.selectedIndex=i;break;}}",
                            field, value);
                } else {
                    jsType(field, value);
                    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                            "$(arguments[0]).val(arguments[1]).trigger('change');", field, value);
                }
                System.out.println("fillField [" + nameAttr + "=" + value + "]: Selenium fallback");
            }
        } catch (Exception e) {
            System.out.println("fillField: could not find [name='" + nameAttr + "']: " + e.getMessage());
        }
    }

    public boolean isTextPresentInGrid(String text) {
        try {
            sleep(1000);
            return driver.getPageSource().contains(text);
        } catch (Exception e) {
            return false;
        }
    }
}
