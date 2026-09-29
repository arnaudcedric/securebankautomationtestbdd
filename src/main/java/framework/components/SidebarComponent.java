package framework.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Component for the dashboard's left-hand navigation sidebar. Present on
 * every authenticated page ({@link framework.pages.BankHomePage} holds one),
 * it exposes one {@code goToX()} method per link, each of which clicks the
 * link and returns the corresponding section's component, allowing calls to
 * be chained (e.g. {@code sidebar().goToAccounts().getMyAccountText()}).
 */
public class SidebarComponent {

    private WebDriver driver;

    @FindBy(css = "[data-testid='sidebar-link-dashboard']")
    private WebElement dashboard;

    @FindBy(css = "[data-testid='sidebar-link-accounts']")
    private WebElement accounts;

    @FindBy(css = "[data-testid='sidebar-link-transfer']")
    private WebElement transfer;

    @FindBy(css = "[data-testid='sidebar-link-send-money']")
    private WebElement sendMoney;

    @FindBy(css = "[data-testid='sidebar-link-bill-pay']")
    private WebElement billPay;

    @FindBy(css = "[data-testid='sidebar-link-transactions']")
    private WebElement transactions;

    public SidebarComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public DashboardComponent goToDashboard() {
        dashboard.click();
        return new DashboardComponent(driver);
    }

    public AccountsComponent goToAccounts() {
        accounts.click();
        return new AccountsComponent(driver);
    }

    public TransferComponent goToTransfer() {
        transfer.click();
        return new TransferComponent(driver);
    }

    public SendMoneyComponent goToSendMoney() {
        sendMoney.click();
        return new SendMoneyComponent(driver);
    }

    public BillPayComponent goToBillPay() {
        billPay.click();
        return new BillPayComponent(driver);
    }

    public TransactionsComponent goToTransactions() {
        transactions.click();
        return new TransactionsComponent(driver);
    }

}
