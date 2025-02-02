package TestCases;

import Config.DriverFactory;
import Config.PropertyReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class TestBase {
    public WebDriver driver;

    @BeforeMethod
    public void setup() {
        String browser = PropertyReader.getProperty("browser");
        DriverFactory.initDriver(browser);
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.get(PropertyReader.getProperty("baseUrl"));
    }
    @AfterMethod
    public void teardown() {
        DriverFactory.quitDriver();
    }
}
