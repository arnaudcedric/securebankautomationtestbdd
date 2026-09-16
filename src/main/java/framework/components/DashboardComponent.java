package framework.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DashboardComponent {

    private WebDriver driver;

    @FindBy(css = "[data-testid='stat-card'][data-metric='net-worth']")
    private WebElement netWorthCard;

    @FindBy(css = "[data-testid='stat-card'][data-metric='net-change']")
    private WebElement netChangeCard;

    @FindBy(css = "[data-testid='stat-card'][data-metric='income']")
    private WebElement incomeCard;

    @FindBy(css = "[data-testid='stat-card'][data-metric='expense']")
    private WebElement expenseCard;

    @FindBy(css = "[data-testid='stat-card']")
    private List<WebElement> statCards;

    public DashboardComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public int getNumberOfStatCards() {
        return statCards.size();
    }

}
