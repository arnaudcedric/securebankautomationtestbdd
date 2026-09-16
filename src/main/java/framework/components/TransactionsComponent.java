package framework.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
