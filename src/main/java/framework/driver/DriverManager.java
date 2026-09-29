package framework.driver;

import org.openqa.selenium.WebDriver;

/**
 * Holds the current thread's {@link WebDriver} in a {@link ThreadLocal} so
 * that parallel test/scenario execution (see {@code testng.xml}'s
 * {@code parallel="tests"}) does not share a driver instance across threads.
 * Set in {@code hooks.Hooks#setUp} before each scenario and cleared in
 * {@code hooks.Hooks#tearDown} after each scenario.
 */
public class DriverManager {

        private DriverManager() {
        }

        private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

        public static WebDriver getDriver() {
            return DRIVER.get();
        }

        public static void setDriver(WebDriver driver) {
            DRIVER.set(driver);
        }

        public static void unload() {
            DRIVER.remove();
        }

}
