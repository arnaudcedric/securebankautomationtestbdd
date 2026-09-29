package framework.driver;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Factory that creates a configured {@link WebDriver} instance for a given
 * browser name ({@code chrome}, {@code firefox}, or {@code edge}), optionally
 * headless. Called from {@code hooks.Hooks} before each scenario; the
 * resulting driver is then stored in {@code framework.driver.DriverManager}.
 */
public final class DriverFactory {

    private DriverFactory() {
    }


    public static WebDriver createDriver(
            String browser,
            boolean headless) {

        return switch (
                browser.toLowerCase().trim()
                ) {

            case "chrome" ->
                    createChrome(headless);

            case "firefox" ->
                    createFirefox(headless);

            case "edge" ->
                    createEdge(headless);

            default ->
                    throw new IllegalArgumentException(
                            "Unsupported browser: "
                                    + browser
                    );
        };
    }


    private static WebDriver createChrome(
            boolean headless) {

        ChromeOptions options =
                new ChromeOptions();

        if (headless) {
            options.addArguments(
                    "--headless=new"
            );
        }

        options.addArguments(
                "--no-sandbox",
                "--disable-dev-shm-usage"
        );

        return new ChromeDriver(options);
    }


    private static WebDriver createFirefox(
            boolean headless) {

        FirefoxOptions options =
                new FirefoxOptions();

        if (headless) {
            options.addArguments(
                    "-headless"
            );
        }

        return new FirefoxDriver(options);
    }


    private static WebDriver createEdge(
            boolean headless) {

        EdgeOptions options =
                new EdgeOptions();

        if (headless) {
            options.addArguments(
                    "--headless=new"
            );
        }

        return new EdgeDriver(options);
    }
}