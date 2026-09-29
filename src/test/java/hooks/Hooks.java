package hooks;

import config.TestConfig;

import framework.config.ConfigManager;
import framework.driver.DriverFactory;
import framework.driver.DriverManager;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Cucumber lifecycle hooks that run around every scenario.
 * <p>
 * {@link #setUp(Scenario)} resolves the {@code browser} and {@code headless}
 * settings (system property &gt; {@code config.TestConfig}/testng.xml &gt;
 * {@code config-*.properties}), logs the resolved execution configuration,
 * creates a {@link WebDriver} via {@code framework.driver.DriverFactory},
 * and stores it in {@code framework.driver.DriverManager} for the scenario's
 * duration.
 * <p>
 * {@link #tearDown(Scenario)} attaches a screenshot to the Cucumber/Allure
 * report if the scenario failed, then quits the driver and clears it from
 * {@code DriverManager}.
 */
public class Hooks {

    @Before
    public void setUp(Scenario scenario) {

        // =====================================================
        // BROWSER
        //
        // Priority:
        // 1. Maven / GitHub / Jenkins -Dbrowser
        // 2. testng.xml
        // 3. config properties
        // =====================================================

        String browser =
                System.getProperty("browser");

        if (browser == null || browser.isBlank()) {
            browser = TestConfig.getBrowser();
        }

        if (browser == null || browser.isBlank()) {
            browser = ConfigManager.get("browser");
        }


        // =====================================================
        // HEADLESS
        //
        // Priority:
        // 1. Maven / GitHub / Jenkins -Dheadless
        // 2. testng.xml
        // 3. config properties
        // =====================================================

        String systemHeadless =
                System.getProperty("headless");

        boolean headless;

        if (systemHeadless != null) {

            headless =
                    Boolean.parseBoolean(systemHeadless);

        } else if (TestConfig.getHeadless() != null) {

            headless =
                    TestConfig.getHeadless();

        } else {

            headless =
                    Boolean.parseBoolean(
                            ConfigManager.get("headless")
                    );
        }


        // =====================================================
        // CUCUMBER TAG FILTER
        // =====================================================

        String tagFilter =
                System.getProperty(
                        "cucumber.filter.tags",
                        "No tag filter"
                );


        // =====================================================
        // LOG EXECUTION CONFIGURATION
        // =====================================================

        System.out.println();
        System.out.println(
                "========================================"
        );

        System.out.println(
                "Scenario : " + scenario.getName()
        );

        System.out.println(
                "Browser  : " + browser
        );

        System.out.println(
                "Headless : " + headless
        );

        System.out.println(
                "Tag Filter: " + tagFilter
        );

        System.out.println(
                "Scenario Tags: "
                        + scenario.getSourceTagNames()
        );

        System.out.println(
                "Thread   : "
                        + Thread.currentThread().getName()
        );

        System.out.println(
                "========================================"
        );


        // =====================================================
        // CREATE DRIVER
        // =====================================================

        WebDriver driver =
                DriverFactory.createDriver(
                        browser,
                        headless
                );

        DriverManager.setDriver(driver);
    }


    @After
    public void tearDown(Scenario scenario) {

        WebDriver driver =
                DriverManager.getDriver();

        if (driver == null) {
            return;
        }

        try {

            // =================================================
            // SCREENSHOT ON FAILURE
            // =================================================

            if (scenario.isFailed()
                    && driver instanceof TakesScreenshot) {

                byte[] screenshot =
                        ((TakesScreenshot) driver)
                                .getScreenshotAs(
                                        OutputType.BYTES
                                );

                scenario.attach(
                        screenshot,
                        "image/png",
                        "Failure Screenshot"
                );
            }

        } catch (Exception e) {

            System.err.println(
                    "Unable to capture screenshot: "
                            + e.getMessage()
            );

        } finally {

            // =================================================
            // CLOSE DRIVER
            // =================================================

            driver.quit();

            DriverManager.unload();
        }
    }
}