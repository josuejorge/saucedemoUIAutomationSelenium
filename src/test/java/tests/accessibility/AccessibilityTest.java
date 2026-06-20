package tests.accessibility;

import base.BaseTest;
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.LoginPage;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class AccessibilityTest extends BaseTest {

    private static final String USER = "standard_user";
    private static final String PASS = "secret_sauce";

    @Test(description = "WCAG - Página de Login")
    public void validarAcessibilidadeLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        analisarAcessibilidade("Login");
    }

    @Test(description = "WCAG - Página Home / Inventário")
    public void validarAcessibilidadeHome() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login(USER, PASS);
        analisarAcessibilidade("Home / Inventário");
    }

    @Test(description = "WCAG - Página de Detalhe do Produto")
    public void validarAcessibilidadeProduto() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login(USER, PASS);
        new HomePage(driver).getFirstProductName().click();
        analisarAcessibilidade("Detalhe do Produto");
    }

    @Test(description = "WCAG - Página do Carrinho")
    public void validarAcessibilidadeCarrinho() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login(USER, PASS);
        HomePage homePage = new HomePage(driver);
        homePage.addFirstItemToCart();
        new CartPage(driver).navigate();
        analisarAcessibilidade("Carrinho");
    }

    @Test(description = "WCAG - Página de Checkout")
    public void validarAcessibilidadeCheckout() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login(USER, PASS);
        HomePage homePage = new HomePage(driver);
        homePage.addFirstItemToCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.navigate();
        cartPage.checkout();
        analisarAcessibilidade("Checkout - Informações");
    }

    @Step("Analisar acessibilidade WCAG: {pageName}")
    private void analisarAcessibilidade(String pageName) {
        Results results = new AxeBuilder().analyze(driver);
        List<Rule> violations = results.getViolations();

        List<Rule> bloqueantes = violations.stream()
                .filter(r -> "critical".equals(r.getImpact()) || "serious".equals(r.getImpact()))
                .collect(Collectors.toList());

        List<Rule> avisos = violations.stream()
                .filter(r -> "moderate".equals(r.getImpact()) || "minor".equals(r.getImpact()))
                .collect(Collectors.toList());

        if (!avisos.isEmpty()) {
            Allure.addAttachment(
                    "Avisos WCAG - " + pageName + " (" + avisos.size() + ")",
                    "text/plain",
                    new ByteArrayInputStream(formatarViolacoes(avisos).getBytes(StandardCharsets.UTF_8)),
                    ".txt"
            );
        }

        if (!bloqueantes.isEmpty()) {
            Allure.addAttachment(
                    "Violações WCAG críticas - " + pageName + " (" + bloqueantes.size() + ")",
                    "text/plain",
                    new ByteArrayInputStream(formatarViolacoes(bloqueantes).getBytes(StandardCharsets.UTF_8)),
                    ".txt"
            );
            throw new AssertionError(
                    "[" + pageName + "] " + bloqueantes.size()
                    + " violação(ões) WCAG critical/serious encontrada(s). Veja o attachment no Allure."
            );
        }
    }

    private String formatarViolacoes(List<Rule> violacoes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < violacoes.size(); i++) {
            Rule rule = violacoes.get(i);
            sb.append("#").append(i + 1)
              .append(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            sb.append("Regra:      ").append(rule.getId()).append("\n");
            sb.append("Impacto:    ").append(rule.getImpact().toUpperCase()).append("\n");
            sb.append("Descrição:  ").append(rule.getDescription()).append("\n");
            sb.append("Critérios:  ").append(String.join(", ", rule.getTags())).append("\n");
            sb.append("Referência: ").append(rule.getHelpUrl()).append("\n");
            sb.append("Elementos:\n");
            rule.getNodes().forEach(node ->
                    sb.append("  → ").append(node.getHtml()).append("\n")
            );
            sb.append("\n");
        }
        return sb.toString();
    }
}
