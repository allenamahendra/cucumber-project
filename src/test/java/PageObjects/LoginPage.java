package PageObjects;

import libraries.Validations;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import stepDefinations.TestBase;
import testData.TestDataGenerator;

import java.io.IOException;
import java.util.List;

public class LoginPage extends TestBase {


    public LoginPage() {
        InitElements();
    }

    private void InitElements() {

    }

    public void openBrowser() throws IOException {
        openDriverBrowser();
    }


    public void navigateToApplicationPage() throws IOException {
        navigatetoURL();
    }


}
