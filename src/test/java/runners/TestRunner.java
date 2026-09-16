package runners;

import config.TestConfig;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

import org.testng.ITestContext;
import org.testng.annotations.*;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "steps",
                "hooks",
                "types"
        },
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber.json"
        }
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    public void configureExecution(ITestContext testContext) {

        String browser =
                testContext.getCurrentXmlTest()
                        .getParameter("browser");

        String headless =
                testContext.getCurrentXmlTest()
                        .getParameter("headless");

        TestConfig.setBrowser(browser);
        TestConfig.setHeadless(Boolean.parseBoolean(headless));

        System.out.println(
                "TestNG execution: " +
                        testContext.getName()
        );

        System.out.println(
                "Browser: " + browser
        );

        System.out.println(
                "Headless: " + headless
        );
    }
}
