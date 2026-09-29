package framework.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Component for the "Send Money" section of the dashboard, reached via
 * {@link SidebarComponent#goToSendMoney()}. Currently exposes just the page
 * title text used for navigation assertions.
 */
public class SendMoneyComponent extends BaseComponent {

    @FindBy(css = "[data-testid='send-money-page-title']")
    private WebElement sendMoneyText;

    public String getSendMoneyText() {
        return wait.until(
                ExpectedConditions.visibilityOf(sendMoneyText)
        ).getText();
    }

    public SendMoneyComponent(WebDriver driver) {
        super(driver);
    }


}
