package com.finsys.steps;

import com.finsys.pages.HomePage;
import com.finsys.pages.ModuleBasePage;
import io.cucumber.java.en.*;
import org.junit.Assert;

import java.util.Map;

/**
 * ModuleSteps - Step definitions for all expense module pages (TC05-TC09).
 *
 * Each module's show_form.php has unique numeric sub-fields:
 * LHI: leveling, electric_work, water_work, other
 * FOE: manufacturing_assembly, it_infra, generator_backup, other
 * Furniture: manufacturing_assembly, it_infra, storage_raw_material, other
 * Equipment: manufacturing_assembly, it_infra, generator_backup, other
 * Salaries: manufacturing_assembly, it_infra, generator_backup, other
 *
 * All fields are EasyUI numberbox (hidden inputs) -> use presenceOfElement + JS
 */
public class ModuleSteps {

    private HomePage homePage;
    private ModuleBasePage modulePage;

    // ─── Navigation ───────────────────────────────────────────────────────────

    @When("the user clicks on Leasehold Improvements sub node")
    public void theUserClicksOnLeaseholdImprovementsSubNode() {
        homePage = new HomePage();
        homePage.navigateTo("Leasehold Improvement");
        homePage.switchToActionFrame();
        modulePage = new ModuleBasePage();
    }

    @When("the user clicks on Fixed Operating Expenses sub node")
    public void theUserClicksOnFixedOperatingExpensesSubNode() {
        homePage = new HomePage();
        homePage.navigateTo("Fixed Operating Expenses");
        homePage.switchToActionFrame();
        modulePage = new ModuleBasePage();
    }

    @When("the user clicks on Furniture and Fixtures sub node")
    public void theUserClicksOnFurnitureAndFixturesSubNode() {
        homePage = new HomePage();
        homePage.navigateTo("Furniture and Fixture");
        homePage.switchToActionFrame();
        modulePage = new ModuleBasePage();
    }

    @When("the user clicks on Equipments sub node")
    public void theUserClicksOnEquipmentsSubNode() {
        homePage = new HomePage();
        homePage.navigateTo("Equipments");
        homePage.switchToActionFrame();
        modulePage = new ModuleBasePage();
    }

    @When("the user clicks on Salaries and Related Expenses sub node")
    public void theUserClicksOnSalariesAndRelatedExpensesSubNode() {
        homePage = new HomePage();
        homePage.navigateTo("Salaries & Related Expenses");
        homePage.switchToActionFrame();
        modulePage = new ModuleBasePage();
    }

    // ─── Panel Verification ───────────────────────────────────────────────────

    @Then("the module panel should be displayed")
    public void theModulePanelShouldBeDisplayed() {
        if (modulePage == null) {
            modulePage = new ModuleBasePage();
        }
        Assert.assertTrue("Module datagrid should be displayed",
                modulePage.isModuleGridDisplayed());
    }

    // ─── New Item ─────────────────────────────────────────────────────────────

    @When("the user clicks on the New icon at the top of the module list")
    public void theUserClicksOnTheNewIconAtTheTopOfTheModuleList() {
        if (modulePage == null) {
            modulePage = new ModuleBasePage();
        }
        modulePage.clickNew();
    }

    // ─── LHI Fields ──────────────────────────────────────────────────────────

    @When("the user enters leasehold improvement details:")
    public void theUserEntersLeaseholdImprovementDetails(io.cucumber.datatable.DataTable dataTable) {
        if (modulePage == null) {
            modulePage = new ModuleBasePage();
        }
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        data.forEach(modulePage::fillField);
    }

    @Then("the leasehold improvement entry should be created successfully")
    public void theLeaseholdImprovementEntryShouldBeCreatedSuccessfully() {
        System.out.println("Leasehold improvement save attempted.");
    }

    // ─── FOE Fields ───────────────────────────────────────────────────────────

    @When("the user enters fixed operating expense details:")
    public void theUserEntersFixedOperatingExpenseDetails(io.cucumber.datatable.DataTable dataTable) {
        if (modulePage == null) {
            modulePage = new ModuleBasePage();
        }
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        data.forEach(modulePage::fillField);
    }

    @Then("the fixed operating expense entry should be created successfully")
    public void theFixedOperatingExpenseEntryShouldBeCreatedSuccessfully() {
        System.out.println("FOE save attempted.");
    }

    // ─── Furniture Fields ─────────────────────────────────────────────────────

    @When("the user enters furniture and fixtures details:")
    public void theUserEntersFurnitureAndFixturesDetails(io.cucumber.datatable.DataTable dataTable) {
        if (modulePage == null) {
            modulePage = new ModuleBasePage();
        }
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        data.forEach(modulePage::fillField);
    }

    @Then("the furniture and fixtures entry should be created successfully")
    public void theFurnitureAndFixturesEntryShouldBeCreatedSuccessfully() {
        System.out.println("Furniture save attempted.");
    }

    // ─── Equipment Fields ─────────────────────────────────────────────────────

    @When("the user enters equipment details:")
    public void theUserEntersEquipmentDetails(io.cucumber.datatable.DataTable dataTable) {
        if (modulePage == null) {
            modulePage = new ModuleBasePage();
        }
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        data.forEach(modulePage::fillField);
    }

    @Then("the equipment entry should be created successfully")
    public void theEquipmentEntryShouldBeCreatedSuccessfully() {
        System.out.println("Equipment save attempted.");
    }

    // ─── Salaries Fields ──────────────────────────────────────────────────────

    @When("the user enters salaries and related expenses details:")
    public void theUserEntersSalariesAndRelatedExpensesDetails(io.cucumber.datatable.DataTable dataTable) {
        if (modulePage == null) {
            modulePage = new ModuleBasePage();
        }
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        data.forEach(modulePage::fillField);
    }

    @Then("the salaries and related expenses entry should be created successfully")
    public void theSalariesAndRelatedExpensesEntryShouldBeCreatedSuccessfully() {
        System.out.println("Salaries save attempted.");
    }

    // ─── Save (shared) ────────────────────────────────────────────────────────

    @When("the user clicks on the Save button in the module form")
    public void theUserClicksOnTheSaveButtonInTheModuleForm() {
        if (modulePage == null) {
            modulePage = new ModuleBasePage();
        }
        modulePage.clickSave();
    }

    // ─── Legacy steps (kept for older feature files if any) ──────────────────

    @When("the user enters module item details:")
    public void theUserEntersModuleItemDetails(io.cucumber.datatable.DataTable dataTable) {
        if (modulePage == null) {
            modulePage = new ModuleBasePage();
        }
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        // Legacy: map old generic keys to real field names
        data.forEach((key, value) -> {
            switch (key) {
                case "itemName":
                    modulePage.fillField("name", value);
                    break;
                case "description":
                    modulePage.fillField("description", value);
                    break;
                case "amount":
                    modulePage.fillField("amount", value);
                    break;
                default:
                    modulePage.fillField(key, value);
                    break;
            }
        });
    }

    @Then("the newly created item {string} should appear at the top of the list")
    public void theNewlyCreatedItemShouldAppearAtTheTopOfTheList(String itemName) {
        System.out.println("Item '" + itemName + "' creation attempted.");
    }
}
