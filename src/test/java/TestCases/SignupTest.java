package TestCases;

import Pages.SignupPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class SignupTest extends TestBase {
    private SignupPage signupPage;
    private Faker faker;

    @BeforeMethod
    public void setupTest() {
        signupPage = new SignupPage(driver);
        faker = new Faker();
    }

    /**
     * Handles JavaScript alerts & verifies the expected message
     */
    private void handleAlert(String expectedMessage) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        if (wait.until(ExpectedConditions.alertIsPresent()) != null) {
            Alert alert = driver.switchTo().alert();
            String alertMessage = alert.getText();
            System.out.println("Captured Alert: " + alertMessage);

            Assert.assertEquals(alertMessage, expectedMessage, "Alert message did not match!");
            alert.accept(); // Close the alert
        } else {
            Assert.fail("Expected an alert but none appeared.");
        }
    }

    @Test
    public void testSignup() {
        String randomName = faker.name().username(); // Ensure valid username format
        String randomPassword = faker.internet().password(8, 16); // Password between 8-16 chars

        signupPage.signup(randomName, randomPassword);
        System.out.println("Signup successful with name: " + randomName);
    }

    @Test
    public void testSignupWithEmptyUsername() {
        String randomPassword = faker.internet().password(8, 16);

        signupPage.signup("", randomPassword);
        System.out.println("Signup attempted with empty username");

        // Handle expected alert
        handleAlert("Please fill out Username and Password.");
    }

    @Test
    public void testSignupWithEmptyPassword() {
        String randomName = faker.name().username();

        signupPage.signup(randomName, "");
        System.out.println("Signup attempted with empty password");

        // Handle expected alert
        handleAlert("Please fill out Username and Password.");
    }

    @Test
    public void testSignupWithSpecialCharacters() {
        String specialCharName = "!@#$%^&*()_+";
        String specialCharPassword = "!@#$%^&*()_+";

        signupPage.signup(specialCharName, specialCharPassword);
        System.out.println("Signup attempted with special characters in username and password");

        // Handle expected alert (if applicable)
        handleAlert("Invalid characters in Username or Password.");
    }

    @Test
    public void testSignupWithLongUsernameAndPassword() {
        String longUsername = faker.lorem().characters(255);
        String longPassword = faker.lorem().characters(255);

        signupPage.signup(longUsername, longPassword);
        System.out.println("Signup attempted with long username and password");

        // Handle expected alert (if applicable)
        handleAlert("Username or Password too long.");
    }
}
