package framework.pages;

import framework.components.SidebarComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object for the authenticated bank home page (the dashboard landing
 * page shown right after a successful login). Exposes the welcome banner
 * text and the {@link SidebarComponent}, which is the entry point for
 * navigating to the other dashboard sections (accounts, transfer, send
 * money, bill pay, transactions).
 */
public class BankHomePage extends BasePage {

    private final SidebarComponent sidebar;

    @FindBy(css = "[data-testid='dashboard-welcome-message']")
    private WebElement welcomeText;

    public BankHomePage(WebDriver driver) {
        super(driver);
        this.sidebar = new SidebarComponent(driver);
    }

    public String getWelcomeText() {

        return wait.until(
                ExpectedConditions.visibilityOf(welcomeText)
        ).getText().trim();


    }

    public SidebarComponent sidebar() {
        return sidebar;
    }

}
