package context;

import framework.driver.DriverManager;
import framework.pages.PageManager;

/**
 * Cucumber-injected (via cucumber-picocontainer) per-scenario container that
 * lazily creates a single {@link PageManager} bound to the current
 * scenario's {@link framework.driver.DriverManager#getDriver()}. Injected
 * into step definition classes (e.g. {@code steps.LoginSteps}) via
 * constructor injection so all steps within one scenario share the same
 * page-object instances.
 */
public class ScenarioContext {

    private PageManager pageManager;

    public PageManager pages() {

        if (pageManager == null) {

            pageManager = new PageManager(
                    DriverManager.getDriver()
            );
        }

        return pageManager;
    }

}
