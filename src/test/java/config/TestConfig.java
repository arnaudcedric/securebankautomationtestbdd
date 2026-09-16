package config;

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