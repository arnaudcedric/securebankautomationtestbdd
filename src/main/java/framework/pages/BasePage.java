package framework.pages;

import org.openqa.selenium.WebDriver;

/**
 * Marker/base class for every full-page Page Object (e.g. {@link LoginPage},
 * {@link BankHomePage}). It adds no behavior of its own beyond {@link BaseUI};
 * it exists purely to distinguish "whole page" objects from the reusable
 * "page fragment" objects under {@code framework.components}
 * (which extend {@code BaseComponent} instead).
 */
public abstract class BasePage extends BaseUI {

    protected BasePage(WebDriver driver) {
        super(driver);
    }

}
