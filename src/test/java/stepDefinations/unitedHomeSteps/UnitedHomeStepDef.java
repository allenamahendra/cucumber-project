package stepDefinations.unitedHomeSteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

public class UnitedHomeStepDef {

    @When("^User open united home page$")
    public void user_open_united_home_page() throws Throwable {
        System.out.println("User open united home page");
    }

    @And("User selects Book tab")
    public void userSelectsBookTab() {
        System.out.println("User selects Book tab");
    }

    @And("^User selects flight type as \"([^\"]*)\"$")
    public void user_selects_flight_type_as_something(String flightType) throws Throwable {
        System.out.println("Flight type selected as: "+flightType);
    }
}
