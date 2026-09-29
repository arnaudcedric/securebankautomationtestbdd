package runners;

import config.TestConfig;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

import org.testng.ITestContext;
import org.testng.annotations.*;

/**
 * TestNG entry point that executes all Cucumber {@code .feature} files under
 * {@code src/test/resources/features}, wiring glue code from the
 * {@code steps}, {@code hooks} and {@code types} packages (see
 * {@code @CucumberOptions} below). Extending {@code AbstractTestNGCucumberTests}
 * turns every scenario into its own TestNG test method, so this single class
 * is what {@code testng.xml} (and, indirectly, {@code mvn test}) runs.
 * <p>
 * {@link #configureExecution(ITestContext)} reads the {@code browser} and
 * {@code headless} {@code <parameter>} values from the current
 * {@code testng.xml} {@code <test>} (if present) and stores them in
 * {@link TestConfig} so that {@code hooks.Hooks#setUp} can pick them up
 * before creating the WebDriver for each scenario. {@link #cleanUpExecution()}
 * clears them again once the {@code <test>} finishes.
 * <p>
 * {@link #scenarios()} overrides the inherited data provider as
 * {@code @DataProvider(parallel = true)}. This is required for scenario-level
 * parallel execution: a suite's {@code parallel="methods"}/{@code thread-count}
 * attributes (e.g. in {@code testng-parallel.xml}) have no effect on
 * data-provider-driven test methods unless the data provider itself opts in
 * to parallel mode. It's safe here because {@code framework.driver.DriverManager}
 * and {@code config.TestConfig} are both {@code ThreadLocal}.
 */
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

    /**
     * Re-declares the inherited Cucumber scenario data provider as
     * {@code parallel = true} so that, when the suite itself also enables
     * {@code parallel="methods"} (see {@code testng-parallel.xml}), scenarios
     * execute concurrently across {@code thread-count} threads instead of
     * one at a time.
     */
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

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
