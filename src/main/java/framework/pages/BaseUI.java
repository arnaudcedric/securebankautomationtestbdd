package framework.pages;

import framework.config.ConfigManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Root class of the Page Object hierarchy.
 * <p>
 * Holds the {@link WebDriver} and a shared {@link WebDriverWait} (timeout read
 * from {@link ConfigManager}), initializes {@code @FindBy} elements via
 * {@link PageFactory}, and exposes small reusable Selenium helpers
 * ({@link #getText}, {@link #click}, {@link #navigateTo}) so that pages and
 * components don't repeat explicit-wait boilerplate. Both {@link BasePage}
 * (pages) and {@code BaseComponent} (reusable page fragments) extend this
 * class.
 */
public class BaseUI {

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected BaseUI(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(
                Long.parseLong(
                        ConfigManager.get("timeout")
                )));
        PageFactory.initElements(driver, this);
    }

    protected String getText(WebElement element) {
        return wait.until(
                ExpectedConditions.visibilityOf(element)
        ).getText().trim();
    }

    protected void click(WebElement element) {
        wait.until(
                ExpectedConditions.elementToBeClickable(element)
        ).click();
    }

    protected void navigateTo(String path) {
        driver.get(ConfigManager.get("base.url") + path);
    }



}
