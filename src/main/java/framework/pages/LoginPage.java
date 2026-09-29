package framework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object for the login page ({@code /bank/login}).
 * <p>
 * Note it extends {@link BankHomePage} rather than {@link BasePage} so that,
 * once login succeeds, callers can keep chaining home-page actions from the
 * same object (or use {@link #getBankPage()} to get a fresh
 * {@link BankHomePage} instance). Provides fluent methods to open the page,
 * fill in credentials, submit the form, and read the inline error message
 * shown on failed login attempts.
 */
public class LoginPage extends BankHomePage {

    private static final String PATH = "/bank/login";

    @FindBy(id = "login-username")
    private WebElement username;

    @FindBy(id = "login-password")
    private WebElement password;

    @FindBy(css = "[data-testid='login-submit-btn']")
    private WebElement loginButton;

    @FindBy(css = "[data-testid='login-error-message']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        navigateTo(PATH);
        return this;
    }

    public LoginPage enterUsername(String value) {
        wait.until(
                ExpectedConditions.visibilityOf(username)
        ).sendKeys(value);
        return this;
    }

    public LoginPage enterPassword(String value) {
        wait.until(
                ExpectedConditions.visibilityOf(password)
        ).sendKeys(value);
        return this;
    }

    public void clickLogin() {
        click(loginButton);
    }


    public void login(String usernameValue,String passwordValue) {
        enterUsername(usernameValue);
        enterPassword(passwordValue);
        clickLogin();
    }

    public BankHomePage getBankPage() {
        return new BankHomePage(driver);
    }


    public String getErrorMessage() {

        return getText(errorMessage);
    }

}
