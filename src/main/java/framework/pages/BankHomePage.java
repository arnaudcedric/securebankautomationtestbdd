package framework.pages;

import framework.components.SidebarComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
