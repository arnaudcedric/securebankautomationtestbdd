package framework.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class TransferComponent {

    private WebDriver driver;


    public TransferComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

}
