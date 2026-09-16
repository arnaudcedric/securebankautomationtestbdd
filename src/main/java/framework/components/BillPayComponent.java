package framework.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BillPayComponent extends BaseComponent {

    @FindBy(css = "[data-testid='bill-pay-page-title']")
    private WebElement payABillText;

    public String getPayABillText() {
        return wait.until(
                ExpectedConditions.visibilityOf(payABillText)
        ).getText();
    }

    public BillPayComponent(WebDriver driver) {
        super(driver);
    }
}
