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
                "json:target/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        }
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    public void configureExecution(ITestContext testContext) {

        String browser =
                testContext
                        .getCurrentXmlTest()
                        .getParameter("browser");

        String headless =
                testContext
                        .getCurrentXmlTest()
                        .getParameter("headless");


        // Only populate TestConfig when TestNG
        // actually supplied the parameter.

        if (browser != null && !browser.isBlank()) {
            TestConfig.setBrowser(browser);
        }

        if (headless != null && !headless.isBlank()) {
            TestConfig.setHeadless(
                    Boolean.parseBoolean(headless)
            );
        }
    }


    @AfterClass
    public void cleanUpExecution() {
        TestConfig.clear();
    }
}
