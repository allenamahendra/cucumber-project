package stepDefinations.UI;

import PageObjects.HomePage;
import PageObjects.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;

public class UIStepDef {
    LoginPage loginScreen = new LoginPage();
    HomePage homePage = new HomePage();

    @When("I open united home page")
    public void iOpenUnitedHomePage() throws IOException {
        loginScreen.openBrowser();
        loginScreen.navigateToApplicationPage();
    }

    @And("I select Book menu")
    public void iSelectBookMenu() throws Exception {
        homePage.selectBookmenu();
    }

    @And("I select flight type option as {string}")
    public void iSelectOption(String flightType) throws Exception {
        homePage.selectFlightType(flightType);
    }

    @And("I select from city as {string}")
    public void iSelectFromCityAs(String city) {
        homePage.selectFromCity(city);
    }

    @And("I select to city as {string}")
    public void iSelectToCityAs(String city) {
        homePage.selectToCity(city);
    }

    @And("I click on find flights button")
    public void iClickOnFindFlightsButton() throws Exception {
        homePage.clickOnFindFlightsButton();
    }

    @And("I select from date as {string}")
    public void iSelectFromDateAs(String fromDate) {
        homePage.selectFromDate(fromDate);
    }

    @And("I select travellers as {string}")
    public void iSelectTravellersAs(String arg0) {
    }

    @Then("list of available flights are displayed")
    public void listOfAvailableFlightsAreDisplayed() {
        homePage.verifyListOfAvailableFlights();
    }

    @And("I select travellers as {string} Adults")
    public void iSelectTravellersAsAdults(String travellers) throws Exception {
        homePage.selectTravellersMenu();
        homePage.selectAdults(travellers);
        homePage.closeTravellersMenu();
    }

    @And("I select to date as {string}")
    public void iSelectToDateAs(String toDate) {
        homePage.selectToDate(toDate);
    }
}
