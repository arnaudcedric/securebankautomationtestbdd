package framework.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Placeholder component for the "Transfer" section of the dashboard, reached
 * via {@link SidebarComponent#goToTransfer()}. No elements or behavior
 * defined yet beyond {@link PageFactory} initialization.
 * <p>
 * Note: unlike most other components, this class does not extend
 * {@code BaseComponent}; it initializes its own {@link WebDriver} field
 * directly.
 */
public class TransferComponent {

    private WebDriver driver;


    public TransferComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

}
