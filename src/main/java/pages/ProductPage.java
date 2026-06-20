package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By productName          = By.cssSelector(".inventory_details_name");
    private final By productDescription   = By.cssSelector(".inventory_details_desc");
    private final By productPrice         = By.cssSelector(".inventory_details_price");
    private final By productImage         = By.cssSelector("img.inventory_details_img");
    private final By addToCartButton      = By.cssSelector("[data-test^='add-to-cart']");
    private final By removeFromCartButton = By.cssSelector("[data-test^='remove']");
    private final By backToProductsButton = By.cssSelector("[data-test='back-to-products']");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebElement getProductName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productName));
    }

    public WebElement getProductDescription() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productDescription));
    }

    public WebElement getProductPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productPrice));
    }

    public WebElement getProductImage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productImage));
    }

    public WebElement getAddToCartButton() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartButton));
    }

    public WebElement getRemoveFromCartButton() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(removeFromCartButton));
    }

    public void addToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    }

    public void goBackToProducts() {
        wait.until(ExpectedConditions.elementToBeClickable(backToProductsButton)).click();
    }
}
