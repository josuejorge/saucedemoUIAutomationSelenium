package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By cartIcon              = By.cssSelector(".shopping_cart_link");
    private final By cartItems             = By.cssSelector(".cart_item");
    private final By cartItemNames         = By.cssSelector(".cart_item .inventory_item_name");
    private final By removeButtons         = By.cssSelector("[data-test^='remove']");
    private final By checkoutButton        = By.cssSelector("[data-test='checkout']");
    private final By continueShoppingButton = By.cssSelector("[data-test='continue-shopping']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigate() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
    }

    public List<WebElement> getCartItems() {
        return driver.findElements(cartItems);
    }

    public WebElement getFirstCartItemName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemNames));
    }

    public WebElement getCheckoutButton() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutButton));
    }

    public WebElement getContinueShoppingButton() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(continueShoppingButton));
    }

    public void checkout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
    }

    public void removeFirstItem() {
        wait.until(ExpectedConditions.elementToBeClickable(removeButtons)).click();
    }

    public void continueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton)).click();
    }
}
