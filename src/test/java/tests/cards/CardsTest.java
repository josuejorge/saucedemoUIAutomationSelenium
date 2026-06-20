package tests.cards;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;

public class CardsTest extends BaseTest {

    private static final String VALID_USER     = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";

    private HomePage    homePage;
    private ProductPage productPage;

    @BeforeMethod(dependsOnMethods = "setUp")
    public void setUpPage() {
        homePage    = new HomePage(driver);
        productPage = new ProductPage(driver);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login(VALID_USER, VALID_PASSWORD);
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
    public void validarAdicionarProdutoAoCarrinho() {
        homePage.addFirstItemToCart();
        Assert.assertTrue(homePage.getCartBadge().isDisplayed());
        Assert.assertEquals(homePage.getCartBadge().getText(), "1");
        Assert.assertTrue(homePage.getFirstAddToCartButton().isDisplayed());
    }

    @Test
    public void validarAdicionarMultiplosProdutosAoCarrinho() {
        homePage.addFirstItemToCart();
        Assert.assertEquals(homePage.getCartBadge().getText(), "1");
        homePage.addFirstItemToCart();
        Assert.assertEquals(homePage.getCartBadge().getText(), "2");
    }

    @Test
    public void validarRemoverProdutoDoCarrinho() {
        homePage.addFirstItemToCart();
        Assert.assertEquals(homePage.getCartBadge().getText(), "1");
        homePage.removeFirstItemFromCart();
        Assert.assertTrue(homePage.getFirstAddToCartButton().isDisplayed());
    }

    @Test
    public void validarDetalhesDoProduto() {
        homePage.getFirstProductName().click();
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory-item.html"));
        Assert.assertTrue(productPage.getProductName().isDisplayed());
        Assert.assertTrue(productPage.getProductDescription().isDisplayed());
        Assert.assertTrue(productPage.getProductPrice().isDisplayed());
        Assert.assertTrue(productPage.getProductPrice().getText().matches("^\\$\\d+\\.\\d{2}$"));
        Assert.assertTrue(productPage.getProductImage().isDisplayed());
    }

    @Test
    public void validarBotaoBackToProducts() {
        homePage.getFirstProductName().click();
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory-item.html"));
        productPage.goBackToProducts();
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"));
        Assert.assertTrue(homePage.getInventoryList().isDisplayed());
    }

    @Test
    public void validarAdicionarProdutoDentroTelaDedEtalhe() {
        homePage.getFirstProductName().click();
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory-item.html"));
        productPage.addToCart();
        Assert.assertTrue(homePage.getCartBadge().isDisplayed());
        Assert.assertEquals(homePage.getCartBadge().getText(), "1");
        Assert.assertTrue(productPage.getRemoveFromCartButton().isDisplayed());
    }
}
