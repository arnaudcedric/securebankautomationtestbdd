package framework.pages;

import framework.config.ConfigManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
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
