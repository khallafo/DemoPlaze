package TestCases;
import Pages.SignupPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import Pages.LogInPage;

import java.time.Duration;

public class LogInTest extends TestBase {
    private Faker faker;
    private LogInPage loginPage;


    /**
     * Handles JavaScript alerts & verifies the expected message
     */

    private void handleAlert(String expectedMessage) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        if (wait.until(ExpectedConditions.alertIsPresent()) != null) {
            Alert alert = driver.switchTo().alert();
            String alertMessage = alert.getText();
            System.out.println("Captured Alert: " + alertMessage);

            Assert.assertEquals(alertMessage, expectedMessage, "Please fill out Username and Password.");
            alert.accept(); // Close the alert
        } else {
            Assert.fail("Expected an alert but none appeared.");
        }
    }
        @Test
        public void testLogin () {
            loginPage = new LogInPage(driver);
            faker = new Faker();
            String randomName = faker.name().username(); // Ensure valid username format
            String randomPassword = faker.internet().password(8, 16); // Password between 8-16 chars

            loginPage.login(randomName, randomPassword);
            System.out.println("Login successful with name: " + randomName);
        }

        @Test
        public void testLoginWithEmptyUsername () {
            loginPage = new LogInPage(driver);
            faker = new Faker();
            String randomPassword = faker.internet().password(8, 16);

            loginPage.login("", randomPassword);
            System.out.println("Login attempted with empty username");

            // Handle expected alert
            handleAlert("Please fill out Username and Password.");
        }

        @Test
        public void testLoginWithEmptyPassword () {
            loginPage = new LogInPage(driver);
            faker = new Faker();
            String randomName = faker.name().username(); // Ensure valid username format

            loginPage.login(randomName, "");
            System.out.println("Login attempted with empty password");

            // Handle expected alert
            handleAlert("Please fill out Username and Password.");
        }

        @Test
        public void testLoginWithInvalidCredentials () {
            loginPage = new LogInPage(driver);
            faker = new Faker();
            String randomName = faker.name().username(); // Ensure valid username format
            String randomPassword = faker.internet().password(8, 16); // Password between 8-16 chars

            loginPage.login(randomName, randomPassword);
            System.out.println("Login attempted with invalid credentials");

            // Handle expected alert
            handleAlert("Invalid username or password!");
        }

        @Test
        public void testLoginWithSpecialCharacters () {
            loginPage = new LogInPage(driver);
            faker = new Faker();
            String specialCharName = "!@#$%^&*()_+";
            String specialCharPassword = "!@#$%^&*()_+";

            loginPage.login(specialCharName, specialCharPassword);
            System.out.println("Login attempted with special characters in username and password");

            // Handle expected alert (if applicable)
            handleAlert("Invalid characters in Username or Password.");
        }

        @Test
        public void testLoginWithLongUsernameAndPassword () {
            loginPage = new LogInPage(driver);
            faker = new Faker();
            String longUsername = faker.lorem().characters(255);
            String longPassword = faker.lorem().characters(255);

            loginPage.login(longUsername, longPassword);
            System.out.println("Login attempted with long username and password");

            // Handle expected alert (if applicable)
            handleAlert("Invalid characters in Username or Password.");
        }



    }


