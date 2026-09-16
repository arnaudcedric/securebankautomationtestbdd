package hooks;

import config.TestConfig;
import framework.config.ConfigManager;
import framework.driver.DriverFactory;
import framework.driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import org.openqa.selenium.WebDriver;

public class Hooks {

    @Before
    public void setUp() {

        String browser = TestConfig.getBrowser();

        if (browser == null) {
            browser = System.getProperty(
                    "browser",
                    ConfigManager.get("browser")
            );
        }

        Boolean testNgHeadless =
                TestConfig.getHeadless();

        boolean headless;

        if (testNgHeadless != null) {
            headless = testNgHeadless;
        } else {

            headless = Boolean.parseBoolean(
                    System.getProperty(
                            "headless",
                            ConfigManager.get("headless")
                    )
            );
        }

// -----------------------------------------
        // DEBUG
        // -----------------------------------------

        System.out.println();
        System.out.println(
                "========================================"
        );

        System.out.println(
                "Browser  : " + browser
        );

        System.out.println(
                "Headless : " + headless
        );

        System.out.println(
                "Tags     : " +
                        System.getProperty(
                                "cucumber.filter.tags",
                                "No tag filter"
                        )
        );

        System.out.println("========================================");


        // -----------------------------------------
        // DRIVER
        // -----------------------------------------

        WebDriver driver = DriverFactory.createDriver(browser, headless);
        DriverManager.setDriver(driver);
    }


    @After
    public void tearDown(Scenario scenario) {

        WebDriver driver =
                DriverManager.getDriver();

        if (driver != null) {
            if (scenario.isFailed()) {
                // Screenshot can be attached here
            }
            driver.quit();
            DriverManager.unload();
        }
    }
}
