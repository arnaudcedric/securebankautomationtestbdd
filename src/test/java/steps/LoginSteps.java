package steps;

import context.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.User;
import org.testng.Assert;
import types.LoginResult;

import java.util.List;

import static types.LoginResult.SUCCESS;

public class LoginSteps {

    private final ScenarioContext context;

    public LoginSteps(ScenarioContext context) {
        this.context = context;
    }
    @Given("the user is on the login page")
    public void theUserIsOnTheLoginPage() {
        context.pages().loginPage().open();
    }


    @When("the user logs in with:")
    public void theUserLogsInWith(List<User> users) {

        User user = users.getFirst();

        context.pages()
                .loginPage()
                .login(
                        user.getUsername(),
                        user.getPassword()
                );
    }

    @Then("the home page should be displayed")
    public void theHomePageShouldBeDisplayed() {

        String welcomeText = context.pages()
                .bankHomePage()
                .getWelcomeText();

        Assert.assertEquals(welcomeText, "Welcome back, Alex");
    }

    @When("the user logs in with username {string}")
    public void theUserLogsInWithUsername(String username) {
        context.pages().loginPage().enterUsername(username);
    }

    @And("password {string}")
    public void password(String password) {
        context.pages().loginPage().enterPassword(password);
        context.pages().loginPage().clickLogin();
    }

    @Then("the login result should be {loginResult} with message {string}")
    public void theLoginResultShouldBeResult(LoginResult result, String expectedMessage) {

        switch (result){
            case SUCCESS ->{
                String welcomeText = context.pages()
                        .bankHomePage()
                        .getWelcomeText();

                Assert.assertEquals(welcomeText, expectedMessage);
            }
            case FAILURE -> {
                String actualError =
                        context.pages()
                                .loginPage()
                                .getErrorMessage();

                Assert.assertEquals(
                        actualError,
                        expectedMessage
                );
            }
        }
    }
}
