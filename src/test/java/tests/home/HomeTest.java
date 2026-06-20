package tests.home;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class HomeTest extends BaseTest {

    private static final String VALID_USER     = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";

    private HomePage homePage;
    private WebDriverWait wait;

    @BeforeMethod(dependsOnMethods = "setUp")
    public void setUpPage() {
        homePage = new HomePage(driver);
        wait     = new WebDriverWait(driver, Duration.ofSeconds(10));
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login(VALID_USER, VALID_PASSWORD);
    }

    @Test
    public void validarHomepage() {
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"));
        Assert.assertTrue(homePage.getInventoryList().isDisplayed());
        Assert.assertEquals(homePage.getInventoryItems().size(), 6);
    }

    @Test
    public void validarCardDeProdutoNaHome() {
        Assert.assertTrue(homePage.getFirstProductName().isDisplayed());
        Assert.assertTrue(homePage.getFirstProductPrice().isDisplayed());
        Assert.assertTrue(homePage.getFirstProductPrice().getText().matches("^\\$\\d+\\.\\d{2}$"));
        Assert.assertTrue(homePage.getFirstProductImage().isDisplayed());
        Assert.assertTrue(homePage.getFirstAddToCartButton().isDisplayed());
        Assert.assertTrue(homePage.getFirstAddToCartButton().getText().contains("Add to cart"));
    }

    @Test
    public void validarAbout() {
        homePage.openMenu();
        homePage.getAboutLink().click();
        wait.until(ExpectedConditions.urlContains("saucelabs.com"));
        Assert.assertTrue(driver.getCurrentUrl().contains("saucelabs.com"));
    }

    @Test
    public void validarAllItems() {
        homePage.openMenu();
        homePage.getAllItemsLink().click();
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"));
        Assert.assertTrue(homePage.getInventoryList().isDisplayed());
    }

    @Test
    public void validarCarrinhoPersistidoAposReload() {
        homePage.addFirstItemToCart();
        Assert.assertEquals(homePage.getCartBadge().getText(), "1");
        driver.navigate().refresh();
        Assert.assertTrue(homePage.getCartBadge().isDisplayed());
        Assert.assertEquals(homePage.getCartBadge().getText(), "1");
    }

    @Test
    public void validarOrdenarProdutosNameAtoZ() {
        homePage.selectSort("az");
        List<String> names  = homePage.getProductNames();
        List<String> sorted = names.stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(names, sorted);
    }

    @Test
    public void validarOrdenarProdutosNameZtoA() {
        homePage.selectSort("za");
        List<String> names  = homePage.getProductNames();
        List<String> sorted = names.stream()
                .sorted((a, b) -> b.compareTo(a))
                .collect(Collectors.toList());
        Assert.assertEquals(names, sorted);
    }

    @Test
    public void validarOrdenarProdutosPriceLowToHigh() {
        homePage.selectSort("lohi");
        List<Double> prices = homePage.getProductPrices();
        List<Double> sorted = prices.stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(prices, sorted);
    }

    @Test
    public void validarOrdenarProdutosPriceHighToLow() {
        homePage.selectSort("hilo");
        List<Double> prices = homePage.getProductPrices();
        List<Double> sorted = prices.stream()
                .sorted((a, b) -> b.compareTo(a))
                .collect(Collectors.toList());
        Assert.assertEquals(prices, sorted);
    }
}
