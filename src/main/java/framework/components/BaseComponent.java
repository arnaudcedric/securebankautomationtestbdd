package framework.components;

import framework.pages.BaseUI;
import org.openqa.selenium.WebDriver;

/**
 * Base class for reusable "page fragment" components (e.g. the sidebar,
 * or the content of an individual dashboard section such as Accounts,
 * Transfer, Send Money, Bill Pay, Transactions). Extends {@link BaseUI}
 * to inherit the shared {@code driver}/{@code wait} and Selenium helpers,
 * mirroring the role that {@code BasePage} plays for full-page objects.
 */
public abstract class BaseComponent extends BaseUI {

    protected BaseComponent(WebDriver driver) {
        super(driver);
    }

}
