package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class SignupPage extends BasePage {
    @FindBy(css = "#signin2")
    private WebElement signupPage;
    @FindBy(css = "#sign-username")
    private WebElement usernameField;
    @FindBy(css = "#sign-password")
    private WebElement passwordField;
    @FindBy(css = "#signInModal > div > div > div.modal-footer > button.btn.btn-primary")
    private WebElement signupButton;

    private WebDriverWait wait;

    public SignupPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // 10 seconds wait time
    }

    public void clickSignupButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement signupButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("signin2")));

        try {
            signupButton.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Element not clickable, using JavaScriptExecutor.");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", signupButton);
        }
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameField));
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.sendKeys(password);
    }

    public void clickSignup() {
        wait.until(ExpectedConditions.visibilityOf(signupButton));
        signupButton.click();
    }

    public void signup(String username, String password) {
        clickSignupButton();
        enterUsername(username);
        enterPassword(password);
        clickSignup();
    }
}