package tests.login;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

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
}
