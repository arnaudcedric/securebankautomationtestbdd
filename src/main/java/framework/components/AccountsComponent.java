package framework.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class AccountsComponent extends BaseComponent {

    @FindBy(css = "[data-testid='accounts-table-wrapper']")
    private WebElement tableWrapper;

    @FindBy(css = "[data-testid='accounts-table']")
    private WebElement accountsTable;

    @FindBy(css = "[data-testid='accounts-page-title']")
    private WebElement myAccountText;

    @FindBy(xpath = "//table[@data-testid='accounts-table']//th[normalize-space()='Account']")
    private WebElement accountHeader;

    @FindBy(xpath = "//table[@data-testid='accounts-table']//th[normalize-space()='Type']")
    private WebElement typeHeader;

    @FindBy(xpath = "//table[@data-testid='accounts-table']//th[normalize-space()='Balance']")
    private WebElement balanceHeader;

    @FindBy(xpath = "//table[@data-testid='accounts-table']//th[normalize-space()='Status']")
    private WebElement statusHeader;

    @FindBy(xpath = "//table[@data-testid='accounts-table']//th[normalize-space()='Actions']")
    private WebElement actionsHeader;

    @FindBy(css = "[data-testid='stat-card']")
    private List<WebElement> statCards;

    public AccountsComponent(WebDriver driver) {
        super(driver);
    }

    public String getMyAccountText() {
        return wait.until(
                ExpectedConditions.visibilityOf(myAccountText)
        ).getText();
    }

}
