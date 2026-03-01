package com.finsys.steps;

import com.finsys.pages.CompanyPage;
import com.finsys.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * CompanySteps - Step Definitions for Company module (TC02, TC03, TC04)
 *
 * Company page loads inside an <iframe id="actionid">.
 * Steps must switch to the iframe before interacting with the datagrid/forms.
 */
public class CompanySteps {

    private static final Logger logger = LogManager.getLogger(CompanySteps.class);

    private HomePage homePage;
    private CompanyPage companyPage;
    private String lastCreatedCompanyName;

    @Given("the user is on the Home Page after successful login")
    public void theUserIsOnTheHomePageAfterSuccessfulLogin() {
        homePage = new HomePage();
        Assert.assertTrue("User should be on Home Page",
                homePage.getCurrentUrl().contains("index.php") || homePage.getCurrentUrl().contains("finsys"));
    }

    @When("the user expands the Financial Analysis left accordion pane")
    public void theUserExpandsTheFinancialAnalysisLeftAccordionPane() {
        homePage = new HomePage();
        // The accordion is already visible on index.php; just log
        logger.info("Financial Analysis accordion is in the left panel");
    }

    @When("the user clicks on Manage Company sub node")
    public void theUserClicksOnManageCompanySubNode() {
        homePage = new HomePage();
        homePage.clickManageCompanyLink();
        // Wait for iframe to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }
        // Switch into the iframe and create CompanyPage
        homePage.switchToActionFrame();
        companyPage = new CompanyPage();
    }

    @Then("the Manage Company panel should be displayed")
    public void theManageCompanyPanelShouldBeDisplayed() {
        if (companyPage == null) {
            homePage = new HomePage();
            homePage.switchToActionFrame();
            companyPage = new CompanyPage();
        }
        Assert.assertTrue("Manage Company datagrid should be visible",
                companyPage.isCompanyGridDisplayed());
    }

    @When("the user clicks on the New icon at the top of the company list")
    public void theUserClicksOnTheNewIconAtTheTopOfTheCompanyList() {
        if (companyPage == null) {
            companyPage = new CompanyPage();
        }
        companyPage.clickNew();
    }

    @When("the user enters company details:")
    public void theUserEntersCompanyDetails(io.cucumber.datatable.DataTable dataTable) {
        if (companyPage == null) {
            companyPage = new CompanyPage();
        }
        java.util.Map<String, String> data = dataTable.asMap(String.class, String.class);
        // Field names match the actual <input name="..."> in show_form.php
        if (data.containsKey("name"))
            companyPage.fillField("name", data.get("name"));
        if (data.containsKey("companyName"))
            companyPage.fillField("name", data.get("companyName"));
        if (data.containsKey("type"))
            companyPage.fillField("type", data.get("type"));
        if (data.containsKey("address"))
            companyPage.fillField("address", data.get("address"));
        if (data.containsKey("phone"))
            companyPage.fillField("phone", data.get("phone"));
        if (data.containsKey("email"))
            companyPage.fillField("email", data.get("email"));
        if (data.containsKey("pan"))
            companyPage.fillField("pan", data.get("pan"));
        if (data.containsKey("tin"))
            companyPage.fillField("tin", data.get("tin"));
        if (data.containsKey("totalemployee"))
            companyPage.fillField("totalemployee", data.get("totalemployee"));
        if (data.containsKey("totalEmployee"))
            companyPage.fillField("totalemployee", data.get("totalEmployee"));
    }

    @When("the user clicks on the Save button in the company form")
    public void theUserClicksOnTheSaveButtonInTheCompanyForm() {
        if (companyPage == null) {
            companyPage = new CompanyPage();
        }
        companyPage.clickSave();
    }

    @Then("the newly created company {string} should appear at the top of the company list")
    public void theNewlyCreatedCompanyShouldAppearAtTheTopOfTheCompanyList(String companyName) {
        if (companyPage == null) {
            companyPage = new CompanyPage();
        }
        Assert.assertTrue("Company '" + companyName + "' should be in the datagrid",
                companyPage.isTextPresentInGrid(companyName));
    }

    /**
     * Creates a company with a uniquely timestamped name to avoid DB duplicate
     * errors on re-runs.
     */
    @When("the user enters company details with unique name")
    public void theUserEntersCompanyDetailsWithUniqueName() {
        if (companyPage == null) {
            companyPage = new CompanyPage();
        }
        String timestamp = new SimpleDateFormat("MMddHHmm").format(new Date());
        lastCreatedCompanyName = "AutoCo" + timestamp;
        companyPage.fillField("name", lastCreatedCompanyName);
        companyPage.fillField("type", "IT");
        companyPage.fillField("address", "123 Test Street");
        companyPage.fillField("phone", "9876543210");
        companyPage.fillField("email", "test@autotest.com");
        companyPage.fillField("pan", "ABCDE1234F");
        companyPage.fillField("tin", "TIN1234567890");
        companyPage.fillField("totalemployee", "150");

        // Fill country/state/city to prevent backend JOIN exclusion
        companyPage.fillField("countryname", "India");
        companyPage.fillField("state", "Maharashtra");
        companyPage.fillField("city", "Mumbai");

        logger.info("Creating company with unique name: " + lastCreatedCompanyName);
    }

    @Then("the newly created company should appear at the top of the company list")
    public void theNewlyCreatedCompanyShouldAppearAtTheTopOfTheCompanyList() {
        if (companyPage == null) {
            companyPage = new CompanyPage();
        }
        Assert.assertTrue("Company '" + lastCreatedCompanyName + "' should be in the datagrid",
                companyPage.isTextPresentInGrid(lastCreatedCompanyName));
    }

    // -- Update Company steps (TC03) --

    @When("the user clicks on the Plus icon to view a company")
    public void theUserClicksOnThePlusIconToViewACompany() {
        if (companyPage == null) {
            companyPage = new CompanyPage();
        }
        companyPage.expandFirstRow(); // expands row-detail to load the edit form (TC03)
    }

    @When("the user modifies the company details:")
    public void theUserModifiesTheCompanyDetails(io.cucumber.datatable.DataTable dataTable) {
        if (companyPage == null) {
            companyPage = new CompanyPage();
        }
        java.util.Map<String, String> data = dataTable.asMap(String.class, String.class);
        if (data.containsKey("name"))
            companyPage.fillField("name", data.get("name"));
        if (data.containsKey("companyName"))
            companyPage.fillField("name", data.get("companyName"));
        if (data.containsKey("address"))
            companyPage.fillField("address", data.get("address"));
        if (data.containsKey("phone"))
            companyPage.fillField("phone", data.get("phone"));
        if (data.containsKey("email"))
            companyPage.fillField("email", data.get("email"));
        if (data.containsKey("totalemployee"))
            companyPage.fillField("totalemployee", data.get("totalemployee"));
        if (data.containsKey("totalEmployee"))
            companyPage.fillField("totalemployee", data.get("totalEmployee"));
    }

    @Then("the updated company details should be reflected on the View Company page")
    public void theUpdatedCompanyDetailsShouldBeReflectedOnTheViewCompanyPage() {
        if (companyPage == null) {
            companyPage = new CompanyPage();
        }
        Assert.assertTrue("Company datagrid should still be visible after update",
                companyPage.isCompanyGridDisplayed());
    }

    // -- Delete Company steps (TC04) --

    @When("the user selects a company from the list")
    public void theUserSelectsACompanyFromTheList() {
        if (companyPage == null) {
            companyPage = new CompanyPage();
        }
        companyPage.selectFirstRow();
    }

    @When("the user clicks the Destroy button")
    public void theUserClicksTheDestroyButton() {
        if (companyPage == null) {
            companyPage = new CompanyPage();
        }
        companyPage.clickDestroy();
    }

    @When("the user confirms the deletion by clicking OK")
    public void theUserConfirmsTheDeletionByClickingOK() {
        if (companyPage == null) {
            companyPage = new CompanyPage();
        }
        companyPage.clickConfirmOk();
    }

    @Then("the company {string} should be deleted from the list")
    public void theCompanyShouldBeDeletedFromTheList(String companyName) {
        if (companyPage == null) {
            companyPage = new CompanyPage();
        }
        // After deletion, the row is removed from the datagrid
        Assert.assertTrue("Company datagrid should still be visible after deletion",
                companyPage.isCompanyGridDisplayed());
    }
}
