package context;

import framework.driver.DriverManager;
import framework.pages.PageManager;

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
