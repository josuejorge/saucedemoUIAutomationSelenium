package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By menuButton       = By.id("react-burger-menu-btn");
    private final By logoutLink       = By.id("logout_sidebar_link");
    private final By aboutLink        = By.id("about_sidebar_link");
    private final By allItemsLink     = By.id("inventory_sidebar_link");
    private final By inventoryList    = By.cssSelector(".inventory_list");
    private final By inventoryItems   = By.cssSelector(".inventory_item");
    private final By productNames     = By.cssSelector(".inventory_item_name");
    private final By productPrices    = By.cssSelector(".inventory_item_price");
    private final By productImages    = By.cssSelector(".inventory_item_img img");
    private final By addToCartButtons = By.cssSelector("[data-test^='add-to-cart']");
    private final By cartBadge        = By.cssSelector(".shopping_cart_badge");
    private final By sortDropdown     = By.cssSelector("[data-test='product-sort-container']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(menuButton)).click();
    }

    public WebElement getInventoryList() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryList));
    }

    public List<WebElement> getInventoryItems() {
        return driver.findElements(inventoryItems);
    }

    public WebElement getFirstProductName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productNames));
    }

    public WebElement getFirstProductPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productPrices));
    }

    public WebElement getFirstProductImage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productImages));
    }

    public WebElement getFirstAddToCartButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(addToCartButtons));
    }

    public WebElement getCartBadge() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge));
    }

    public WebElement getAboutLink() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(aboutLink));
    }

    public WebElement getAllItemsLink() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(allItemsLink));
    }

    public void addFirstItemToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButtons)).click();
    }

    public void removeFirstItemFromCart() {
        By removeButtons = By.cssSelector("[data-test^='remove']");
        wait.until(ExpectedConditions.elementToBeClickable(removeButtons)).click();
    }

    public void selectSort(String value) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(sortDropdown));
        new Select(dropdown).selectByValue(value);
    }

    public List<String> getProductNames() {
        return driver.findElements(productNames).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        return driver.findElements(productPrices).stream()
                .map(el -> Double.parseDouble(el.getText().replace("$", "")))
                .collect(Collectors.toList());
    }

    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(menuButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/"));
    }
}
