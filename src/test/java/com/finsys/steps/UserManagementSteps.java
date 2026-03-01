package com.finsys.steps;

import com.finsys.pages.HomePage;
import com.finsys.pages.UserManagementPage;
import io.cucumber.java.en.*;
import org.junit.Assert;

import java.util.Map;

/**
 * UserManagementSteps - Step definitions for TC10 User Management.
 *
 * users/show_form.php real field names:
 * firstname, lastname, phone, email, photoid, gender (radio), address, uid,
 * rolename (select)
 *
 * Navigation: CFO has no visible Manage User link -> use JS Navigate('manage
 * user')
 */
public class UserManagementSteps {

    private HomePage homePage;
    private UserManagementPage userPage;

    @When("the user clicks on User Management sub node")
    public void theUserClicksOnUserManagementSubNode() {
        homePage = new HomePage();
        // Navigate via JS since 'Manage User' link is not visible for CFO role
        homePage.navigateTo("manage user");
        homePage.switchToActionFrame();
        userPage = new UserManagementPage();
    }

    @Then("the User Management panel should be displayed")
    public void theUserManagementPanelShouldBeDisplayed() {
        if (userPage == null) {
            userPage = new UserManagementPage();
        }
        Assert.assertTrue("User Management datagrid should be displayed",
                userPage.isUserPanelDisplayed());
    }

    @When("the user clicks on the New icon at the top of the user list")
    public void theUserClicksOnTheNewIconAtTheTopOfTheUserList() {
        if (userPage == null) {
            userPage = new UserManagementPage();
        }
        userPage.clickNew();
    }

    /**
     * Fills user form fields using real field names from users/show_form.php:
     * uid, firstname, lastname, email, phone, photoid, rolename
     */
    @When("the user enters new user details:")
    public void theUserEntersNewUserDetails(io.cucumber.datatable.DataTable dataTable) {
        if (userPage == null) {
            userPage = new UserManagementPage();
        }
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        data.forEach((key, value) -> {
            switch (key) {
                // support both old and new naming conventions
                case "firstName":
                case "firstname":
                    userPage.fillField("firstname", value);
                    break;
                case "lastName":
                case "lastname":
                    userPage.fillField("lastname", value);
                    break;
                case "uid":
                    userPage.fillField("uid", value);
                    break;
                case "email":
                    userPage.fillField("email", value);
                    break;
                case "phone":
                    userPage.fillField("phone", value);
                    break;
                case "photoid":
                    userPage.fillField("photoid", value);
                    break;
                case "rolename":
                case "role":
                    userPage.fillField("rolename", value);
                    break;
                default:
                    userPage.fillField(key, value);
                    break;
            }
        });
    }

    @When("the user clicks on the Save button in the user form")
    public void theUserClicksOnTheSaveButtonInTheUserForm() {
        if (userPage == null) {
            userPage = new UserManagementPage();
        }
        userPage.clickSave();
    }

    @Then("the newly created user {string} should appear in the user list")
    public void theNewlyCreatedUserShouldAppearInTheUserList(String userId) {
        if (userPage == null) {
            userPage = new UserManagementPage();
        }
        boolean found = userPage.isTextPresentInGrid(userId);
        Assert.assertTrue("User '" + userId + "' should be present in the datagrid", found);
    }
}
