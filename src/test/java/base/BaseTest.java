package base;

import evidence.ScreenshotEventListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Map;

public class BaseTest {

    protected WebDriver driver;
    protected static final String BASE_URL = "https://www.saucedemo.com";

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", Map.of(
            "credentials_enable_service", false,
            "profile.password_manager_enabled", false,
            "profile.password_manager_leak_detection", false
        ));
        ChromeDriver rawDriver = new ChromeDriver(options);
        rawDriver.manage().window().maximize();
        driver = new EventFiringDecorator<>(new ScreenshotEventListener(rawDriver)).decorate(rawDriver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
