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
        signupPage = new SignupPage(driver);
        faker = new Faker();
        String randomName = faker.name().username(); // Ensure valid username format
        String randomPassword = faker.internet().password(8, 16); // Password between 8-16 chars

        signupPage.signup(randomName, randomPassword);
        System.out.println("Signup successful with name: " + randomName);
    }

    @Test
    public void testSignupWithEmptyUsername() {
        signupPage = new SignupPage(driver);
        faker = new Faker();
        String randomPassword = faker.internet().password(8, 16);

        signupPage.signup("", randomPassword);
        System.out.println("Signup attempted with empty username");

        // Handle expected alert
        handleAlert("Please fill out Username and Password.");
    }

    @Test
    public void testSignupWithEmptyPassword() {
        signupPage = new SignupPage(driver);
        faker = new Faker();
        String randomName = faker.name().username();

        signupPage.signup(randomName, "");
        System.out.println("Signup attempted with empty password");

        // Handle expected alert
        handleAlert("Please fill out Username and Password.");
    }

    @Test
    public void testSignupWithSpecialCharacters() {
        signupPage = new SignupPage(driver);
        faker = new Faker();
        String specialCharName = "!@#$%^&*()_+";
        String specialCharPassword = "!@#$%^&*()_+";

        signupPage.signup(specialCharName, specialCharPassword);
        System.out.println("Signup attempted with special characters in username and password");

        // Handle expected alert (if applicable)
        handleAlert("Invalid characters in Username or Password.");
    }

    @Test
    public void testSignupWithLongUsernameAndPassword() {
        signupPage = new SignupPage(driver);
        faker = new Faker();
        String longUsername = faker.lorem().characters(255);
        String longPassword = faker.lorem().characters(255);

        signupPage.signup(longUsername, longPassword);
        System.out.println("Signup attempted with long username and password");

        // Handle expected alert (if applicable)
        handleAlert("Username or Password too long.");
    }

    @Test
    public void testSignupWithWhitespaceUsername() {
        signupPage = new SignupPage(driver);
        String whitespaceUsername = "   ";
        String randomPassword = new Faker().internet().password(8, 16);

        signupPage.signup(whitespaceUsername, randomPassword);
        System.out.println("Signup attempted with whitespace username");

        // Handle expected alert
        handleAlert("Please fill out Username and Password.");
    }

    @Test
    public void testSignupWithWhitespacePassword() {
        signupPage = new SignupPage(driver);
        String randomName = new Faker().name().username();
        String whitespacePassword = "   ";

        signupPage.signup(randomName, whitespacePassword);
        System.out.println("Signup attempted with whitespace password");

        // Handle expected alert
        handleAlert("Please fill out Username and Password.");
    }

    @Test
    public void testSignupWithSQLInjection() {
        signupPage = new SignupPage(driver);
        String sqlInjectionUsername = "admin' OR '1'='1";
        String sqlInjectionPassword = "password' OR '1'='1";

        signupPage.signup(sqlInjectionUsername, sqlInjectionPassword);
        System.out.println("Signup attempted with SQL injection in username and password");

        // Handle expected alert (if applicable)
        handleAlert("Invalid characters in Username or Password.");
    }

    @Test
    public void testSignupWithHTMLTags() {
        signupPage = new SignupPage(driver);
        String htmlTagUsername = "<script>alert('test')</script>";
        String htmlTagPassword = "<b>password</b>";

        signupPage.signup(htmlTagUsername, htmlTagPassword);
        System.out.println("Signup attempted with HTML tags in username and password");

        // Handle expected alert (if applicable)
        handleAlert("Invalid characters in Username or Password.");
    }

    @Test
    public void testSignupWithMaxLengthUsername() {
        signupPage = new SignupPage(driver);
        String maxLengthUsername = new Faker().lorem().characters(50); // Assuming max length is 50
        String randomPassword = new Faker().internet().password(8, 16);

        signupPage.signup(maxLengthUsername, randomPassword);
        System.out.println("Signup attempted with max length username");

        // Handle expected alert (if applicable)}
    }
}