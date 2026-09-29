package config;

/**
 * Thread-local bridge that carries the {@code browser}/{@code headless}
 * values supplied by a TestNG {@code <parameter>} (in {@code testng.xml})
 * from {@code runners.TestRunner#configureExecution} into
 * {@code hooks.Hooks#setUp}, since Cucumber hooks have no direct access to
 * the TestNG {@code ITestContext}. Values are {@code null} unless TestNG
 * actually supplied them, letting {@code Hooks} fall back to system
 * properties or {@code framework.config.ConfigManager} otherwise. Cleared
 * after each TestNG {@code <test>} finishes.
 */
public final class TestConfig {

    private static final ThreadLocal<String> browser = new ThreadLocal<>();

    private static final ThreadLocal<Boolean> headless = new ThreadLocal<>();


    private TestConfig() {
    }

    public static void setBrowser(String value) {
        browser.set(value);
    }

    public static String getBrowser() {
        return browser.get();
    }


    public static void setHeadless(boolean value) {
        headless.set(value);
    }

    public static Boolean getHeadless() {
        return headless.get();
    }


    public static void clear() {
        browser.remove();
        headless.remove();
    }
}