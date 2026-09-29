package framework.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Component for the "Pay a Bill" section of the dashboard, reached via
 * {@link SidebarComponent#goToBillPay()}. Currently exposes just the page
 * title text used for navigation assertions.
 */
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
