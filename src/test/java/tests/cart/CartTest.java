package tests.cart;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.LoginPage;

public class CartTest extends BaseTest {

    private static final String VALID_USER     = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";
    private static final String FIRST_NAME     = "John";
    private static final String LAST_NAME      = "Doe";
    private static final String ZIP_CODE       = "12345";

    private HomePage homePage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @BeforeMethod(dependsOnMethods = "setUp")
    public void setUpPage() {
        homePage     = new HomePage(driver);
        cartPage     = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login(VALID_USER, VALID_PASSWORD);
        homePage.addFirstItemToCart();
    }

    @Test
    public void validarCarrinho() {
        cartPage.navigate();
        Assert.assertTrue(driver.getCurrentUrl().contains("/cart.html"));
        Assert.assertEquals(cartPage.getCartItems().size(), 1);
        Assert.assertTrue(cartPage.getFirstCartItemName().isDisplayed());
        Assert.assertTrue(cartPage.getCheckoutButton().isDisplayed());
        Assert.assertTrue(cartPage.getContinueShoppingButton().isDisplayed());
    }

    @Test
    public void validarCheckoutSemInformacao() {
        cartPage.navigate();
        cartPage.checkout();
        Assert.assertTrue(driver.getCurrentUrl().contains("/checkout-step-one.html"));
        checkoutPage.clickContinue();
        Assert.assertTrue(checkoutPage.getErrorMessage().isDisplayed());
        Assert.assertTrue(checkoutPage.getErrorMessage().getText()
                .contains("First Name is required"));
    }

    @Test
    public void validarCompraCompletaDeProduto() {
        cartPage.navigate();
        cartPage.checkout();
        Assert.assertTrue(driver.getCurrentUrl().contains("/checkout-step-one.html"));
        checkoutPage.fillInfo(FIRST_NAME, LAST_NAME, ZIP_CODE);
        checkoutPage.clickContinue();
        Assert.assertTrue(driver.getCurrentUrl().contains("/checkout-step-two.html"));
        Assert.assertEquals(checkoutPage.getOrderItems().size(), 1);
        checkoutPage.clickFinish();
        Assert.assertTrue(driver.getCurrentUrl().contains("/checkout-complete.html"));
        Assert.assertTrue(checkoutPage.getCompleteHeader().isDisplayed());
        Assert.assertEquals(checkoutPage.getCompleteHeader().getText(), "Thank you for your order!");
    }

    @Test
    public void validarRemoverPedidoDoCarrinho() {
        cartPage.navigate();
        Assert.assertEquals(cartPage.getCartItems().size(), 1);
        cartPage.removeFirstItem();
        Assert.assertEquals(cartPage.getCartItems().size(), 0);
    }

    @Test
    public void validarCancelarCompraDeProduto() {
        cartPage.navigate();
        cartPage.checkout();
        Assert.assertTrue(driver.getCurrentUrl().contains("/checkout-step-one.html"));
        checkoutPage.clickCancel();
        Assert.assertTrue(driver.getCurrentUrl().contains("/cart.html"));
        Assert.assertEquals(cartPage.getCartItems().size(), 1);
    }
}
