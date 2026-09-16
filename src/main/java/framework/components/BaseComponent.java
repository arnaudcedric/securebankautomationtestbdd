package framework.components;

import framework.pages.BaseUI;
import org.openqa.selenium.WebDriver;

public abstract class BaseComponent extends BaseUI {

    protected BaseComponent(WebDriver driver) {
        super(driver);
    }

}
