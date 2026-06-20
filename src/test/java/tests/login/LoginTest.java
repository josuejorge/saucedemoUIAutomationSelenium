package tests.login;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest { 

    private LoginPage loginPage;
    private static final String VALID_USER     = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";
    private static final String WRONG_PASSWORD = "wrong_password";

    @BeforeMethod(dependsOnMethods = "setUp")
    public void setUpPage() {
        loginPage = new LoginPage(driver);
        loginPage.navigate();
    }

    @Test
    public void validarQueSiteAbreComSucesso() {
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/");
        Assert.assertTrue(loginPage.getLogo().isDisplayed());
        Assert.assertEquals(loginPage.getLogo().getText(), "Swag Labs");
        Assert.assertTrue(loginPage.getUsernameInput().isDisplayed());
        Assert.assertTrue(loginPage.getPasswordInput().isDisplayed());
        Assert.assertTrue(loginPage.getLoginButton().isDisplayed());
    }

    @Test
    public void validarLoginComSucesso() {
        loginPage.login(VALID_USER, VALID_PASSWORD);
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"));

    }

    @Test
    public void validarLoginComFalha() {
        loginPage.login(VALID_USER, WRONG_PASSWORD);
        Assert.assertTrue(loginPage.getErrorMessage().isDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().getText()
            .contains("Username and password do not match any user in this service"));
    }
}
