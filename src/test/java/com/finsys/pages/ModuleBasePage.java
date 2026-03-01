package com.finsys.pages;

import com.finsys.utils.JsonReader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * ModuleBasePage - Generic Page Object for all expense-module pages in FinSys.
 *
 * All modules share the same EasyUI datagrid pattern inside
 * <iframe id="actionid">.
 * Module show_form.php fields (all EasyUI widgets - hidden, use JS):
 * - LHI: leveling, electric_work, water_work, other (all easyui-numberbox)
 * - FOE: check foe_show_form.php
 * - Furniture: check furniture show_form.php
 * - Equipment: check equipment show_form.php
 * - Salary: check salary show_form.php
 */
public class ModuleBasePage extends BasePage {

    @FindBy(id = "toolbar")
    private WebElement toolbar;

    @FindBy(id = "result")
    private WebElement resultDiv;

    public ModuleBasePage() {
        super();
    }

    /**
     * Returns true when the module toolbar is visible inside the iframe.
     * EasyUI hides the original #dg table — check #toolbar instead.
     */
    public boolean isModuleGridDisplayed() {
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

    /** Clicks the 'New' toolbar button. */
    public void clickNew() {
        WebElement newBtn = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath(JsonReader.getXpath("toolbar.btn.new"))));
        jsClick(newBtn);
        sleep(2000); // wait for EasyUI to expand the row and load show_form.php
    }

    /** Selects the first data row in the EasyUI datagrid. */
    public void selectFirstRow() {
        WebElement firstRow = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("tr[datagrid-row-index='0']")));
        jsClick(firstRow);
        sleep(1000);
    }

    /** Clicks the 'Destroy' toolbar button. */
    public void clickDestroy() {
        WebElement destroyBtn = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath(JsonReader.getXpath("toolbar.btn.destroy"))));
        jsClick(destroyBtn);
        sleep(500);
    }

    /** Clicks OK on EasyUI messager confirm dialog. */
    public void clickConfirmOk() {
        try {
            WebElement okBtn = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath(JsonReader.getXpath("messager.btn.ok"))));
            jsClick(okBtn);
            sleep(1500);
        } catch (Exception e) {
            try {
                driver.switchTo().alert().accept();
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Fills a form field inside the expanded row detail (show_form.php).
     *
     * EasyUI numberbox/textbox widgets clone the real input into a visible
     * sibling but keep the original hidden. We use JS querySelector to locate
     * the element by its name attribute (works for both hidden and visible
     * inputs) and then set the value via jQuery / JS so EasyUI picks it up.
     */
    public void fillField(String nameAttr, String value) {
        try {
            // First try JS querySelector — works for hidden EasyUI numberbox inputs
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
                    // Set value via JS and trigger change so EasyUI widgets update
                    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                            "arguments[0].value=arguments[1]; " +
                                    "$(arguments[0]).val(arguments[1]).trigger('change').trigger('blur');",
                            field, value);
                }
                System.out.println("fillField [" + nameAttr + "=" + value + "]: JS querySelector");
            } else {
                // Fallback: Selenium wait (for non-EasyUI / visible fields)
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
            String allNames = "";
            try {
                Object namesObj = ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                        "return Array.from(document.querySelectorAll('[name]')).map(el => el.getAttribute('name')).join(', ');");
                allNames = namesObj != null ? namesObj.toString() : "none";
            } catch (Exception ex) {
                allNames = "error fetching names";
            }
            System.out.println("fillField: could not find field [name='" + nameAttr + "'].\n Available named elements: "
                    + allNames + "\n Exception: " + e.getMessage());
        }
    }

    /** Clicks the Save button inside the inline form (onclick='save1(this)'). */
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

    /** Returns true if given text exists anywhere in the page source. */
    public boolean isTextPresentInGrid(String text) {
        try {
            sleep(1000);
            return driver.getPageSource().contains(text);
        } catch (Exception e) {
            return false;
        }
    }

    public String getResultMessage() {
        try {
            return getText(resultDiv);
        } catch (Exception e) {
            return "";
        }
    }
}
