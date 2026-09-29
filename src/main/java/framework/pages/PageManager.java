package framework.pages;

import org.openqa.selenium.WebDriver;

/**
 * Lazily-instantiated registry of Page Objects for a single {@link WebDriver}
 * instance. One {@code PageManager} is created per test/scenario (see
 * {@code context.ScenarioContext}) so that step definitions can obtain page
 * objects (e.g. {@code pages().loginPage()}) without each step having to
 * construct them manually, and each page is only instantiated once per run.
 */
public class PageManager {

    private final WebDriver driver;

    private LoginPage loginPage;
    private BankHomePage bankHomePage;

    public PageManager(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage loginPage() {

        if (loginPage == null) {
            loginPage = new LoginPage(driver);
        }

        return loginPage;
    }

    public BankHomePage bankHomePage() {

        if (bankHomePage == null) {
            bankHomePage = new BankHomePage(driver);
        }

        return bankHomePage;
    }
}
