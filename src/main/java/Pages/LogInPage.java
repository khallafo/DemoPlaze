package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LogInPage extends BasePage {
    @FindBy(css = "#login2")
    private WebElement loginPage;
    @FindBy(css = "#loginusername")
    private WebElement usernameField;
    @FindBy(css = "#loginpassword")
    private WebElement passwordField;
    @FindBy(css = "#logInModal > div > div > div.modal-footer > button.btn.btn-primary")
    private WebElement loginButton;

    private WebDriverWait wait;

    public LogInPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // 10 seconds wait time
    }

    public void clickLoginButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("login2")));

        try {
            loginButton.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Element not clickable, using JavaScriptExecutor.");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", loginButton);
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

    public void clickLogin() {
        wait.until(ExpectedConditions.visibilityOf(loginButton));
        loginButton.click();
    }

    public void login(String username, String password) {
        clickLoginButton();
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
}
