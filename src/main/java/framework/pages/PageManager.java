package framework.pages;

import org.openqa.selenium.WebDriver;

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
