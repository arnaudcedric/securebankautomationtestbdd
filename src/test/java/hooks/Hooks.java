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
            browser = ConfigManager.get("browser");
        }

        Boolean configuredHeadless =
                TestConfig.getHeadless();

        boolean headless =
                configuredHeadless != null
                        ? configuredHeadless
                        : Boolean.parseBoolean(
                        ConfigManager.get("headless")
                );

        WebDriver driver =
                DriverFactory.createDriver(
                        browser,
                        headless
                );

        DriverManager.setDriver(driver);

//        driver.manage()
//                .window()
//                .maximize();
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
