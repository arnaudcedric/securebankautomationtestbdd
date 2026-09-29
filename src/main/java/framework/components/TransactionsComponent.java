package framework.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Component for the "Transactions" section of the dashboard, reached via
 * {@link SidebarComponent#goToTransactions()}. Currently exposes just the
 * page title text used for navigation assertions.
 */
public class TransactionsComponent extends BaseComponent {

    @FindBy(css = "[data-testid='transactions-page-title']")
    private WebElement payABillText;

    public String getMyAccountText() {
        return wait.until(
                ExpectedConditions.visibilityOf(payABillText)
        ).getText();
    }

    public TransactionsComponent(WebDriver driver) {
        super(driver);
    }

}
